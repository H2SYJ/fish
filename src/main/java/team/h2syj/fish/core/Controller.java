package team.h2syj.fish.core;

import java.util.Scanner;
import java.util.function.Consumer;

public class Controller {
    private static final Scanner scanner = new Scanner(System.in);
    private final String nextLine;

    public Controller() {
        this("选择");
    }

    public Controller(String tips) {
        System.out.printf("%s: ", tips);
        this.nextLine = scanner.nextLine();
    }

    public Controller next(String regex, Consumer<String> callBack) {
        if (nextLine.matches(regex))
            callBack.accept(nextLine);
        return this;
    }

    public Controller next(Choose choose) {
        if (nextLine.matches(choose.regex()))
            choose.callBack().accept(nextLine);
        return this;
    }

}
