package team.h2syj.fish.card.buff;

import java.util.List;

import team.h2syj.fish.buff.Buff.DamageUpBuff;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.BuffCard;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.TargetSelect.SelfTargetSelect;

@Rarity(Rarity.normal)
public class BuffCard_重攻击 extends BuffCard implements SelfTargetSelect {

    final int baseDamage = 2;
    final int attachDamage = 2;

    @Override
    public String name() {
        return "重攻击";
    }

    @Override
    public String desc() {
        return "下次造成的伤害+%d。行动点为偶数时：伤害额外+%d。".formatted(baseDamage, attachDamage);
    }

    @Override
    public int cost() {
        return 1;
    }

    @Override
    public void process(Biological self, List<Biological> target) {
        self.addBuff(new Buff_重攻击());
    }

    public class Buff_重攻击 extends DamageUpBuff {
        public Buff_重攻击() {
            super(1);
        }

        @Override
        public String name() {
            return "重攻击";
        }

        @Override
        public String desc() {
            return "下次造成的伤害+%d。行动点为偶数时：伤害额外+%d".formatted(baseDamage, attachDamage);
        }

        @Override
        public double up(double damage, Biological attacker, Biological target) {
            if (attacker.getFightingState().getAction() % 2 == 0)
                return baseDamage + attachDamage;
            return baseDamage;
        }
    }

}
