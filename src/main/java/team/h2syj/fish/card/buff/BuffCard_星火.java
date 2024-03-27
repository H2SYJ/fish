package team.h2syj.fish.card.buff;

import java.util.List;

import team.h2syj.fish.buff.Buff.AttackBeforeBuff;
import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.BuffCard;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.TargetSelect.SelfTargetSelect;
import team.h2syj.fish.debuff.turnBefore.TurnBeforeDeBuff_灼烧;
import team.h2syj.fish.utils.Utils;

/**
 * 姬子 施放攻击后，有50%的基础概率使敌方目标陷入灼烧状态，持续2回合。 灼烧状态下，敌方目标每回合开始时受到等同于姬子30%攻击力的火属性持续伤害。
 */
@Rarity(Rarity.rare)
public class BuffCard_星火 extends BuffCard implements SelfTargetSelect {

    @Override
    public String name() {
        return "星火";
    }

    @Override
    public String desc() {
        return "施放攻击前，有50%的基础概率使敌方目标陷入灼烧状态，持续2回合。灼烧状态下，敌方目标每回合开始时受到3点火属性持续伤害。";
    }

    @Override
    public int cost() {
        return 3;
    }

    @Override
    public void process(Biological self, List<Biological> target) {
        self.addBuff(new Buff_星火());
    }

    public class Buff_星火 extends AttackBeforeBuff {
        public Buff_星火() {
            super(Utils.INFINITE);
        }

        @Override
        public String name() {
            return "星火";
        }

        @Override
        public String desc() {
            return "施放攻击前，有50%的基础概率使敌方目标陷入灼烧状态，持续2回合。灼烧状态下，敌方目标每回合开始时受到3点火属性持续伤害。";
        }

        @Override
        public void execute(Biological attacker, List<Biological> target) {
            for (Biological biological : target) {
                int random = Utils.random(0, 1);
                if (random == 1) {
                    biological.addDeBuff(new TurnBeforeDeBuff_灼烧(2, 3));
                }
            }
        }
    }

}
