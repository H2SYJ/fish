package team.h2syj.fish.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Rarity {

    int normal = 1; // 普通品质
    int rare = 2; // 稀有品质
    int legend = 3; // 传说品质

    /**
     * 物品稀有等级 </br>
     * 暂定 1-3 级
     *
     * @return 物品稀有等级
     */
    int value();

}
