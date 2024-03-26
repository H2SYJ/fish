package team.h2syj.fish.player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.fusesource.jansi.Ansi.Attribute;

import team.h2syj.fish.card.buff.BuffCard_重攻击;
import team.h2syj.fish.card.magic.MagicCard_运筹帷幄;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card;
import team.h2syj.fish.core.Card.AttackCard;
import team.h2syj.fish.core.Choose;
import team.h2syj.fish.core.Controller;
import team.h2syj.fish.core.Deck;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.Renderer;
import team.h2syj.fish.core.Renderer.ColorList;
import team.h2syj.fish.core.Renderer.Line;
import team.h2syj.fish.core.TargetSelect;

public class Player extends Biological {
    public Player() {
        super(20d);
        deck.add(new AttackCard_让我摸摸看());
        deck.add(new AttackCard_让我摸摸看());
        deck.add(new AttackCard_让我摸摸看());
        deck.add(new AttackCard_让我摸摸看());
        deck.add(new AttackCard_让我摸摸看());
        deck.add(new AttackCard_让我摸摸看());
        deck.add(new MagicCard_运筹帷幄());
        deck.add(new MagicCard_运筹帷幄());
        deck.add(new BuffCard_重攻击());
        deck.add(new BuffCard_重攻击());
    }

    @Override
    public void action() {
        Renderer renderer = new Renderer("回合开始");
        renderer.newLine().color(ColorList.green_spring).print(this.getStateString()).end();

        AtomicBoolean continues = new AtomicBoolean(false);
        do {
            renderer.print("当前手牌").end();
            Deck curDeck = fightingState.getCurDeck();
            int actionPoint = fightingState.getAction();
            List<Card> cards = curDeck.getCards();
            List<Choose> chooses = new ArrayList<>();
            for (int i = 0; i < cards.size(); i++) {
                Card card = cards.get(i);
                int cost = card.cost();
                Line line = renderer.print("%s）", i + 1);
                if (cost > actionPoint) {
                    line.attribute(Attribute.STRIKETHROUGH_ON);
                    line.color(ColorList.red_cochineal).print("（🚫行动点不足）");
                } else {
                    chooses.add(new Choose(String.valueOf(i + 1), input -> {
                        if (card instanceof TargetSelect targetSelect) {
                            Biological target = targetSelect.select();
                            card.execute(this, List.of(target));
                            continues.set(!(card instanceof AttackCard)); // 非攻击卡不结束回合
                        }
                    }));
                }
                line.color(card.getColor()).print(card).end();
            }

            if (state != State.正常) {
                renderer.newLine().color(ColorList.red_cochineal).print("你当前无法行动").end();
                return;
            }

            Controller controller;
            do {
                controller = new Controller("等待行动");
                for (Choose choose : chooses) {
                    controller.next(choose);
                }
            } while (!controller.isMatch());
        } while (continues.get());
        new Renderer("回合结束");
    }

    @Rarity(Rarity.normal)
    public static class AttackCard_让我摸摸看 extends AttackCard {
        @Override
        public String name() {
            return "让我摸摸看！";
        }

        @Override
        public String desc() {
            return String.format("对敌方单体造成%s点伤害「摸一下！真的就一下！」", baseDamage());
        }

        @Override
        public double baseDamage() {
            return 3;
        }
    }

}
