package team.h2syj.fish.core;

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
        System.out.println("""
                初始卡牌：
                %s
                           
                1）继续
                """);
    }

}
