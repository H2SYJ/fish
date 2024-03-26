package team.h2syj.fish.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

public class BeanUtils {

    private static final Map<Class<?>, BeanHelper> map = new ConcurrentHashMap<>();

    public static List<Object> loadAllObj(Object obj) {
        List<Object> result = new ArrayList<>();
        result.add(obj);

        Class<?> clazz = obj.getClass();
        List<Field> fields = loadAllField(clazz);
        for (Field field : fields) {
            try {
                Object child = field.get(obj);
                if (child == null)
                    continue;
                result.add(child);
                if (child instanceof Collection<?> collection) {
                    for (Object item : collection)
                        result.addAll(loadAllObj(item));
                }

                Class<?> childClass = child.getClass();
                if (childClass.isEnum())
                    continue;
                String packageName = childClass.getPackageName();
                if (packageName.startsWith("java"))
                    continue;
                result.addAll(loadAllObj(child));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    public static List<Field> loadAllField(Class<?> clazz) {
        List<Field> result = new ArrayList<>(getBeanHelper(clazz).getFields());
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class)
            result.addAll(loadAllField(clazz.getSuperclass()));
        return result;
    }

    public static List<Class<?>> loadAllInterfaces(Class<?> clazz) {
        List<Class<?>> result = new ArrayList<>(getBeanHelper(clazz).getInterfaces());
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class)
            result.addAll(loadAllInterfaces(clazz.getSuperclass()));
        return result;
    }

    public static <T extends Annotation> Optional<T> findAnnotation(Class<?> clazz, Class<T> annotaionClass) {
        return Optional.ofNullable(clazz.getDeclaredAnnotation(annotaionClass));
    }

    public static BeanHelper getBeanHelper(Class<?> clazz) {
        return map.computeIfAbsent(clazz, BeanHelper::new);
    }

    @Getter
    public static class BeanHelper {
        private final List<Field> fields;
        private final List<Method> methods;
        private final List<Class<?>> interfaces;
        private final Class<?> superclass;
        private final List<Annotation> annotations;

        private BeanHelper(Class<?> clazz) {
            this.fields = List.of(clazz.getDeclaredFields());
            if (!Utils.isEmpty(fields)) {
                for (Field field : fields) {
                    field.setAccessible(true);
                }
            }
            this.methods = List.of(clazz.getDeclaredMethods());
            this.interfaces = List.of(clazz.getInterfaces());
            this.superclass = clazz.getSuperclass();
            this.annotations = List.of(clazz.getDeclaredAnnotations());
        }
    }

}
