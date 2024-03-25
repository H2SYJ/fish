package team.h2syj.fish.core;

import java.util.LinkedList;
import team.h2syj.fish.event.SlimeWorldEvent;
import team.h2syj.fish.player.Player;

public class World {
    private final Player p1;
    private final Player p2;
    private final LinkedList<WorldEvent> events = new LinkedList<>();

    public World(Player p1) {
        this(p1, null);
    }

    public World(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        init();
    }

    private void init() {
        Renderer renderer = new Renderer("初始化世界");
        renderer.print("怪物正在四处游荡").slowPrintln(1000, "...");
        events.add(new SlimeWorldEvent());
        renderer.println("每天的奇迹正在发生...");
        renderer.println("神秘的宝箱里有什么呢...");
        renderer.println("商店老板进货中...");
        renderer.println("邪教徒召唤Boss中...");
    }

    public void start() {
        while (events.peek() != null) {
            WorldEvent event = events.pop();
            event.join(p1, p2);
        }
    }
}
