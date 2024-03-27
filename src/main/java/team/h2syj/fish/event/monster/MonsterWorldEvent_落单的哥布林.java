package team.h2syj.fish.event.monster;

import java.util.List;

import team.h2syj.fish.core.Card;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.Renderer;
import team.h2syj.fish.core.Runtime;
import team.h2syj.fish.core.Treasure;
import team.h2syj.fish.core.WorldEvent.MonsterWorldEvent;
import team.h2syj.fish.monster.Monster_哥布林;
import team.h2syj.fish.player.Player;

@Rarity(Rarity.normal)
public class MonsterWorldEvent_落单的哥布林 extends MonsterWorldEvent {

    public MonsterWorldEvent_落单的哥布林() {
        monsters.add(new Monster_哥布林());
    }

    @Override
    public void join(Player p1, Player p2) {
        Runtime.fighting(p1, p2, monsters);

        Renderer renderer = new Renderer("战斗胜利");
        renderer.print("获得10g").end();
        p1.getData().modifyGold(10);
        if (p2 != null)
            p2.getData().modifyGold(10);

        List<Card> cards = Treasure.getCards(Rarity.normal, 3);
        Runtime.choose("选择卡牌加入到卡组", "选择", cards, "拒绝").ifPresent(Runtime.me().getDeck()::add);
    }
}
