package team.h2syj.fish.core;

import java.util.ArrayList;
import java.util.List;

// 卡组
public class Deck {
    private final List<Card> cards = new ArrayList<>();

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
