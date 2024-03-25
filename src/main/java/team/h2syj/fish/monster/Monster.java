package team.h2syj.fish.monster;

import lombok.Getter;
import lombok.Setter;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.SystemSetting;

@Setter
@Getter
public abstract class Monster extends Biological {

    protected String name;

    public Monster(double hp) {
        super(hp * SystemSetting.difficulty);
    }

    @Override
    public void action() {
        // TODO monster ai
    }

    @Override
    public String toString() {
        return String.format("%s（%s/%s）", name, data.getCurHp(), data.getHp());
    }
}
