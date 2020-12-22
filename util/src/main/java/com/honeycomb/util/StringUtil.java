package com.honeycomb.util;

import com.alibaba.fastjson.PropertyNamingStrategy;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author honeycomb
 * @version 1.0.0
 */
public class StringUtil {

    public static final String EMPTY = "";

    // ?表示最小匹配，去掉?就是贪婪匹配
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("#\\{(.)*?\\}");

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

    /**
     * 填充占位符
     * 占位符格式:#{XXX.XXX}
     * ps:字符串会在前后增加单引号
     *
     * @param str   字符串
     * @param param 参数
     * @return 填充后字符串
     */
    public static String fillPlaceholder(String str, Map<String, Object> param) {
        if (isBlank(str) || CollectionUtils.isEmpty(param)) {
            return str;
        }
        EvaluationContext context = new StandardEvaluationContext();
        param.forEach(context::setVariable);
        ExpressionParser parser = new SpelExpressionParser();

        Matcher m = PLACEHOLDER_PATTERN.matcher(str);
        while (m.find()) {
            final String group = m.group();
            final String replace = group.replace("{", "").replace("}", "");
            final Class<?> valueType = parser.parseExpression(replace).getValueType(context);
            String value = parser.parseExpression(replace).getValue(context, String.class);
            if (Objects.nonNull(value)) {
                if (String.class.isAssignableFrom(valueType)) {
                    value = "'".concat(value).concat("'");
                }
                str = str.replace(group, value);
            }
        }
        return str;
    }

    private StringUtil() {
        throw new IllegalStateException("Utility class");
    }
}
