package team.h2syj.fish.core;

import java.util.function.Consumer;

public record Choose(String regex, Consumer<String> callBack) {
}
