package team.h2syj.fish.core;

import java.util.LinkedList;
import java.util.List;
import team.h2syj.fish.core.Biological.State;
import team.h2syj.fish.monster.Monster;
import team.h2syj.fish.player.Player;

/**
 * 战场
 */
public class Battlefield {
    private final Player p1;
    private final Player p2;
    private final List<Monster> monsters;
    private final Turn turn;

    public Battlefield(Player p1, Player p2, List<Monster> monsters) {
        this.p1 = p1;
        this.p2 = p2;
        this.monsters = monsters;
        this.turn = new Turn(p1, p2, monsters);
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
            return p1.state == State.死亡 && p2.state == State.死亡;
        return p1.state == State.死亡;
    }

    public Biological next() {
        Biological next = turn.next();
        if (next != null) {
            // 发送回合开始事件
        }
        return next;
    }

    public Turn getTurn() {
        return this.turn;
    }

}
