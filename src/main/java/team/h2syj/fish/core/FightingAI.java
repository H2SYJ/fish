package team.h2syj.fish.core;

import java.util.List;
import java.util.Optional;

import team.h2syj.fish.core.Biological.FightingState;
import team.h2syj.fish.core.Biological.State;
import team.h2syj.fish.core.Card.AttackCard;
import team.h2syj.fish.core.Card.BuffCard;
import team.h2syj.fish.core.Card.DeBuffCard;
import team.h2syj.fish.core.Card.MagicCard;
import team.h2syj.fish.core.Renderer.ColorList;
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
        this.renderer = new Renderer(String.format("%s 回合开始", self.getName()));
    }

    public void action() {
        if (self.state != State.正常) {
            renderer.newLine().color(ColorList.red_cochineal).print("暂时无法行动").end();
            return;
        }
        FightingState fightingState = self.fightingState;
        while (true) {
            Optional<Card> optCard;
            List<Card> list = getUsedCardList(fightingState);
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
                break; // 使用了攻击卡就退出
            break; // 没有一张卡可以用
        }
        new Renderer(String.format("%s 回合结束", self.getName()));
    }

    public void execute(Card card) {
        List<Biological> targetList = getTargetList(card);
        for (Biological biological : targetList) {
            renderer.print("对 %s 使用 ", biological.getName()).color(card.getColor()).print(card).end();
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
