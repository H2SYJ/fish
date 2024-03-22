package team.h2syj.fish.monster;

import java.util.List;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.AttackCard;
import team.h2syj.fish.core.Card.MagicCard;

public class Slime extends Biological {
    public Slime() {
        super(5);
        deck.add(new NormalAttackCard());
        deck.add(new NormalAttackCard());
        deck.add(new NormalAttackCard());
        deck.add(new CuteCard());
        deck.add(new CuteCard());
        deck.add(new CuteCard());
    }


    public static class CuteCard extends MagicCard {
        @Override
        public String name() {
            return "呆呆";
        }

        @Override
        public String desc() {
            return "发呆~";
        }

        @Override
        public void execute(Biological self, List<Biological> target) {
        }
    }

    public static class NormalAttackCard extends AttackCard {
        @Override
        public String name() {
            return "撞撞";
        }

        @Override
        public String desc() {
            return String.format("对敌方单体造成%s点伤害", baseDamage());
        }

        @Override
        public double baseDamage() {
            return 1;
        }
    }

}
