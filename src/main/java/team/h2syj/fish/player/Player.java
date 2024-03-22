package team.h2syj.fish.player;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.AttackCard;
import team.h2syj.fish.core.Renderer;

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
