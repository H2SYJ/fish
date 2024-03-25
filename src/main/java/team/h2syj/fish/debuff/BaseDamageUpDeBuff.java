package team.h2syj.fish.debuff;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.debuff.DeBuff.DamageUpDeBuff;

public class BaseDamageUpDeBuff extends DamageUpDeBuff {
    public BaseDamageUpDeBuff() {
        super(2);
    }

    @Override
    public String name() {
        return "人生的意义";
    }

    @Override
    public String desc() {
        return "受到的伤害提升1点";
    }

    @Override
    public double up(double damage, Biological attacker, Biological target) {
        return damage + 1;
    }
}
