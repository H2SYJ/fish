package team.h2syj.fish.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import team.h2syj.fish.player.Player;
import team.h2syj.fish.utils.BeanUtils;

public interface TargetSelect {

    List<Biological> select(Player self);

    /**
     * 计算Aoe
     * 
     * @param targetSelect 目标选择器
     * @param target       选择的目标
     * @param allTarget    所有目标
     * @return 在Aoe范围内的所有人，list第一个为主要目标也就是target
     */
    static List<Biological> calculatorAoe(TargetSelect targetSelect, Biological target, List<Biological> allTarget) {
        int aoe = BeanUtils.findAnnotation(targetSelect.getClass(), Aoe.class).map(Aoe::value).orElse(0);
        if (aoe == 0) // 0 直接返回
            return List.of(target);
        else if (aoe == Aoe.All) // 全体目标
            return allTarget;
        int index = allTarget.indexOf(target); // 目标所在的下标
        int startIndex = Math.max(index - aoe, 0); // aoe开始下标
        int endIndex = Math.min(index + aoe, allTarget.size()); // aoe结束下标
        List<Biological> list = new ArrayList<>();
        list.add(target); // 第一个为主要目标
        list.addAll(allTarget.subList(startIndex, endIndex)); // 后续的是次要目标
        return list;
    }

    /**
     * 敌方选择器
     */
    interface EnemyTargetSelect extends TargetSelect {
        @Override
        default List<Biological> select(Player self) {
            Battlefield battlefield = Runtime.getBattlefield().orElseThrow();
            List<Biological> monsters = battlefield.getMonsters();
            Biological target = Runtime.choose(self, "选择目标", "选择目标", monsters);
            return calculatorAoe(this, target, monsters);
        }
    }

    /**
     * 友方选择器
     */
    interface FriendlyTargetSelect extends TargetSelect {
        @Override
        default List<Biological> select(Player self) {
            Battlefield battlefield = Runtime.getBattlefield().orElseThrow();
            List<Biological> friends = battlefield.getFriends();
            Biological target = Runtime.choose(self, "选择目标", "选择目标", friends);
            return calculatorAoe(this, target, friends);
        }
    }

    /**
     * 选择自身
     */
    interface SelfTargetSelect extends TargetSelect {
        @Override
        default List<Biological> select(Player self) {
            return List.of(self);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    @interface Aoe {
        int All = 999; // 对敌方全体攻击

        /**
         * 敌人站位 </br>
         * A B C D E </br>
         * </br>
         * aoe 1 ：主要目标 B 次要目标 A,C </br>
         * aoe 2 ：主要目标 C 次要目标 A,B,D,E
         *
         * @return 攻击范围
         */
        int value();
    }

}
