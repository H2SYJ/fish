package team.h2syj.fish.player;

import java.util.List;
import team.h2syj.fish.core.Battlefield;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card;
import team.h2syj.fish.core.Card.AttackCard;
import team.h2syj.fish.core.Controller;
import team.h2syj.fish.core.Deck;
import team.h2syj.fish.core.Renderer;
import team.h2syj.fish.core.Runtime;
import team.h2syj.fish.monster.Monster;

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
        List<Card> cards = curDeck.getCards();
        for (int i = 0; i < cards.size(); i++) {
            renderer.print("%s）", i + 1).println(cards.get(i));
        }

        if (state != State.正常) {
            renderer.println("你当前无法行动");
            return;
        }

        Controller controller = new Controller("等待行动");
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            controller.next(String.valueOf(i + 1), input -> {
                Battlefield battlefield = Runtime.getBattlefield().orElseThrow();
                List<Monster> monsters = battlefield.getMonsters();
                for (int j = 0; j < monsters.size(); j++) {
                    Monster monster = monsters.get(j);
                    renderer.print("%s）", j + 1).println(monster);
                }
                Controller targetController = new Controller("选择目标");
                for (int j = 0; j < monsters.size(); j++) {
                    Monster monster = monsters.get(j);
                    targetController.next(String.valueOf(j + 1), s -> card.execute(this, List.of(monster)));
                }
            });
        }
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
