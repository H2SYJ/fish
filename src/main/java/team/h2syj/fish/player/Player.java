package team.h2syj.fish.player;

import java.util.List;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.AttackCard;
import team.h2syj.fish.utils.DamageCalculator;

public class Player extends Biological {
    public Player(double hp) {
        super(20d);
        deck.add(new NormalAttackCard());
        deck.add(new NormalAttackCard());
        deck.add(new NormalAttackCard());
        deck.add(new NormalAttackCard());
    }


    public static class NormalAttackCard extends AttackCard {
        @Override
        public String name() {
            return "让我摸摸看！";
        }

        @Override
        public String desc() {
            return "对敌方单体造成3点伤害【摸一下！真的就一下！】";
        }

        @Override
        public void execute(Biological self, List<Biological> target) {
            for (Biological biological : target) {
                double damage = DamageCalculator.calculate(3, self, biological);
                biological.injuried(damage);
            }
        }
    }

}
