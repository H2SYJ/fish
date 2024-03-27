package team.h2syj.fish.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import team.h2syj.fish.core.BattlefieldEvent.BaseBattlefieldEvent;
import team.h2syj.fish.core.BattlefieldEvent.TurnBattlefieldEvent;
import team.h2syj.fish.core.Renderer.Line;
import team.h2syj.fish.player.Player;
import team.h2syj.fish.utils.Utils;

public class Runtime {

    private static Player me = null;
    private static Battlefield battlefield = null;

    public static void home() {
        System.out.println("""
                ███████╗██╗███████╗██╗  ██╗
                ██╔════╝██║██╔════╝██║  ██║
                █████╗  ██║███████╗███████║
                ██╔══╝  ██║╚════██║██╔══██║
                ██║     ██║███████║██║  ██║
                ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝

                1）单人游戏
                2）双人游戏
                exit）退出
                """);
        new Controller().next("1", input -> startSingle())
                .next("2", input -> System.out.println("等待实现"))
                .next("exit", System.out::println);
    }

    public static void startSingle() {
        Player player = new Player();
        player.setName("我");
        me = player;
        Renderer renderer = new Renderer("初始卡牌");
        player.deck.stream().forEach(card -> {
            renderer.newLine().color(card.getColor()).print(card).end();
        });
        renderer.newLine().end();
        Controller.enterContinue();
        new World(player).start();
    }

    public static void fighting(Player p1, Player p2, List<Biological> monsters) {
        Renderer.eraseScreen();
        Renderer renderer = new Renderer("进入战斗");
        battlefield = new Battlefield(p1, p2, monsters);
        battlefield.triggerEvent(BaseBattlefieldEvent.class, BaseBattlefieldEvent.Type.进入战斗);
        do {
            renderer.print("行动轴：（当前回合）%s", battlefield.getTurn()).end();
            renderer.newLine().end();
            Biological biological = battlefield.next();
            battlefield.triggerEvent(TurnBattlefieldEvent.class, TurnBattlefieldEvent.Type.回合开始, biological);
            biological.action();
            battlefield.triggerEvent(TurnBattlefieldEvent.class, TurnBattlefieldEvent.Type.回合结束, biological);
            battlefield.refreshState();
        } while (!battlefield.win() && !battlefield.lose());
        if (battlefield.lose())
            Runtime.endGame();
        battlefield.triggerEvent(BaseBattlefieldEvent.class, BaseBattlefieldEvent.Type.离开战斗);
        battlefield = null;
    }

    public static <T> T choose(String title, String tips, List<T> list) {
        return choose(title, tips, list, null).orElseThrow();
    }

    public static <T> Optional<T> choose(String title, String tips, List<T> list, String cancel) {
        Renderer renderer = new Renderer(title);
        List<Choose> targetChooses = new ArrayList<>();
        AtomicReference<T> select = new AtomicReference<>();
        for (int i = 0; i < list.size(); i++) {
            T item = list.get(i);
            Line line = renderer.print("%s）", i + 1);
            if (item instanceof Treasure treasure)
                line.color(treasure.getColor());
            line.print(item).end();
            targetChooses.add(new Choose(String.valueOf(i + 1), s -> select.set(item)));
        }
        if (Utils.isNotEmpty(cancel)) {
            renderer.print("n）%s", cancel).end();
            targetChooses.add(new Choose("n", s -> {
                select.set(null);
            }));
        }
        Controller controller;
        do {
            controller = new Controller(tips);
            for (Choose choose : targetChooses) {
                controller.next(choose);
            }
        } while (!controller.isMatch());
        return Optional.ofNullable(select.get());
    }

    private static void endGame() {
        Renderer renderer = new Renderer("游戏结束");
        renderer.print("啊哈！你输了！").end();
        throw new GameOverException();
    }

    public static Optional<Battlefield> getBattlefield() {
        return Optional.ofNullable(battlefield);
    }

    public static Player me() {
        return me;
    }

    public static class GameOverException extends RuntimeException {
    }

}
