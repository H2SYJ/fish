package team.h2syj.fish.monster;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.FightingAI;
import team.h2syj.fish.core.Runtime;
import team.h2syj.fish.core.SystemSetting;

public abstract class Monster extends Biological {

    public Monster(double hp) {
        super(hp * SystemSetting.difficulty);
    }

    @Override
    public void action() {
        Runtime.getBattlefield()
                .ifPresent(item -> new FightingAI(this, item.getMonsters(), item.getFriends()).action());
    }

}
