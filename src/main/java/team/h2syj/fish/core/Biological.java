package team.h2syj.fish.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cn.hutool.core.util.NumberUtil;
import lombok.Getter;
import lombok.Setter;
import team.h2syj.fish.buff.Buff;
import team.h2syj.fish.core.BattlefieldEvent.BaseBattlefieldEvent;
import team.h2syj.fish.core.BattlefieldEvent.CardBattlefieldEvent;
import team.h2syj.fish.core.BattlefieldEvent.TurnBattlefieldEvent;
import team.h2syj.fish.debuff.DeBuff;

/**
 * 生物抽象类
 */
@Getter
public abstract class Biological implements BaseBattlefieldEvent, TurnBattlefieldEvent, CardBattlefieldEvent {
    @Setter
    protected String name;
    protected Data data = new Data();
    protected State state = State.正常;
    protected FightingState fightingState;
    protected Deck deck = new Deck();

    public enum State {
        正常,
        无法行动,
        死亡;
    }

    public Biological(double hp) {
        this.data.curHp = hp;
        this.data.hp = hp;
    }

    public abstract void action();

    public double curHp() {
        return this.data.curHp;
    }

    public double maxHp() {
        return this.data.hp;
    }

    public List<Buff> getBuffs() {
        return fightingState.buffs;
    }

    public Biological addBuff(Buff buff) {
        fightingState.buffs.add(buff);
        return this;
    }

    public List<DeBuff> getDeBuffs() {
        return fightingState.deBuffs;
    }

    public Biological addDeBuff(DeBuff deBuff) {
        fightingState.deBuffs.add(deBuff);
        return this;
    }

    public Biological injuried(double damage) {
        Battlefield battlefield = Runtime.getBattlefield().orElseThrow();
        battlefield.triggerEvent(InjuriedBattlefieldEvent.class, InjuriedBattlefieldEvent.Type.受到伤害之前, this);
        this.data.curHp = NumberUtil.sub(this.data.curHp, damage);
        if (this.data.curHp <= 0) {
            this.data.curHp = 0;
            battlefield.triggerEvent(DiedBattlefieldEvent.class, DiedBattlefieldEvent.Type.死亡前, this);
            if (this.data.curHp == 0) { // 在死亡前可能被某种手段回血回复起来：如复活、Boss二阶段
                this.state = State.死亡;
                // 真正的死亡
                battlefield.triggerEvent(DiedBattlefieldEvent.class, DiedBattlefieldEvent.Type.死亡后, this);
                return this;
            }
        }
        battlefield.triggerEvent(InjuriedBattlefieldEvent.class, InjuriedBattlefieldEvent.Type.受到伤害之后, this);
        return this;
    }

    public Biological recover(double hp) {
        this.data.curHp = Math.min(NumberUtil.add(this.data.curHp, hp), this.data.hp);
        return this;
    }

    @Override
    public void process(BaseBattlefieldEvent.Type type) {
        switch (type) {
        case 进入战斗 -> {
            this.fightingState = new FightingState(this);
            this.fightingState.gacha(3);
        }
        case 离开战斗 -> {
            this.fightingState = null;
        }
        }
    }

    @Override
    public void process(TurnBattlefieldEvent.Type type, Biological target) {
        if (this != target)
            return;
        switch (type) {
        case 回合开始 -> {
            // 回合开始恢复一点行动点
            if (fightingState.action < data.action)
                fightingState.modifyAction(1);
            fightingState.gacha(2); // 回合开始抽一张牌
        }
        case 回合结束 -> {
            // 回合型buff回合数-1
            getBuffs().removeIf(Buff::diffTurn);
            getDeBuffs().removeIf(DeBuff::diffTurn);
        }
        }
    }

    @Override
    public void process(CardBattlefieldEvent.Type type, Card card, Biological use, List<Biological> target) {
        if (this != use)
            return;
        switch (type) {
        case 使用卡牌之前 -> {
        }
        case 使用卡牌之后 -> fightingState.useCard(card);
        }
    }

    public String getStateString() {
        String hpContent = hpContent();

        String actionBar = IntStream.range(0, fightingState.action).mapToObj(i -> "※").collect(Collectors.joining());
        String actionContent = String.format("行动点（%s/%s）：%s", fightingState.action, data.action, actionBar);

        return String.join(SystemSetting.newline, hpContent, actionContent);
    }

    public String hpContent() {
        int len = (int) (NumberUtil.mul(data.curHp, data.hp) * 20);
        String hpBar = IntStream.range(0, 20).mapToObj(i -> i < len ? "|" : "-").collect(Collectors.joining());
        return String.format("血量（%s/%s）：(%s)", data.curHp, data.hp, hpBar);
    }

    /**
     * 战斗状态
     */
    @lombok.Data
    public static class FightingState {
        int action;
        Deck deck; // 牌组
        Deck curDeck = new Deck(); // 手牌
        Deck nextDeck = new Deck(); // 墓地
        int taunt = 50; // 嘲讽值
        List<Buff> buffs = new ArrayList<>();
        List<DeBuff> deBuffs = new ArrayList<>();

        protected FightingState(Biological biological) {
            this.deck = biological.deck.temp();
        }

        public void gacha() {
            gacha(1);
        }

        public void gacha(int num) {
            for (int i = 0; i < num; i++) {
                deck.gacha().ifPresentOrElse(curDeck::add, () -> {
                    (deck = nextDeck).gacha().ifPresent(curDeck::add);
                    nextDeck = new Deck();
                });
            }
        }

        public void useCard(Card card) {
            curDeck.remove(card);
            nextDeck.add(card);
        }

        public void modifyAction(int action) {
            this.action += action;
        }

    }

    /**
     * 生物数据
     */
    @lombok.Data
    public static class Data {
        double curHp; // 当前血量
        double hp; // 血量上限
        int action = 5; // 行动点上限
        int gold = 100; // 金币

        public void modifyGold(int gold) {
            this.gold += gold;
        }
    }

    @Override
    public String toString() {
        return String.format("%s（%s/%s）", name, data.getCurHp(), data.getHp());
    }

}
