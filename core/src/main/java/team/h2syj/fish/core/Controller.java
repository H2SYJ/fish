package team.h2syj.fish.core;

import java.util.function.Consumer;

import lombok.Getter;

/**
 * 控制器：用户输入做出的选择
 */
@Getter
public class Controller {
    public interface Input {
        String nextLine();
    }

    private final String input;
    private boolean match = false;

    public Controller() {
        this("选择");
    }

    public Controller(String tips) {
        SystemSetting.output.print(String.format("%s: ", tips));
        this.input = SystemSetting.input.nextLine();
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
