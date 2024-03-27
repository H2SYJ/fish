package team.h2syj.fish.debuff.turnBefore;

import lombok.Setter;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Renderer;
import team.h2syj.fish.core.Renderer.ColorList;
import team.h2syj.fish.debuff.DeBuff.TurnBeforeDeBuff;

public class TurnBeforeDeBuff_灼烧 extends TurnBeforeDeBuff {

    @Setter
    private int damage;

    public TurnBeforeDeBuff_灼烧(int turn, int damage) {
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
    public void execute(Biological target) {
        target.injuried(damage);
        new Renderer().newLine()
                .color(ColorList.red_胭脂虫)
                .print("%s受到%d点火属性持续伤害，%s", target.getName(), damage, target.hpContent())
                .end();
    }

}
