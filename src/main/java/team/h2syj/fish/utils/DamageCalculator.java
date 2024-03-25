package team.h2syj.fish.utils;

import java.util.List;

import team.h2syj.fish.buff.Buff;
import team.h2syj.fish.buff.Buff.DamageDownBuff;
import team.h2syj.fish.buff.Buff.DamageUpBuff;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.debuff.DeBuff;
import team.h2syj.fish.debuff.DeBuff.DamageDownDeBuff;
import team.h2syj.fish.debuff.DeBuff.DamageUpDeBuff;

public class DamageCalculator {

    public static double calculate(double baseDamage, Biological attacker, Biological target) {
        double damage = baseDamage;
        // 攻击者Buff
        List<Buff> attackerBuffs = attacker.getBuffs();
        for (Buff attackerBuff : attackerBuffs) {
            if (attackerBuff instanceof DamageUpBuff buff) // 造成伤害提升buff
                damage = buff.up(damage, attacker, target);
        }
        // 攻击者DeBuff
        List<DeBuff> attackerDeBuffs = attacker.getDeBuffs();
        for (DeBuff attackerBuff : attackerDeBuffs) {
            if (attackerBuff instanceof DamageDownDeBuff buff) // 造成伤害降低DeBuff
                damage = buff.down(damage, attacker, target);
        }
        // 目标Buff
        List<Buff> targetBuffs = target.getBuffs();
        for (Buff targetBuff : targetBuffs) {
            if (targetBuff instanceof DamageDownBuff buff) // 受到伤害降低Buff
                damage = buff.down(damage, attacker, target);
        }
        // 目标DeBuff
        List<DeBuff> targetDeBuffs = target.getDeBuffs();
        for (DeBuff targetBuff : targetDeBuffs) {
            if (targetBuff instanceof DamageUpDeBuff buff) // 受到伤害提升DeBuff
                damage = buff.up(damage, attacker, target);
        }
        return damage;
    }

}
