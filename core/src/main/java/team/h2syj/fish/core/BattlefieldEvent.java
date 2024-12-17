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

        /**
         * @param card   使用的卡牌
         * @param use    使用者
         * @param target 目标
         */
        void process(Type type, Card card, Biological use, List<Biological> target);
    }

    interface AttackBattlefieldEvent extends BattlefieldEvent {
        enum Type {
            造成伤害之前,
            造成伤害之后;
        }

        /**
         * @param attacker 造成伤害的对象：可能是 Biological、Buff、DeBuff 类
         * @param target   受到伤害的对象
         * @param damage   造成的伤害
         */
        void process(Type type, Object attacker, Biological target, double damage);
    }

    interface InjuriedBattlefieldEvent extends BattlefieldEvent {
        enum Type {
            受到伤害之前,
            受到伤害之后;
        }

        /**
         * @param attacker 造成伤害的对象：可能是 Biological、Buff、DeBuff 类
         * @param target   受到伤害的对象
         * @param damage   受到的伤害
         */
        void process(Type type, Object attacker, Biological target, double damage);
    }

    interface DiedBattlefieldEvent extends BattlefieldEvent {
        enum Type {
            死亡前, // 复活、Boss二阶段需要这个
            死亡后;
        }

        /**
         * @param target 死亡的对象
         */
        void process(Type type, Biological target);
    }

}
