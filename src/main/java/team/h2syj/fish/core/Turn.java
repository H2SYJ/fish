package team.h2syj.fish.core;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import team.h2syj.fish.monster.Monster;
import team.h2syj.fish.player.Player;

/**
 * 管理回合的类
 */
public class Turn {
    // 回合数
    private final AtomicInteger turnNum = new AtomicInteger();
    // 行动轴
    private final LinkedList<Biological> axis = new LinkedList<>();

    public Turn(Player p1, Player p2, List<Monster> monsters) {
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


    public LinkedList<Biological> getAxis() {
        return axis;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Biological biological : axis) {
            String name = "";
            if (biological == SystemSetting.me)
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
