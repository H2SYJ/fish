package team.h2syj.fish.core;

public interface Effect {
    String name();

    String desc();

    int turn();

    default boolean diffTurn() {
        int turn = turn();
        return --turn <= 0;
    };
}
