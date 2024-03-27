package team.h2syj.fish.core;

public interface Effect {
    String name();

    String desc();

    int turn();

    default double value() {
        return -1;
    };

    boolean diffTurn();
}
