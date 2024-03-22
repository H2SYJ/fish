package team.h2syj.fish.event;

import java.util.stream.IntStream;
import team.h2syj.fish.core.Runtime;
import team.h2syj.fish.core.WorldEvent.MonsterWorldEvent;
import team.h2syj.fish.monster.Slime;
import team.h2syj.fish.player.Player;
import team.h2syj.fish.utils.Utils;

public class SlimeWorldEvent extends MonsterWorldEvent {
    public SlimeWorldEvent() {
        // 随机1-3个史莱姆
        IntStream.range(0, Utils.random(1, 3)).mapToObj(i -> new Slime()).forEach(monsters::add);
    }

    @Override
    public void join(Player p1, Player p2) {
        // 进入战斗
        Runtime.fighting(p1, p2, monsters);
    }
}
