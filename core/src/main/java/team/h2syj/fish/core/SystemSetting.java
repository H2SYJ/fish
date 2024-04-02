package team.h2syj.fish.core;

import java.util.Scanner;

import team.h2syj.fish.core.Controller.Input;
import team.h2syj.fish.core.Renderer.Output;

public class SystemSetting {
    // 系统换行
    public static final String newline = System.lineSeparator();
    /**
     * 游戏难度系数：增加怪物血量和伤害 </br>
     * 单人游戏：1倍 </br>
     * 双人游戏：1.5倍 </br>
     */
    public static double difficulty = 1d;

    public static Output output = System.out::print;
    public static Input input = (player) -> new Scanner(System.in).nextLine();
}
