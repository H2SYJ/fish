package team.h2syj.fish.core;

import java.util.ArrayList;
import java.util.List;

import team.h2syj.fish.buff.Buff;
import team.h2syj.fish.debuff.DeBuff;

/**
 * 生物抽象类
 */
public abstract class Biological {
    protected double hp;
    protected State state = State.正常;
    protected List<Buff> buffs = new ArrayList<>();
    protected List<DeBuff> deBuffs = new ArrayList<>();
    protected Deck deck = new Deck();

    public enum State {
        正常,
        无法行动,
        死亡;
    }

    public Biological(double hp) {
        this.hp = hp;
    }

    public abstract void action();

    public double hp() {
        return hp;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }

    public Biological addBuff(Buff buff) {
        this.buffs.add(buff);
        return this;
    }

    public List<DeBuff> getDeBuffs() {
        return deBuffs;
    }

    public Biological addDeBuff(DeBuff deBuff) {
        this.deBuffs.add(deBuff);
        return this;
    }

    public Biological injuried(double damage) {
        this.hp -= damage;
        if (this.hp <= 0) {
            this.hp = 0;
            this.state = State.死亡;
        }
        return this;
    }

}
