package team.h2syj.fish.card.attack;

import java.util.List;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.AttackCard;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.TargetSelect.EnemyTargetSelect;

@Rarity(Rarity.legend)
public class AttackCard_安神秘法 extends AttackCard implements EnemyTargetSelect {
    @Override
    public String name() {
        return "安神秘法";
    }

    @Override
    public String desc() {
        return "对敌方单位造成10点伤害。当前生命值低于50%时造成15点伤害并回复30%生命值上限";
    }

    @Override
    public int cost() {
        return 1;
    }

    @Override
    public double baseDamage(Biological self, List<Biological> target) {
        boolean lowHp = self.curHp() < self.maxHp() * 0.5; // 是否为低血量
        if (lowHp)
            self.recover(self.maxHp() * 0.3);
        return lowHp ? 15 : 10;
    }
}
