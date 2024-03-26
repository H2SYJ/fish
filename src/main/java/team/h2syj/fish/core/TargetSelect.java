package team.h2syj.fish.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public interface TargetSelect {

    Biological select();

    /**
     * 敌方选择器
     */
    interface EnemyTargetSelect extends TargetSelect {
        @Override
        default Biological select() {
            Renderer renderer = new Renderer("选择目标");
            Battlefield battlefield = Runtime.getBattlefield().orElseThrow();
            List<Biological> monsters = battlefield.getMonsters();
            List<Choose> targetChooses = new ArrayList<>();
            AtomicReference<Biological> select = new AtomicReference<>();
            for (int j = 0; j < monsters.size(); j++) {
                Biological monster = monsters.get(j);
                renderer.print("%s）", j + 1).print(monster).end();
                targetChooses.add(new Choose(String.valueOf(j + 1), s -> select.set(monster)));
            }
            Controller controller;
            do {
                controller = new Controller("选择目标");
                for (Choose choose : targetChooses) {
                    controller.next(choose);
                }
            } while (!controller.isMatch());
            return select.get();
        }
    }

    /**
     * 友方选择器
     */
    interface FriendlyTargetSelect extends TargetSelect {
        @Override
        default Biological select() {
            Renderer renderer = new Renderer("选择目标");
            Battlefield battlefield = Runtime.getBattlefield().orElseThrow();
            List<Biological> list = battlefield.getFriends();
            List<Choose> targetChooses = new ArrayList<>();
            AtomicReference<Biological> select = new AtomicReference<>();
            for (int j = 0; j < list.size(); j++) {
                Biological target = list.get(j);
                renderer.print("%s）", j + 1).print(target).end();
                targetChooses.add(new Choose(String.valueOf(j + 1), s -> select.set(target)));
            }
            Controller controller;
            do {
                controller = new Controller("选择目标");
                for (Choose choose : targetChooses) {
                    controller.next(choose);
                }
            } while (!controller.isMatch());
            return select.get();
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
