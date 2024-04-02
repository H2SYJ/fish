package team.h2syj.fish.monster;

import java.util.List;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.AttackCard;

public class Monster_史莱姆 extends Monster {
    public Monster_史莱姆() {
        super(5);
        setName("史莱姆");
        deck.add(new AttackCard_撞撞());
        deck.add(new AttackCard_撞撞());
        deck.add(new AttackCard_撞撞());
        deck.add(new AttackCard_撞撞());
        deck.add(new AttackCard_撞撞());
        deck.add(new AttackCard_撞撞());
        deck.add(new AttackCard_撞撞());
        deck.add(new AttackCard_撞撞());
        deck.add(new AttackCard_呆呆());
        deck.add(new AttackCard_呆呆());
        deck.add(new AttackCard_呆呆());
    }

    public static class AttackCard_呆呆 extends AttackCard {
        @Override
        public String name() {
            return "呆呆";
        }

        @Override
        public String desc() {
            return "发呆~";
        }

        @Override
        public double baseDamage(Biological self, List<Biological> target) {
            return 0;
        }

        @Override
        public int cost() {
            return 0;
        }
    }

    public static class AttackCard_撞撞 extends AttackCard {
        @Override
        public String name() {
            return "撞撞";
        }

        @Override
        public String desc() {
            return "对敌方单体造成1点伤害";
        }

        @Override
        public double baseDamage(Biological self, List<Biological> target) {
            return 1;
        }

        @Override
        public int cost() {
            return 0;
        }
    }

}
