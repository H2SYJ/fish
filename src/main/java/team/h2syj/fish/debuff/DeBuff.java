package team.h2syj.fish.debuff;

public interface DeBuff {
    String name();

    String desc();

    int turn();

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
        public abstract double up(double damage);
    }

    // 造成的伤害降低
    abstract class DamageDownDeBuff extends AbstractDeBuff {
        public DamageDownDeBuff(int turn) {
            super(turn);
        }
        public abstract double down(double damage);
    }

    // 数值提升
    abstract class ExaltationDeBuff extends AbstractDeBuff {
        public ExaltationDeBuff(int turn) {
            super(turn);
        }
    }
}
