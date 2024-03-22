package team.h2syj.fish.core;

import java.util.ArrayList;
import java.util.List;
import team.h2syj.fish.monster.Monster;
import team.h2syj.fish.player.Player;

public interface WorldEvent {
    void join(Player p1, Player p2);

    abstract class BaseWorldEvent implements WorldEvent {
    }

    abstract class MonsterWorldEvent implements WorldEvent {
        protected List<Monster> monster = new ArrayList<>();
    }

    abstract class BossWorldEvent implements WorldEvent {
        protected List<Monster> monster = new ArrayList<>();
    }

    abstract class StoreWorldEvent implements WorldEvent {
    }

}
