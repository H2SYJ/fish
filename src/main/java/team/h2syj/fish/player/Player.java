package team.h2syj.fish.player;

import java.util.ArrayList;
import java.util.List;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card;
import team.h2syj.fish.core.Card.AttackCard;
import team.h2syj.fish.core.Choose;
import team.h2syj.fish.core.Controller;
import team.h2syj.fish.core.Deck;
import team.h2syj.fish.core.Renderer;
import team.h2syj.fish.core.TargetSelect;

public class Player extends Biological {
    public Player() {
        super(20d);
        deck.add(new NormalAttackCard());
        deck.add(new NormalAttackCard());
        deck.add(new NormalAttackCard());
        deck.add(new NormalAttackCard());
    }

    @Override
    public void action() {
        Renderer renderer = new Renderer("开始行动");
        renderer.println("当前手牌");
        Deck curDeck = fightingState.getCurDeck();
        int actionPoint = fightingState.getAction();
        List<Card> cards = curDeck.getCards();
        List<Choose> chooses = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            int cost = card.cost();
            renderer.print("%s）", i + 1);
            if (cost > actionPoint) {
                renderer.print("（🚫行动点不足）");
            } else {
                chooses.add(new Choose(String.valueOf(i + 1), input -> {
                    if (card instanceof TargetSelect targetSelect) {
                        Biological target = targetSelect.select();
                        card.execute(this, List.of(target));
                    }
                }));
            }
            renderer.println(card);
        }

        if (state != State.正常) {
            renderer.println("你当前无法行动");
            return;
        }

        Controller controller;
        do {
            controller = new Controller("等待行动");
            for (Choose choose : chooses) {
                controller.next(choose);
            }
        } while (!controller.isMatch());
    }

    public static class NormalAttackCard extends AttackCard {
        @Override
        public String name() {
            return "让我摸摸看！";
        }

        @Override
        public String desc() {
            return String.format("对敌方单体造成%s点伤害【摸一下！真的就一下！】", baseDamage());
        }

        @Override
        public double baseDamage() {
            return 3;
        }
    }

}
