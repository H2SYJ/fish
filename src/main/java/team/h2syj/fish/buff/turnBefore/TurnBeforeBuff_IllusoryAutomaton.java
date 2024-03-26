package team.h2syj.fish.buff.turnBefore;

import team.h2syj.fish.buff.Buff.TurnBeforeBuff;
import team.h2syj.fish.core.Biological;
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
        return "回合开始时，回复等同于20%生命上限的生命值。";
    }

    @Override
    public void execute(Biological attacker) {
        attacker.recover(attacker.hp() * 0.2);
    }

}
