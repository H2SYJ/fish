package team.h2syj.fish.debuff;

public interface DeBuff {
    String name();

    String desc();

    int round();

    abstract class AbstractDeBuff implements DeBuff {
        protected int round;

        public AbstractDeBuff(int round) {
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

    // 受到的伤害提升
    abstract class DamageUpDeBuff extends AbstractDeBuff {
        public DamageUpDeBuff(int round) {
            super(round);
        }
        public abstract double up(double damage);
    }

    // 造成的伤害降低
    abstract class DamageDownDeBuff extends AbstractDeBuff {
        public DamageDownDeBuff(int round) {
            super(round);
        }
        public abstract double down(double damage);
    }

    // 数值提升
    abstract class ExaltationDeBuff extends AbstractDeBuff {
        public ExaltationDeBuff(int round) {
            super(round);
        }
    }
}
