package team.h2syj.fish.core;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import team.h2syj.fish.monster.Monster;
import team.h2syj.fish.player.Player;

public class Turn {
    private final AtomicInteger turnNum = new AtomicInteger();
    private final Player p1;
    private final Player p2;
    private final List<Monster> monster;

    private final LinkedList<Biological> axis = new LinkedList<>();

    public Turn(Player p1, Player p2, List<Monster> monster) {
        this.p1 = p1;
        this.p2 = p2;
        this.monster = monster;
        axis.add(p1);
        if (p2 != null)
            axis.add(p2);
        axis.addAll(monster);
    }


    @Override
    public String toString() {
        return "";
    }
}
