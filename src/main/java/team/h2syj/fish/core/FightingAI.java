package team.h2syj.fish.core;

import java.util.List;
import java.util.Optional;

import team.h2syj.fish.core.Biological.FightingState;
import team.h2syj.fish.core.Card.AttackCard;
import team.h2syj.fish.core.Card.BuffCard;
import team.h2syj.fish.core.Card.DeBuffCard;
import team.h2syj.fish.core.Card.MagicCard;
import team.h2syj.fish.core.TargetSelect.EnemyTargetSelect;
import team.h2syj.fish.core.TargetSelect.FriendlyTargetSelect;
import team.h2syj.fish.core.TargetSelect.SelfTargetSelect;
import team.h2syj.fish.utils.Utils;

public class FightingAI {

    private final Biological self;
    private final List<Biological> friends;
    private final List<Biological> enemy;
    private final Renderer renderer;

    public FightingAI(Biological self, List<Biological> friends, List<Biological> enemy) {
        this.self = self;
        this.friends = friends;
        this.enemy = enemy;
        this.renderer = new Renderer(String.format("%s 开始行动", self.getName()));
    }

    public void action() {
        FightingState fightingState = self.fightingState;
        List<Card> list;
        Optional<Card> optCard;
        do {
            list = getUsedCardList(fightingState);
            // 优先挂debuff
            (optCard = deBuffCard(list)).ifPresent(this::execute);
            if (optCard.isPresent())
                continue;
            // 然后是buff
            (optCard = buffCard(list)).ifPresent(this::execute);
            if (optCard.isPresent())
                continue;
            // 魔法
            (optCard = magicCard(list)).ifPresent(this::execute);
            if (optCard.isPresent())
                continue;
            // 攻击
            (optCard = attackCard(list)).ifPresent(this::execute);
            if (optCard.isPresent())
                continue;
            // 如果使用的卡不为空并且消耗的费用大于0则继续行动
            // 费用大于0的代表行动卡，行动卡不结束回合
        } while (optCard.isPresent() && optCard.get().cost() > 0);
    }

    public void execute(Card card) {
        List<Biological> targetList = getTargetList(card);
        for (Biological biological : targetList) {
            renderer.println("对 %s 目标使用 %s", biological.getName(), card);
        }
        card.execute(self, targetList);
    }

    public List<Biological> getTargetList(Card card) {
        if (card instanceof EnemyTargetSelect) {
            // 最大的嘲讽值
            int maxTaunt = enemy.stream().mapToInt(item -> item.fightingState.getTaunt()).max().orElse(1);
            int random = Utils.random(1, maxTaunt); // 随机一个攻击的嘲讽值
            // 获取将要攻击的目标列表
            List<Biological> list = enemy.stream().filter(item -> random <= item.fightingState.getTaunt()).toList();
            // 从攻击列表中随机一个目标
            return List.of(Utils.random(list));
        } else if (card instanceof FriendlyTargetSelect) {
            return List.of(Utils.random(friends));
        } else if (card instanceof SelfTargetSelect) {
            return List.of(self);
        } else {
            return List.of();
        }
    }

    public List<Card> getUsedCardList(FightingState fightingState) {
        Deck deck = fightingState.getCurDeck();
        int action = fightingState.getAction();
        // 获取可以用卡牌列表
        return deck.stream().filter(item -> item.cost() <= action).toList();
    }

    public Optional<Card> deBuffCard(List<Card> list) {
        return list.stream().filter(item -> item instanceof DeBuffCard).findFirst();
    }

    public Optional<Card> buffCard(List<Card> list) {
        return list.stream().filter(item -> item instanceof BuffCard).findFirst();
    }

    public Optional<Card> attackCard(List<Card> list) {
        return list.stream().filter(item -> item instanceof AttackCard).findFirst();
    }

    public Optional<Card> magicCard(List<Card> list) {
        return list.stream().filter(item -> item instanceof MagicCard).findFirst();
    }

}
