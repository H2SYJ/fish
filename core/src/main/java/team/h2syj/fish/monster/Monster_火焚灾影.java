package team.h2syj.fish.monster;

import java.util.List;

import cn.hutool.core.util.NumberUtil;
import team.h2syj.fish.buff.Buff.DamageDownBuff;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.AttackCard;
import team.h2syj.fish.core.Card.BuffCard;
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
        deck.add(new AttackCard_灼炎重击());
        deck.add(new AttackCard_灼炎重击());
        deck.add(new AttackCard_灼炎重击());
        deck.add(new AttackCard_灼炎冲击波());
        deck.add(new AttackCard_灼炎冲击波());
        deck.add(new BuffCard_熔岩护体());
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
                biological.injured(self, damage);

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
                biological.injured(self, damage);
                biological.addDeBuff(new TurnBeforeDeBuff_灼烧(dotTurn, dotDamage));
            }
        }
    }

    public class BuffCard_熔岩护体 extends BuffCard {
        @Override
        public String name() {
            return "熔岩护体";
        }

        @Override
        public String desc() {
            return "获得三层【火焰外甲】，受到伤害降低10%，并使对方受到灼烧效果";
        }

        @Override
        public int cost() {
            return 2;
        }

        @Override
        public void process(Biological self, List<Biological> target) {
            self.addBuff(new DamageDownBuff_火焰外甲());
        }
    }

    public class DamageDownBuff_火焰外甲 extends DamageDownBuff {
        final int dotDamage = 2;
        final int dotTurn = 1;

        public DamageDownBuff_火焰外甲() {
            super(Utils.INFINITE);
        }

        @Override
        public String name() {
            return "火焰外甲";
        }

        @Override
        public String desc() {
            return "受到伤害降低10%，并使对方受到灼烧效果";
        }

        @Override
        public double value() {
            return 3;
        }

        @Override
        public double down(double baseDamage, double curDamage, Biological attacker, Biological target) {
            attacker.getDeBuffs().add(new TurnBeforeDeBuff_灼烧(dotTurn, dotDamage));
            diffValue(target, 1);
            return -NumberUtil.mul(baseDamage, (0.1));
        }
    }

}
