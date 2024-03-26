package team.h2syj.fish.core;

import java.util.List;

public interface TargetSelect {

    Biological select();

    /**
     * 敌方选择器
     */
    interface EnemyTargetSelect extends TargetSelect {
        @Override
        default Biological select() {
            Battlefield battlefield = Runtime.getBattlefield().orElseThrow();
            List<Biological> monsters = battlefield.getMonsters();
            return Runtime.choose("选择目标", "选择目标", monsters);
        }
    }

    /**
     * 友方选择器
     */
    interface FriendlyTargetSelect extends TargetSelect {
        @Override
        default Biological select() {
            Battlefield battlefield = Runtime.getBattlefield().orElseThrow();
            List<Biological> friends = battlefield.getFriends();
            return Runtime.choose("选择目标", "选择目标", friends);
        }
    }

    /**
     * 选择自身
     */
    interface SelfTargetSelect extends TargetSelect {
        @Override
        default Biological select() {
            return Runtime.me();
        }
    }
}
