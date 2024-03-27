package team.h2syj.fish.core;

import java.util.List;

/**
 * 战斗事件
 */
public interface BattlefieldEvent {

    /**
     * 进入战斗事件
     */
    interface BaseBattlefieldEvent extends BattlefieldEvent {
        enum Type {
            进入战斗,
            离开战斗;
        }

        void process(Type type);
    }

    /**
     * 回合事件
     */
    interface TurnBattlefieldEvent extends BattlefieldEvent {
        enum Type {
            回合开始,
            回合结束;
        }

        void process(Type type, Biological target);
    }

    /**
     * 卡牌事件
     */
    interface CardBattlefieldEvent extends BattlefieldEvent {
        enum Type {
            使用卡牌之前,
            使用卡牌之后;
        }

        void process(Type type, Card card, Biological self, List<Biological> target);
    }

    interface DiedBattlefieldEvent extends BattlefieldEvent {
        enum Type {
            死亡前, // 复活、Boss二阶段需要这个
            死亡后;
        }

        void process(Type type, Biological target);
    }

}
