package team.h2syj.fish.debuff;

import cn.hutool.core.util.NumberUtil;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Effect;

public interface DeBuff extends Effect {

    abstract class AbstractDeBuff implements DeBuff {
        protected int turn;
        protected double value;

        public AbstractDeBuff(int turn) {
            this.turn = turn;
            this.value = value();
        }

        @Override
        public String toString() {
            return String.format("%s：%s", name(), desc());
        }

        @Override
        public int turn() {
            return turn;
        }

        @Override
        public boolean diffTurn() {
            return --this.turn <= 0;
        };

        // 可用次数型buff每次传入1，护盾型每次传入伤害
        // 减去后耗尽自动清除buff
        public double diffValue(Biological target, double num) {
            value = NumberUtil.sub(value(), num);
            if (value <= 0) {
                target.getDeBuffs().remove(this);
                return -value;
            }
            return -num;
        };
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

    abstract class BaseDeBuff extends AbstractDeBuff {
        public BaseDeBuff(int turn) {
            super(turn);
        }
    }

}
