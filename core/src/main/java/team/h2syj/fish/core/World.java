package team.h2syj.fish.core;

import java.util.LinkedList;

import team.h2syj.fish.event.monster.MonsterWorldEvent_史莱姆;
import team.h2syj.fish.event.monster.MonsterWorldEvent_落单的哥布林;
import team.h2syj.fish.event.store.StoreWorldEvent_卡牌商店;
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
        renderer.print("怪物正在四处游荡...").end();
        events.add(new MonsterWorldEvent_史莱姆());
        events.add(new MonsterWorldEvent_落单的哥布林());
        renderer.print("奇迹正在发生...").end();
        renderer.print("神秘的宝箱里有什么呢...").end();
        renderer.print("商店老板进货中...").end();
        events.add(new StoreWorldEvent_卡牌商店());
        renderer.print("邪教徒召唤Boss中...").end();
    }

    public void start() {
        while (events.peek() != null) {
            WorldEvent event = events.pop();
            event.join(p1, p2);
        }
    }
}
