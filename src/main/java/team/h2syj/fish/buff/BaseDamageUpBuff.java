package team.h2syj.fish.buff;

import team.h2syj.fish.buff.Buff.DamageUpBuff;

public class BaseDamageUpBuff extends DamageUpBuff {
    public BaseDamageUpBuff() {
        super(2);
    }

    @Override
    public String name() {
        return "现在的我强的可怕！";
    }

    @Override
    public String desc() {
        return "造成的伤害提升1点";
    }

    @Override
    public double up(double damage) {
        return damage + 1;
    }
}
