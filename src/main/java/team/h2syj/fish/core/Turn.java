package team.h2syj.fish.core;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import team.h2syj.fish.core.Biological.State;
import team.h2syj.fish.monster.Monster;
import team.h2syj.fish.player.Player;

public class Turn {
    private final AtomicInteger turnNum = new AtomicInteger();
    private final Player p1;
    private final Player p2;
    private final List<Monster> monsters;
    private final LinkedList<Biological> axis = new LinkedList<>();

    public Turn(Player p1, Player p2, List<Monster> monsters) {
        this.p1 = p1;
        this.p2 = p2;
        this.monsters = monsters;
        axis.add(p1);
        if (p2 != null)
            axis.add(p2);
        for (int i = 0; i < monsters.size(); i++) {
            Monster monster = monsters.get(i);
            monster.setName(monster.getClass().getSimpleName() + " " + (char) (i + 65));
            axis.add(monster);
        }
    }

    public Biological next() {
        Biological pop = axis.pop();
        axis.add(pop);
        return pop;
    }

    public Turn died(Biological biological) {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Biological biological : axis) {
            String name = "";
            if (biological == p1 && SystemSetting.isPlayer1)
                name = "你";
            else if (biological instanceof Player)
                name = "玩家";
            else if (biological instanceof Monster monster)
                name = monster.getName();
            builder.append(name);
            if (biological != axis.getLast())
                builder.append(" => ");
        }
        return builder.toString();
    }

}
