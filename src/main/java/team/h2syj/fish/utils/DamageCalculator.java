package team.h2syj.fish.utils;

import java.util.List;
import team.h2syj.fish.buff.Buff;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.debuff.DeBuff;

public class DamageCalculator {

    public static double calculate(double baseDamage, Biological attacker, Biological target) {
        List<Buff> attackerBuffs = attacker.getBuffs();
        List<DeBuff> attackerDeBuffs = attacker.getDeBuffs();
        List<Buff> targetBuffs = target.getBuffs();
        List<DeBuff> targetDeBuffs = target.getDeBuffs();

        double damage = baseDamage;

        return damage;
    }

}
