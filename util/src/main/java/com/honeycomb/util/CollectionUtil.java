package com.honeycomb.util;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 *
 * @author honeycomb
 * @version 1.0.0
 */
public class CollectionUtil {

    /**
     * 判断集合是否为空
     *
     * @param collection 集合对象
     * @param <T>        集合类型，集合类型是{@link Collection}的子类
     * @return {@code true} 是 {@code false} 不是
     */
    public static <T extends Collection> boolean isEmpty(T collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 非空集合判断
     *
     * @param collection 集合对象
     * @param <T>        集合类型，集合类型是{@link Collection}的子类
     * @return {@code true} 是 {@code false} 不是
     */
    public static <T extends Collection> boolean isNotEmpty(T collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断集合是否为空
     *
     * @param map 集合对象
     * @param <T> 集合类型，集合类型是{@link Map}的子类
     * @return {@code true} 是 {@code false} 不是
     */
    public static <T extends Map> boolean isEmpty(T map) {
        return map == null || map.isEmpty();
    }

    /**
     * 非空集合判断
     *
     * @param map 集合对象
     * @param <T> 集合类型，集合类型是{@link Map}的子类
     * @return {@code true} 是 {@code false} 不是
     */
    public static <T extends Map> boolean isNotEmpty(T map) {
        return !isEmpty(map);
    }
}
