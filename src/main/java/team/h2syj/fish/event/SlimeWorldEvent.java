package team.h2syj.fish.event;

import java.util.List;
import java.util.stream.IntStream;

import team.h2syj.fish.core.Card;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.Renderer;
import team.h2syj.fish.core.Runtime;
import team.h2syj.fish.core.Treasure;
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

        Renderer renderer = new Renderer("战斗胜利");
        renderer.print("获得5g").end();
        p1.getData().modifyGold(5);
        if (p2 != null)
            p2.getData().modifyGold(5);

        List<Card> cards = Treasure.getCards(Rarity.normal, 3);
        Runtime.choose("选择卡牌加入到卡组", "选择", cards, "拒绝").ifPresent(Runtime.me().getDeck()::add);
    }

}
