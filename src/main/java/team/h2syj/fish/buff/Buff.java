package team.h2syj.fish.buff;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Effect;

public interface Buff extends Effect {

    abstract class AbstractBuff implements Buff {
        protected int turn;

        public AbstractBuff(int turn) {
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

    // 造成的伤害提升
    abstract class DamageUpBuff extends AbstractBuff {
        public DamageUpBuff(int turn) {
            super(turn);
        }

        public abstract double up(double damage, Biological attacker, Biological target);
    }

    // 受到的伤害降低
    abstract class DamageDownBuff extends AbstractBuff {
        public DamageDownBuff(int turn) {
            super(turn);
        }

        public abstract double down(double damage, Biological attacker, Biological target);
    }

    // 数值提升
    abstract class ExaltationBuff extends AbstractBuff {
        public ExaltationBuff(int turn) {
            super(turn);
        }

        public abstract void execute(Biological target);
    }

    // 回合开始时触发增益
    abstract class TurnBeforeBuff extends AbstractBuff {
        public TurnBeforeBuff(int turn) {
            super(turn);
        }

        public abstract void execute(Biological target);
    }

}
