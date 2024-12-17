package team.h2syj.fish.core;

import java.util.List;

import team.h2syj.fish.core.BattlefieldEvent.CardBattlefieldEvent;
import team.h2syj.fish.core.BattlefieldEvent.CardBattlefieldEvent.Type;
import team.h2syj.fish.core.TargetSelect.EnemyTargetSelect;
import team.h2syj.fish.utils.DamageCalculator;

/**
 * 卡牌
 */
public interface Card extends Treasure {

    String name();

    String desc();

    int cost();

    default void execute(Biological self, List<Biological> target) {
        int cost = cost();
        if (cost > self.fightingState.action)
            throw new RuntimeException();

        Battlefield battlefield = Runtime.getBattlefield().orElseThrow();
        battlefield.triggerEvent(CardBattlefieldEvent.class, Type.使用卡牌之前, this, self, target);
        this.process(self, target);
        self.fightingState.modifyAction(-cost);
        battlefield.triggerEvent(CardBattlefieldEvent.class, Type.使用卡牌之后, this, self, target);
    }

    void process(Biological self, List<Biological> target);

    abstract class AbstractCard implements Card {
        @Override
        public String toString() {
            return String.format("%s：%s", name(), desc());
        }
    }

    abstract class BuffCard extends AbstractCard {
    }

    abstract class DeBuffCard extends AbstractCard implements EnemyTargetSelect {
    }

    abstract class AttackCard extends AbstractCard implements EnemyTargetSelect {
        public abstract double baseDamage(Biological self, List<Biological> target);

        @Override
        public void process(Biological self, List<Biological> target) {
            for (Biological biological : target) {
                double damage = DamageCalculator.calculate(baseDamage(self, target), self, biological);
                biological.injured(self, damage);
            }
        }
    }

    abstract class AoeAttackCard extends AttackCard {
        public abstract void process(Biological self, Biological mainTarget, List<Biological> minorTarget);

        @Override
        public void process(Biological self, List<Biological> target) {
            if (target.isEmpty())
                return;
            if (target.size() > 1)
                process(self, target.get(0), target.subList(1, target.size()));
            else
                process(self, target.get(0), List.of());
        }
    }

    abstract class MagicCard extends AbstractCard {
    }

}
