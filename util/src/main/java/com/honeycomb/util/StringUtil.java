package com.honeycomb.util;

import com.alibaba.fastjson.PropertyNamingStrategy;

/**
 * 字符串工具类
 *
 * @author honeycomb
 * @version 1.0.0
 */
public class StringUtil {

    public static final String EMPTY = "";

    /**
     * 字符串格式化——帕斯卡命名法
     * <pre>
     * 例如：
     * personId 转化为 PersonId
     * </pre>
     *
     * @param str 需要格式化的字符串
     * @return 格式化后的字符串
     * @see PropertyNamingStrategy#PascalCase
     */
    public static String pascalCase(String str) {
        if (isBlank(str))
            return str;
        return PropertyNamingStrategy.PascalCase.translate(str);
    }

    /**
     * 字符串格式化——蛇式命令法
     * <pre>
     *     例如：
     *     personId 转化为 person_id
     * </pre>
     *
     * @param str 需要格式化的字符串
     * @return 格式化后的字符串
     */
    public static String snakeCase(String str) {
        if (isBlank(str))
            return str;
        return PropertyNamingStrategy.SnakeCase.translate(str);
    }

    /**
     * 判断字符串是否为null或者空格串
     * <pre>
     * StringUtils.isBlank(null)             = true
     * StringUtils.isBlank("")               = true
     * StringUtils.isBlank("   ")            = true
     * StringUtils.isBlank("bob")            = false
     * </pre>
     *
     * @param str 字符串
     * @return {@code true} 是 {@code false} 不是
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 非空格串判断
     * <pre>
     * StringUtils.isBlank(null)             = false
     * StringUtils.isBlank("")               = false
     * StringUtils.isBlank("   ")            = false
     * StringUtils.isBlank("bob")            = true
     * </pre>
     *
     * @param str 字符串
     * @return {@code true} 是 {@code false} 不是
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断字符串是否为null或者空字符串
     * <pre>
     * StringUtils.isEmpty(null)             = true
     * StringUtils.isEmpty("")               = true
     * StringUtils.isEmpty("   ")            = false
     * StringUtils.isEmpty("bob")            = false
     * </pre>
     *
     * @param str 字符串
     * @return {@code true} 是 {@code false} 不是
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 非空字符串检验
     * <pre>
     *  StringUtils.isEmpty(null)             = false
     *  StringUtils.isEmpty("")               = false
     *  StringUtils.isEmpty("   ")            = true
     *  StringUtils.isEmpty("bob")            = true
     * </pre>
     *
     * @param str 字符串
     * @return {@code true} 是 {@code false} 不是
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    private StringUtil() {
        throw new IllegalStateException("Utility class");
    }
}
