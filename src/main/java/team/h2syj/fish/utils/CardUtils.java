package team.h2syj.fish.utils;

import team.h2syj.fish.core.Card;
import team.h2syj.fish.core.Rarity;
import team.h2syj.fish.core.Renderer.ColorList;

public class CardUtils {

    public static int getCardColor(Card card) {
        Integer rarity = BeanUtils.findAnnotation(card.getClass(), Rarity.class).map(Rarity::value).orElse(1);
        return switch (rarity) {
        case Rarity.rare -> ColorList.purple;
        case Rarity.legend -> ColorList.gold;
        default -> ColorList.gray;
        };
    }

}
