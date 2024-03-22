package team.h2syj.fish.core;

import java.util.ArrayList;
import java.util.List;
import team.h2syj.fish.buff.Buff;
import team.h2syj.fish.debuff.DeBuff;

public abstract class Biological {
    protected double hp;
    protected List<Buff> buffs = new ArrayList<>();
    protected List<DeBuff> deBuffs = new ArrayList<>();
    protected Deck deck = new Deck();

    public Biological(double hp) {
        this.hp = hp;
    }

}
