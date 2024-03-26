package team.h2syj.fish.card.buff;

import java.util.List;

import team.h2syj.fish.buff.Buff.DamageUpBuff;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.BuffCard;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.TargetSelect.SelfTargetSelect;
import team.h2syj.fish.utils.Utils;

/**
 * 姬子 若当前生命值百分比大于等于80%，则暴击率提高15%。
 */
@Rarity(Rarity.normal)
public class BuffCard_道标 extends BuffCard implements SelfTargetSelect {

    @Override
    public String name() {
        return "道标";
    }

    @Override
    public String desc() {
        return "若当前生命值百分比大于等于80%%，伤害+3。";
    }

    @Override
    public int cost() {
        return 2;
    }

    @Override
    public void process(Biological self, List<Biological> target) {
        self.addBuff(new Buff_道标());
    }

    public class Buff_道标 extends DamageUpBuff {
        public Buff_道标() {
            super(Utils.INFINITE);
        }

        @Override
        public String name() {
            return "道标";
        }

        @Override
        public String desc() {
            return "若当前生命值百分比大于等于80%%，伤害+3。";
        }

        @Override
        public double up(double damage, Biological attacker, Biological target) {
            if (attacker.curHp() / attacker.maxHp() >= 0.8)
                return +3;
            return 0;
        }
    }

}
