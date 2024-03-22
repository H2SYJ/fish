package team.h2syj.fish.buff;

import team.h2syj.fish.core.Biological;

public interface Buff {
    String name();

    String desc();

    int round();

    abstract class AbstractBuff implements Buff {
        protected int round;

        public AbstractBuff(int round) {
            this.round = round;
        }

        @Override
        public String toString() {
            return String.format("%s：%s", name(), desc());
        }

        @Override
        public int round() {
            return round;
        }
    }

    // 造成的伤害提升
    abstract class DamageUpBuff extends AbstractBuff {
        public DamageUpBuff(int round) {
            super(round);
        }

        public abstract double up(double damage);
    }

    // 受到的伤害降低
    abstract class DamageDownBuff extends AbstractBuff {
        public DamageDownBuff(int round) {
            super(round);
        }

        public abstract double down(double damage);
    }

    // 数值提升
    abstract class ExaltationBuff extends AbstractBuff {
        public ExaltationBuff(int round) {
            super(round);
        }

        public abstract void execute(Biological target);
    }

}
