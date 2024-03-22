package team.h2syj.fish.monster;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.SystemSetting;

public abstract class Monster extends Biological {

    protected String name;

    public Monster(double hp) {
        super(hp * SystemSetting.difficulty);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void action() {
        // TODO monster ai
    }
}
