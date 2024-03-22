package team.h2syj.fish.player;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card;
import team.h2syj.fish.core.Card.AttackCard;

public class Player extends Biological {
    public Player(double hp) {
        super(20d);
        deck.add(new NormalAttackCard());
        deck.add(new NormalAttackCard());
        deck.add(new NormalAttackCard());
        deck.add(new NormalAttackCard());
    }


    public static class NormalAttackCard extends Card implements AttackCard {
        @Override
        public String name() {
            return "让我摸摸看！";
        }

        @Override
        public String desc() {
            return "对敌方单体造成3点伤害【摸一下！真的就一下！】";
        }

        @Override
        public void execute(Biological target) {

        }
    }

}
