package team.h2syj.fish.monster;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.SystemSetting;

public abstract class Monster extends Biological {
    public Monster(double hp) {
        super(hp * SystemSetting.difficulty);
    }
}
