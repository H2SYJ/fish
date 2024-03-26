package team.h2syj.fish.card.buff;

import java.util.List;

import team.h2syj.fish.buff.Buff.DamageUpBuff;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.BuffCard;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.TargetSelect.SelfTargetSelect;

@Rarity(Rarity.legend)
public class BuffCard_蝶来引生 extends BuffCard implements SelfTargetSelect {
    @Override
    public String name() {
        return "蝶来引生";
    }

    @Override
    public String desc() {
        return "消耗当前30%生命值（大于5点血触发）获取「蝶来引生」Buff。蝶来引生：获得当前生命值上限20%的伤害提升";
    }

    @Override
    public int cost() {
        return 0;
    }

    @Override
    public void process(Biological self, List<Biological> target) {
        if (self.curHp() > 5) {
            self.injuried(self.curHp() * 0.3);
            self.addBuff(new Buff_蝶来引生());
        }
    }

    public static class Buff_蝶来引生 extends DamageUpBuff {
        public Buff_蝶来引生() {
            super(2);
        }

        @Override
        public String name() {
            return "蝶来引生";
        }

        @Override
        public String desc() {
            return "消耗当前30%生命值（保留1点血）获取「蝶来引生」Buff。蝶来引生：获得当前生命值上限20%的伤害提升";
        }

        @Override
        public double up(double damage, Biological attacker, Biological target) {
            return attacker.maxHp() * 0.2d;
        }
    }

}
