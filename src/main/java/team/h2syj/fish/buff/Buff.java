package team.h2syj.fish.buff;

import cn.hutool.core.util.NumberUtil;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Effect;

public interface Buff extends Effect {

    abstract class AbstractBuff implements Buff {
        protected int turn;
        protected double value;

        public AbstractBuff(int turn) {
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
                target.getBuffs().remove(this);
                return -value;
            }
            return -num;
        };
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

    abstract class BaseBuff extends AbstractBuff {
        public BaseBuff(int turn) {
            super(turn);
        }
    }

}
