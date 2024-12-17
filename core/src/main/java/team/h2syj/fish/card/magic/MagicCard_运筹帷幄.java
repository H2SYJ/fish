package team.h2syj.fish.card.magic;

import java.util.List;

import team.h2syj.fish.core.Biological;
import team.h2syj.fish.core.Card.MagicCard;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.TargetSelect.SelfTargetSelect;

@Rarity(Rarity.rare)
public class MagicCard_运筹帷幄 extends MagicCard implements SelfTargetSelect {

    @Override
    public String name() {
        return "运筹帷幄";
    }

    @Override
    public String desc() {
        return "抓2张牌";
    }

    @Override
    public int cost() {
        return 1;
    }

    @Override
    public void process(Biological self, List<Biological> target) {
        self.getFightingState().gacha(2);
    }

}
