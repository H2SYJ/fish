package team.h2syj.fish.card.buff;

import java.util.List;

import team.h2syj.fish.buff.Buff.DamageUpBuff;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.BuffCard;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.TargetSelect.SelfTargetSelect;
import team.h2syj.fish.debuff.DeBuff;
import team.h2syj.fish.debuff.turnBefore.TurnBeforeDeBuff_灼烧;
import team.h2syj.fish.utils.Utils;

/**
 * 姬子 战技对灼烧状态下的敌方目标造成的伤害提高20%。
 */
@Rarity(Rarity.normal)
public class BuffCard_灼热 extends BuffCard implements SelfTargetSelect {

    @Override
    public String name() {
        return "灼热";
    }

    @Override
    public String desc() {
        return "对灼烧状态下的敌方目标造成的伤害+2。";
    }

    @Override
    public int cost() {
        return 1;
    }

    @Override
    public void process(Biological self, List<Biological> target) {
        self.addBuff(new Buff_灼热());
    }

    public class Buff_灼热 extends DamageUpBuff {
        public Buff_灼热() {
            super(Utils.INFINITE);
        }

        @Override
        public String name() {
            return "灼热";
        }

        @Override
        public String desc() {
            return "对灼烧状态下的敌方目标造成的伤害+2。";
        }

        @Override
        public double up(double damage, Biological attacker, Biological target) {
            for (DeBuff item : target.getDeBuffs()) {
                if (item instanceof TurnBeforeDeBuff_灼烧) {
                    return +2;
                }
            }
            return 0;
        }
    }

}
