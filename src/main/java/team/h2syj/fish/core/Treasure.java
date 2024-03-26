package team.h2syj.fish.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import cn.hutool.core.util.ClassUtil;
import team.h2syj.fish.core.Renderer.ColorList;
import team.h2syj.fish.utils.BeanUtils;
import team.h2syj.fish.utils.BeanUtils.BeanHelper;
import team.h2syj.fish.utils.Utils;

/**
 * 宝物奖励
 */
public interface Treasure {

    default int getColor() {
        return getColor(this.getClass());
    }

    class CardTreasure {
        private static final Map<Integer, List<Class<?>>> cards = new ConcurrentHashMap<>();

        static {
            for (Class<?> clazz : BeanUtils.findClasses(Card.class)) {
                if (!ClassUtil.isNormalClass(clazz))
                    continue;
                Integer rarity = BeanUtils.findAnnotation(clazz, Rarity.class).map(Rarity::value).orElse(1);
                cards.computeIfAbsent(rarity, i -> new ArrayList<>()).add(clazz);
            }
        }
    }

    /**
     * 从所有卡牌中获取指定数量个随机卡牌返回
     * 
     * @param rarity 卡牌的稀有等级
     * @param count  卡牌数量
     * @return 随机List
     */
    static List<Card> getCards(int rarity, int count) {
        List<Class<?>> list = CardTreasure.cards.getOrDefault(rarity, List.of());
        return IntStream.range(0, count)
                .mapToObj(i -> Utils.random(list))
                .map(BeanUtils::getBeanHelper)
                .map(BeanHelper::<Card>newInstance)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    static int getColor(Class<? extends Treasure> clazz) {
        Integer rarity = BeanUtils.findAnnotation(clazz, Rarity.class).map(Rarity::value).orElse(1);
        return switch (rarity) {
        case Rarity.rare -> ColorList.purple;
        case Rarity.legend -> ColorList.gold;
        default -> ColorList.gray;
        };
    }

}
