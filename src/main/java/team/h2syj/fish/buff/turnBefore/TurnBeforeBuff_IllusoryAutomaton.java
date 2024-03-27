package team.h2syj.fish.buff.turnBefore;

import team.h2syj.fish.buff.Buff.TurnBeforeBuff;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Renderer;
import team.h2syj.fish.core.Renderer.ColorList;
import team.h2syj.fish.utils.Utils;

public class TurnBeforeBuff_IllusoryAutomaton extends TurnBeforeBuff {

    public TurnBeforeBuff_IllusoryAutomaton() {
        super(Utils.INFINITE);
    }

    @Override
    public String name() {
        return "虚构机兵";
    }

    @Override
    public String desc() {
        return "回合开始时，回复等同于20%%生命上限的生命值。";
    }

    @Override
    public void execute(Biological target) {
        target.recover(target.curHp() * 0.2);
        new Renderer().newLine()
                .color(ColorList.green_春辰)
                .print("%s触发%s，回复20%%生命，%s", target.getName(), name(), target.hpContent())
                .end();
    }

}
