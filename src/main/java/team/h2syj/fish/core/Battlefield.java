package team.h2syj.fish.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import team.h2syj.fish.core.Biological.State;
import team.h2syj.fish.monster.Monster;
import team.h2syj.fish.player.Player;

/**
 * 战场
 */
@Getter
public class Battlefield {
    private final Player p1;
    private final Player p2;
    private final List<Biological> friends;
    private final List<Biological> monsters;
    private final BattlefieldEventRegister register = new BattlefieldEventRegister();
    private final Turn turn;

    public Battlefield(Player p1, Player p2, List<Biological> monsters) {
        this.p1 = p1;
        this.p2 = p2;
        this.friends = new ArrayList<>();
        this.friends.add(p1);
        if (p2 != null)
            this.friends.add(p2);
        this.monsters = monsters;
        this.turn = new Turn(p1, p2, monsters);
        // 事件注册器扫描战场将所有事件注册进去
        this.register.scan(this);
    }

    public Battlefield died(Biological biological) {
        LinkedList<Biological> axis = turn.getAxis();
        if (!axis.contains(biological))
            return this;
        axis.remove(biological);
        if (biological instanceof Monster)
            monsters.remove(biological);
        return this;
    }

    public boolean win() {
        return monsters.isEmpty();
    }

    public boolean lose() {
        assert p1 != null;
        if (p2 != null)
            return p1.getState() == State.死亡 && p2.getState() == State.死亡;
        return p1.getState() == State.死亡;
    }

    public Biological next() {
        return turn.next();
    }

    public void triggerEvent(Class<? extends BattlefieldEvent> clazz, Object... args) {
        register.scan(this);
        register.triggerEvent(clazz, args);
    }

    public List<Biological> getTarget(String target) {
        return null;
    }

    /**
     * 添加友方单位
     * 
     * @param self   自身
     * @param friend 友方单位
     */
    public void addFriends(Biological self, Biological friend) {
        if (self instanceof Monster)
            monsters.add(friend);
        else
            friends.add(friend);
        turn.add(friend);
    }
}
