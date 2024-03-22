package team.h2syj.fish.buff;

import team.h2syj.fish.core.Biological;

public interface Buff {
    String name();

    String desc();

    int turn();

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

        public abstract double up(double damage);
    }

    // 受到的伤害降低
    abstract class DamageDownBuff extends AbstractBuff {
        public DamageDownBuff(int turn) {
            super(turn);
        }

        public abstract double down(double damage);
    }

    // 数值提升
    abstract class ExaltationBuff extends AbstractBuff {
        public ExaltationBuff(int turn) {
            super(turn);
        }

        public abstract void execute(Biological target);
    }

}
