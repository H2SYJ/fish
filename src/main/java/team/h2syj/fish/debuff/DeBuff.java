package team.h2syj.fish.debuff;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Effect;

public interface DeBuff extends Effect {

    abstract class AbstractDeBuff implements DeBuff {
        protected int turn;

        public AbstractDeBuff(int turn) {
            this.turn = turn;
        }

        @Override
        public String toString() {
            return String.format("%s：%s", name(), desc());
        }

        @Override
        public int turn() {
            return turn;
        }
    }

    // 受到的伤害提升
    abstract class DamageUpDeBuff extends AbstractDeBuff {
        public DamageUpDeBuff(int turn) {
            super(turn);
        }

        public abstract double up(double damage, Biological attacker, Biological self);
    }

    // 造成的伤害降低
    abstract class DamageDownDeBuff extends AbstractDeBuff {
        public DamageDownDeBuff(int turn) {
            super(turn);
        }

        public abstract double down(double damage, Biological attacker, Biological self);
    }

    // 数值降低
    abstract class ExaltationDeBuff extends AbstractDeBuff {
        public ExaltationDeBuff(int turn) {
            super(turn);
        }
    }

    // 回合开始时触发减益
    abstract class TurnBeforeDeBuff extends AbstractDeBuff {

        public TurnBeforeDeBuff(int turn) {
            super(turn);
        }

        public abstract void execute(Biological target);
    }
}
