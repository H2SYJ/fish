package team.h2syj.fish.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Utils {

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static <T> T random(List<T> list) {
        return list.get(random(0, list.size() - 1));
    }

    public static int hexToInt(String hexStr) {
        return Integer.parseInt(hexStr, 16);
    }

    /**
     * <pre>
     * Utils.isEmpty(null)      = true
     * Utils.isEmpty("")        = true
     * Utils.isEmpty(" ")       = false
     * Utils.isEmpty("bob")     = false
     * Utils.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str 目标字符串
     * @return 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断集合为空
     *
     * @param values 目标集合
     * @return 判断目标集合是否为空
     */
    public static boolean isEmpty(Collection<?> values) {
        return values == null || values.isEmpty();
    }

    /**
     * 判断集合是否为空
     *
     * @param values 目标数组
     * @return 判断目标数组是否为空
     */
    public static boolean isEmpty(Object[] values) {
        return values == null || values.length == 0;
    }

    /**
     * 判断Map为空
     *
     * @param map 目标Map
     * @return 判断目标Map是否为空，为NULL或键值对个数为0
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * <pre>
     * Utils.isNotEmpty(null)      = false
     * Utils.isNotEmpty("")        = false
     * Utils.isNotEmpty(" ")       = true
     * Utils.isNotEmpty("bob")     = true
     * Utils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param str 目标字符串
     * @return 判断字符串不为空
     */
    public static boolean isNotEmpty(String str) {
        return !Utils.isEmpty(str);
    }

}
