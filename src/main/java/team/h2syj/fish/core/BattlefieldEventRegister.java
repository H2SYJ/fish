package team.h2syj.fish.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import team.h2syj.fish.monster.Monster;
import team.h2syj.fish.utils.BeanUtils;
import team.h2syj.fish.utils.Utils;

public class BattlefieldEventRegister {

    private final Map<Class<?>, Set<BattlefieldEvent>> map = new ConcurrentHashMap<>();

    public void scan(Battlefield battlefield) {
        register(battlefield.getP1());
        register(battlefield.getP2());
        for (Monster monster : battlefield.getMonsters()) {
            register(monster);
        }
    }

    public void register(Object obj) {
        if (obj == null)
            return;
        List<Object> objects = BeanUtils.loadAllObj(obj);
        for (Object item : objects) {
            if (item instanceof BattlefieldEvent event) {
                List<Class<?>> interfaces = BeanUtils.loadAllInterfaces(event.getClass());
                for (Class<?> anInterface : interfaces) {
                    if (BeanUtils.loadAllInterfaces(anInterface).contains(BattlefieldEvent.class))
                        map.computeIfAbsent(anInterface, key -> new LinkedHashSet<>()).add(event);
                }
            }
        }
    }

    public void triggerEvent(Class<? extends BattlefieldEvent> clazz, Object... args) {
        Set<BattlefieldEvent> set = map.get(clazz);
        if (Utils.isEmpty(set))
            return;
        Optional<Method> optMethod = BeanUtils.getBeanHelper(clazz).getMethods().stream().findFirst();
        if (optMethod.isEmpty())
            return;
        Method method = optMethod.get();
        for (BattlefieldEvent event : set) {
            try {
                method.invoke(event, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
