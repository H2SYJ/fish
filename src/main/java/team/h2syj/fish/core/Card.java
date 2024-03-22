package team.h2syj.fish.core;

import java.util.List;
import team.h2syj.fish.utils.DamageCalculator;

public interface Card {

    String name();

    String desc();

    void execute(Biological self, List<Biological> target);

    abstract class AbstractCard implements Card {
        @Override
        public String toString() {
            return String.format("%s：%s", name(), desc());
        }
    }

    abstract class BuffCard extends AbstractCard {
    }

    abstract class DeBuffCard extends AbstractCard {
    }

    abstract class AttackCard extends AbstractCard {
        public abstract double baseDamage();

        @Override
        public void execute(Biological self, List<Biological> target) {
            for (Biological biological : target) {
                double damage = DamageCalculator.calculate(baseDamage(), self, biological);
                biological.injuried(damage);
            }
        }
    }

    abstract class MagicCard extends AbstractCard {
    }

}
