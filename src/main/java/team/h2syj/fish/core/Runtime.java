package team.h2syj.fish.core;

import java.util.List;
import java.util.Optional;
import team.h2syj.fish.core.BattlefieldEvent.BaseBattlefieldEvent;
import team.h2syj.fish.core.BattlefieldEvent.TurnBattlefieldEvent;
import team.h2syj.fish.monster.Monster;
import team.h2syj.fish.player.Player;

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
        new Controller()
                .next("1", input -> startSingle())
                .next("2", input -> System.out.println("等待实现"))
                .next("exit", System.out::println);
    }

    public static void startSingle() {
        Player player = new Player();
        me = player;
        System.out.println(String.format("""
                初始卡牌：
                %s

                1）继续
                """, player.deck));
        new Controller().next("1", input -> new World(player).start());
    }

    public static void fighting(Player p1, Player p2, List<Monster> monsters) {
        Renderer renderer = new Renderer("进入战斗");
        battlefield = new Battlefield(p1, p2, monsters);
        battlefield.triggerEvent(BaseBattlefieldEvent.class, BaseBattlefieldEvent.Type.进入战斗);
        do {
            renderer.println("行动轴：（当前回合）%s", battlefield.getTurn());
            Biological biological = battlefield.next();
            battlefield.triggerEvent(TurnBattlefieldEvent.class, TurnBattlefieldEvent.Type.回合开始, biological);
            if (biological == me)
                renderer.println(biological.getStateString());
            renderer.println();
            biological.action();
            // 宣布回合结束
            battlefield.triggerEvent(TurnBattlefieldEvent.class, TurnBattlefieldEvent.Type.回合结束, biological);
        } while (!battlefield.win() && !battlefield.lose());
        if (battlefield.lose())
            Runtime.endGame();
        battlefield.triggerEvent(BaseBattlefieldEvent.class, BaseBattlefieldEvent.Type.离开战斗);
        battlefield = null;
    }

    private static void endGame() {
        Renderer renderer = new Renderer("游戏结束");
        renderer.println("啊哈！你输了！");
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
