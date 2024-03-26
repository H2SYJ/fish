package team.h2syj.fish.core;

import java.util.List;

import team.h2syj.fish.core.BattlefieldEvent.CardBattlefieldEvent;
import team.h2syj.fish.core.BattlefieldEvent.CardBattlefieldEvent.Type;
import team.h2syj.fish.core.Biological.State;
import team.h2syj.fish.core.TargetSelect.EnemyTargetSelect;
import team.h2syj.fish.core.TargetSelect.FriendlyTargetSelect;
import team.h2syj.fish.utils.DamageCalculator;

/**
 * 卡牌
 */
public interface Card extends Treasure {

    String name();

    String desc();

    default int cost() {
        return 0;
    }

    default void execute(Biological self, List<Biological> target) {
        int cost = cost();
        if (cost > self.fightingState.action)
            throw new RuntimeException();

        Battlefield battlefield = Runtime.getBattlefield().orElseThrow();
        battlefield.triggerEvent(CardBattlefieldEvent.class, Type.使用卡牌之前, this, self, target);
        this.process(self, target);
        self.fightingState.modifyAction(-cost);
        battlefield.triggerEvent(CardBattlefieldEvent.class, Type.使用卡牌之后, this, self, target);

        for (Biological biological : target) {
            if (biological.state == State.死亡)
                battlefield.died(biological);
        }
    }

    void process(Biological self, List<Biological> target);

    abstract class AbstractCard implements Card {
        @Override
        public String toString() {
            return String.format("%s：%s", name(), desc());
        }
    }

    abstract class BuffCard extends AbstractCard implements FriendlyTargetSelect {
    }

    abstract class DeBuffCard extends AbstractCard implements EnemyTargetSelect {
    }

    abstract class AttackCard extends AbstractCard implements EnemyTargetSelect {
        public abstract double baseDamage();

        @Override
        public void process(Biological self, List<Biological> target) {
            for (Biological biological : target) {
                double damage = DamageCalculator.calculate(baseDamage(), self, biological);
                biological.injuried(damage);
            }
        }
    }

    abstract class MagicCard extends AbstractCard {
    }

}
