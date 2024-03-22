package team.h2syj.fish.buff;

import team.h2syj.fish.buff.Buff.DamageDownBuff;

public class BaseDamageDownBuff extends DamageDownBuff {
    public BaseDamageDownBuff() {
        super(1);
    }

    @Override
    public String name() {
        return "没吃饱饭吗？";
    }

    @Override
    public String desc() {
        return "减少1点即将收到的伤害";
    }

    @Override
    public double down(double damage) {
        return damage - 1;
    }
}
