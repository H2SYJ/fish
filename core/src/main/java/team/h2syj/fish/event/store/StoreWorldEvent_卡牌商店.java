package team.h2syj.fish.event.store;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.fusesource.jansi.Ansi.Attribute;

import team.h2syj.fish.core.Card;
import team.h2syj.fish.core.Choose;
import team.h2syj.fish.core.Controller;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.Renderer;
import team.h2syj.fish.core.Renderer.ColorList;
import team.h2syj.fish.core.Renderer.Line;
import team.h2syj.fish.core.Runtime;
import team.h2syj.fish.core.Treasure;
import team.h2syj.fish.core.Treasure.CardTreasure;
import team.h2syj.fish.core.WorldEvent.StoreWorldEvent;
import team.h2syj.fish.player.Player;
import team.h2syj.fish.utils.Utils;

public class StoreWorldEvent_卡牌商店 extends StoreWorldEvent {
    @Override
    public void join(Player p1, Player p2) {
        Renderer renderer = new Renderer("欢迎光临");

        List<Card> cards = new ArrayList<>();
        cards.addAll(Treasure.getCards(Rarity.normal, Utils.random(6, 8)));
        cards.addAll(Treasure.getCards(Rarity.rare, Utils.random(1, 2)));
        cards.addAll(Treasure.getCards(Rarity.legend, 1));

        List<Choose> chooses = new ArrayList<>();
        AtomicBoolean continues = new AtomicBoolean(false);
        do {
            renderer.print("钱包：%sg", Runtime.me().getData().getGold()).end();
            for (int i = 0; i < cards.size(); i++) {
                Card card = cards.get(i);
                int cost = price(card);
                Line line = renderer.print("%s）", i + 1);
                if (cost > Runtime.me().getData().getGold()) {
                    line.attribute(Attribute.STRIKETHROUGH_ON);
                    line.color(ColorList.red_胭脂虫).print("（🚫金币不足）");
                } else {
                    chooses.add(new Choose(String.valueOf(i + 1), input -> {
                        Runtime.me().getData().modifyGold(-cost); // 付钱！
                        Runtime.me().getDeck().add(card); // 交货！
                        cards.remove(card);
                    }));
                }
                line.color(ColorList.gold_黄栗留).print("- %sg - ", cost).color(card.getColor()).print(card).end();
            }
            renderer.newLine().print("n）退出").end();
            chooses.add(new Choose("n", input -> continues.set(false)));

            Controller controller;
            do {
                controller = new Controller("请选择");
                for (Choose choose : chooses) {
                    controller.next(choose);
                }
            } while (!controller.isMatch());
        } while (continues.get());
    }

    private int price(Card card) {
        int rarity = CardTreasure.rarity(card.getClass());
        int basePrice = switch (rarity) {
        case Rarity.legend -> 300; // 300起步
        case Rarity.rare -> 100; // 100起步
        default -> 20; // 20起步
        };
        return basePrice + Utils.random(0, 99);
    }

}
