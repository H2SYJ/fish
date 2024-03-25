package team.h2syj.fish.debuff;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.debuff.DeBuff.DamageDownDeBuff;

public class BaseDamageDownDeBuff extends DamageDownDeBuff {
    public BaseDamageDownDeBuff() {
        super(1);
    }

    @Override
    public String name() {
        return "饿！！";
    }

    @Override
    public String desc() {
        return "造成的伤害降低1点";
    }

    @Override
    public double down(double damage, Biological attacker, Biological target) {
        return -1;
    }
}
