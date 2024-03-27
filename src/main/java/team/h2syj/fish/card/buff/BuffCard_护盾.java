package team.h2syj.fish.card.buff;

import java.util.List;

import team.h2syj.fish.buff.Buff.DamageDownBuff;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.BuffCard;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.TargetSelect.SelfTargetSelect;
import team.h2syj.fish.utils.Utils;

@Rarity(Rarity.normal)
public class BuffCard_护盾 extends BuffCard implements SelfTargetSelect {

    @Override
    public String name() {
        return "护盾";
    }

    @Override
    public String desc() {
        return "获得3点护盾。";
    }

    @Override
    public int cost() {
        return 1;
    }

    @Override
    public void process(Biological self, List<Biological> target) {
        self.addBuff(new Buff_护盾());
    }

    public class Buff_护盾 extends DamageDownBuff {
        public Buff_护盾() {
            super(Utils.INFINITE);
        }

        @Override
        public String name() {
            return "护盾";
        }

        @Override
        public String desc() {
            return "抵挡%d点伤害。".formatted(value());
        }

        @Override
        public double down(double damage, Biological attacker, Biological target) {
            return diffValue(target, damage);
        }
    }

}
