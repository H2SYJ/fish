package team.h2syj.fish.debuff.turnBefore;

import lombok.Setter;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.debuff.DeBuff.TurnBeforeDeBuff;

public class TurnBeforeDeBuff_Burn extends TurnBeforeDeBuff {

    @Setter
    private int damage;

    public TurnBeforeDeBuff_Burn(int turn, int damage) {
        super(turn);
        setDamage(damage);
    }

    @Override
    public String name() {
        return "灼烧";
    }

    @Override
    public String desc() {
        return "每回合开始时受到%d点火属性持续伤害".formatted(damage);
    }

    @Override
    public void execute(Biological attacker) {
        attacker.injuried(damage);
    }

}
