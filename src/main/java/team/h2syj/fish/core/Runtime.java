package team.h2syj.fish.core;

import java.util.List;
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
        System.out.println(String.format("""
                初始卡牌：
                %s

                1）继续
                """, player.deck));
        new Controller().next("1", input -> new World(player).start());
    }

    public static void fighting(Player p1, Player p2, List<Biological> monster) {
        Renderer renderer = new Renderer("开始战斗");
    }

}
