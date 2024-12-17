package team.h2syj.fish.monster;

import java.util.List;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.AttackCard;
import team.h2syj.fish.core.Card.MagicCard;
import team.h2syj.fish.core.Runtime;
import team.h2syj.fish.utils.Utils;

public class Monster_哥布林 extends Monster {
    public Monster_哥布林() {
        super(10);
        setName("哥布林");
        deck.add(new AttackCard_挥击());
        deck.add(new AttackCard_挥击());
        deck.add(new AttackCard_挥击());
        deck.add(new AttackCard_挥击());
        deck.add(new AttackCard_挥击());
        deck.add(new AttackCard_挥击());
        deck.add(new AttackCard_挥击());
        deck.add(new MagicCard_尖叫());
    }

    public static class MagicCard_尖叫 extends MagicCard {
        @Override
        public String name() {
            return "尖叫";
        }

        @Override
        public String desc() {
            return "有5%的概率召唤一个哥布林到场上";
        }

        @Override
        public int cost() {
            return 2;
        }

        @Override
        public void process(Biological self, List<Biological> target) {
            if (Utils.random(1, 100) <= 5) {
                Runtime.getBattlefield().ifPresent(item -> item.addFriends(self, new Monster_哥布林()));
            }
        }
    }

    public static class AttackCard_挥击 extends AttackCard {
        @Override
        public String name() {
            return "挥击";
        }

        @Override
        public String desc() {
            return "对敌方单体造成2点伤害";
        }

        @Override
        public int cost() {
            return 0;
        }

        @Override
        public double baseDamage(Biological self, List<Biological> target) {
            return 2;
        }
    }

}
