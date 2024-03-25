package team.h2syj.fish.core;

import java.util.ArrayList;
import java.util.List;

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
        this.cards.add(card);
        return this;
    }

    public Deck remove(Card card) {
        this.cards.remove(card);
        return this;
    }

    @Override
    public String toString() {
        return String.join(SystemSetting.newline, cards.stream().map(Card::toString).toList());
    }
}
