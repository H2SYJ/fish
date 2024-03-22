package team.h2syj.fish.core;

import team.h2syj.fish.player.Player;

public abstract class WorldEvent {
    public abstract void join(Player p1, Player p2);
}
