package team.h2syj.fish.core;

import java.util.List;
import team.h2syj.fish.monster.Monster;
import team.h2syj.fish.player.Player;

public class Runtime {

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
        SystemSetting.me = player;
        System.out.println(String.format("""
                初始卡牌：
                %s

                1）继续
                """, player.deck));
        new Controller().next("1", input -> new World(player).start());
    }

    public static void fighting(Player p1, Player p2, List<Monster> monsters) {
        Renderer renderer = new Renderer("进入战斗");
        Battlefield battlefield = new Battlefield(p1, p2, monsters);
        do {
            renderer.print("行动轴：（当前回合）%s", battlefield.getTurn());
            Biological biological = battlefield.next();
            biological.action();
        } while (!battlefield.win() && !battlefield.lose());
        if (battlefield.lose())
            Runtime.endGame();
    }

    private static void endGame() {
        Renderer renderer = new Renderer("游戏结束");
        renderer.print("啊哈！你输了！");
    }

}
