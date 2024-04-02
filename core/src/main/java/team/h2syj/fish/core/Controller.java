package team.h2syj.fish.core;

import java.util.function.Consumer;

import lombok.Getter;
import team.h2syj.fish.player.Player;

/**
 * 控制器：用户输入做出的选择
 */
@Getter
public class Controller {
    public interface Input {
        String nextLine(Player player);
    }

    private final String input;
    private boolean match = false;

    public Controller() {
        this(null, "选择");
    }

    public Controller(String tips) {
        this(null, tips);
    }

    public Controller(Player player) {
        this(player, "选择");
    }

    public Controller(Player player, String tips) {
        SystemSetting.output.print(String.format("%s: ", tips));
        this.input = SystemSetting.input.nextLine(player);
    }

    public Controller next(String regex, Consumer<String> callBack) {
        if (input.matches(regex)) {
            callBack.accept(input);
            match = true;
        }
        return this;
    }

    public Controller next(Choose choose) {
        if (input.matches(choose.regex())) {
            choose.callBack().accept(input);
            match = true;
        }
        return this;
    }

    public static void enterContinue() {
        new Controller("回车继续");
    }

}
