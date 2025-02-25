package slx.window.main;

import java.util.HashMap;
import java.util.Map;

/**
 * SLX正文:元件容器
 */
public class SlxContext {
    /**
     * 元件容器
     */
    private static Map<Class<?>, Object> elements = new HashMap<>();

    /**
     * 增加元件
     * @param clazz 元件class
     * @param element 元件
     */
    public static void addElement(Class<?> clazz, Object element) {
        elements.put(clazz,element);
    }

    /**
     * 获取元件
     * @param clazz 元件类
     * @return 元件
     * @param <T> 元件类型
     */
    public static  <T> T getElement(Class<T> clazz) {
        return (T) elements.get(clazz);
    }

    /**
     * 获取全部元件
     * @return 元件
     */
    public static Map<Class<?>, Object> getElements() {
        return elements;
    }
}
