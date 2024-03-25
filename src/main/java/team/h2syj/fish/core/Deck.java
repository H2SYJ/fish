package team.h2syj.fish.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import team.h2syj.fish.utils.Utils;

/**
 * 卡组
 */
public class Deck {
    private final List<Card> cards = new ArrayList<>();

    /**
     * 在战斗中使用，需要一个临时卡组进行增删。还需要一个空卡组当作墓地。
     *
     * @return 返回一个临时卡组
     */
    public Deck temp() {
        Deck deck = new Deck();
        for (Card card : cards) {
            deck.add(card);
        }
        return deck;
    }

    public Deck add(Card card) {
        if (card == null)
            return this;
        this.cards.add(card);
        return this;
    }

    public Optional<Card> gacha() {
        if (cards.isEmpty())
            return Optional.empty();
        int index = Utils.random(0, cards.size() - 1);
        Card card = cards.get(index);
        remove(card);
        return Optional.ofNullable(card);
    }

    public Deck remove(Card card) {
        if (card == null)
            return this;
        this.cards.remove(card);
        return this;
    }

    @Override
    public String toString() {
        return String.join(SystemSetting.newline, cards.stream().map(Card::toString).toList());
    }
}
