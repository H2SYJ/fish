package team.h2syj.fish.monster;

import java.util.List;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.AttackCard;
import team.h2syj.fish.debuff.turnBefore.TurnBeforeDeBuff_灼烧;
import team.h2syj.fish.utils.DamageCalculator;
import team.h2syj.fish.utils.Utils;

public class Monster_火焚灾影 extends Monster {

    public Monster_火焚灾影(double hp) {
        super(15);
        deck.add(new AttackCard_灼炎重击());
        deck.add(new AttackCard_灼炎重击());
        deck.add(new AttackCard_灼炎重击());
        deck.add(new AttackCard_灼炎重击());
    }

    public static class AttackCard_灼炎重击 extends AttackCard {
        final int baseDamage = 2;
        final int dotDamage = 1;
        final int dotTurn = 2;

        @Override
        public String name() {
            return "灼炎重击";
        }

        @Override
        public String desc() {
            return "对指定敌方单体造成%d点火属性伤害，并有80%概率使目标陷入灼烧状态".formatted(baseDamage);
        }

        @Override
        public int cost() {
            return 0;
        }

        @Override
        public double baseDamage(Biological self, List<Biological> target) {
            return baseDamage;
        }

        @Override
        public void process(Biological self, List<Biological> target) {
            for (Biological biological : target) {
                double damage = DamageCalculator.calculate(baseDamage(self, target), self, biological);
                biological.injuried(damage);

                int random = Utils.random(1, 10);
                if (random <= 8) {
                    biological.addDeBuff(new TurnBeforeDeBuff_灼烧(dotTurn, dotDamage));
                }
            }
        }
    }

    public static class AttackCard_灼炎冲击波 extends AttackCard {
        final int baseDamage = 7;
        final int dotDamage = 1;
        final int dotTurn = 3;

        @Override
        public String name() {
            return "灼炎冲击波";
        }

        @Override
        public String desc() {
            return "对指定敌方单体造成%d点火属性伤害，并使目标陷入灼烧状态".formatted(baseDamage);
        }

        @Override
        public int cost() {
            return 2;
        }

        @Override
        public double baseDamage(Biological self, List<Biological> target) {
            return baseDamage;
        }

        @Override
        public void process(Biological self, List<Biological> target) {
            for (Biological biological : target) {
                double damage = DamageCalculator.calculate(baseDamage(self, target), self, biological);
                biological.injuried(damage);
                biological.addDeBuff(new TurnBeforeDeBuff_灼烧(dotTurn, dotDamage));
            }
        }
    }

}
