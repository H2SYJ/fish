package team.h2syj.fish.card.attack;

import java.util.List;

import team.h2syj.fish.buff.Buff.DamageDownBuff;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.BuffCard;

public class AttackCard_Talent extends BuffCard {

    final int baseDamage = 2;
    final int attachDamage = 2;

    @Override
    public String name() {
        return "重攻击";
    }

    @Override
    public String desc() {
        return "指定我方一名角色，下次造成的伤害+%d。行动点为偶数时：伤害额外+%d。".formatted(baseDamage, attachDamage);
    }

    @Override
    public int cost() {
        return 1;
    }

    @Override
    public void process(Biological self, List<Biological> target) {
        self.addBuff(new Buff_AttackCard_Talent());
    }

    public class Buff_AttackCard_Talent extends DamageDownBuff {
        public Buff_AttackCard_Talent() {
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
        public double down(double damage, Biological attacker, Biological target) {
            if (attacker.getFightingState().getAction() % 2 == 0)
                return baseDamage + attachDamage;
            return baseDamage;
        }
    }

}
