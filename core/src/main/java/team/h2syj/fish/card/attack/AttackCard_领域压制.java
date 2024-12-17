package team.h2syj.fish.card.attack;

import java.util.List;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.AoeAttackCard;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.TargetSelect.Aoe;
import team.h2syj.fish.debuff.DeBuff.DamageUpDeBuff;
import team.h2syj.fish.utils.DamageCalculator;

@Aoe(Aoe.All)
@Rarity(Rarity.legend)
public class AttackCard_领域压制 extends AoeAttackCard {
    @Override
    public String name() {
        return "领域压制";
    }

    @Override
    public String desc() {
        return "使敌方全体陷入「通解」状态，同时对敌方全体造成8伤害。「通解」状态下，敌方受到的伤害提升20%，持续2回合。";
    }

    @Override
    public int cost() {
        return 3;
    }

    @Override
    public double baseDamage(Biological self, List<Biological> target) {
        return 8;
    }

    @Override
    public void process(Biological self, Biological mainTarget, List<Biological> minorTarget) {
        double baseDamage = baseDamage(null, null);
        mainTarget.addDeBuff(new DeBuff_通解());
        mainTarget.injured(self, DamageCalculator.calculate(baseDamage, self, mainTarget));
        for (Biological biological : minorTarget) {
            biological.addDeBuff(new DeBuff_通解());
            biological.injured(self, DamageCalculator.calculate(baseDamage, self, biological));
        }
    }

    public static class DeBuff_通解 extends DamageUpDeBuff {

        public DeBuff_通解() {
            super(2);
        }

        @Override
        public String name() {
            return "通解";
        }

        @Override
        public String desc() {
            return "受到的伤害提升20%";
        }

        @Override
        public double up(double damage, Biological attacker, Biological self) {
            return damage * 0.2;
        }
    }

}
