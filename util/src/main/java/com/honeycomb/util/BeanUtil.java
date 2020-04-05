package com.honeycomb.util;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.honeycomb.util.anno.Alias;
import com.honeycomb.util.anno.Ignore;

import java.lang.reflect.Field;
import java.util.*;

/**
 * bean工具类
 *
 * @author honeycomb
 * @version 1.0.0
 */
public class BeanUtil {

    /**
     * 将map中的value赋值到指定对象{@code target}的属性中
     *
     * @param map              map
     * @param target           指定对象
     * @param ignoreProperties 不需要赋值的对象
     */
    public static void copyPropertiesByMap(Map map, Object target, String... ignoreProperties) {

        if (CollectionUtil.isEmpty(map) || target == null) {
            return;
        }

        MethodAccess targetMethodAccess = MethodAccess.get(target.getClass());
        Field[] fields = target.getClass().getDeclaredFields();
        List<String> ignorePropertiesList = ignoreProperties == null ? Collections.emptyList() : Arrays.asList(ignoreProperties);

        Arrays.stream(fields).filter(field -> !ignorePropertiesList.contains(field.getName()))
                .filter(field -> field.getAnnotation(Ignore.class) == null)
                .forEach(field -> {
                    Alias alias = field.getAnnotation(Alias.class);
                    String value = alias == null ? "" : alias.value();
                    Object sourceFieldValue = null;
                    if (StringUtil.isNotEmpty(value)) {
                        sourceFieldValue = map.get(value);
                    } else {
                        sourceFieldValue = map.get(field.getName());
                    }
                    if (sourceFieldValue != null) {
                        int targetMethodPosi = targetMethodAccess.getIndex("set".concat(StringUtil.pascalCase(field.getName())), field.getType());
                        targetMethodAccess.invoke(target, targetMethodPosi, sourceFieldValue);
                    }
                });
    }

    /**
     * 将对象属性转化为{@code Map<String, String>}</br>
     * 非String对象会调用其{@code toString()}方法
     *
     * @param obj              对象
     * @param ignoreProperties 不需要处理的属性
     * @return 对象属性map集合
     */
    public static Map<String, String> toMap(Object obj, String... ignoreProperties) {

        if (obj == null) {
            return Collections.emptyMap();
        }

        MethodAccess methodAccess = MethodAccess.get(obj.getClass());
        Field[] fields = obj.getClass().getDeclaredFields();
        List<String> ignorePropertiesList = ignoreProperties == null ? Collections.emptyList() : Arrays.asList(ignoreProperties);
        Map<String, String> propertyMap = new HashMap<>(16);

        Arrays.stream(fields).filter(field -> !ignorePropertiesList.contains(field.getName()))
                .filter(field -> field.getAnnotation(Ignore.class) == null)
                .forEach(field -> {
                    String prefix = "get";
                    if (ReflectionUtil.isBoolean(field)) {
                        prefix = "is";
                    }

                    String key = Optional.ofNullable(field.getAnnotation(Alias.class)).map(Alias::value).orElse(field.getName());
                    String getMethod = prefix + StringUtil.pascalCase(field.getName());
                    String value = Optional.ofNullable(methodAccess.invoke(obj, getMethod)).map(Object::toString).orElse("");

                    propertyMap.put(key, value);
                });
        return propertyMap;
    }

    private BeanUtil() {
        throw new IllegalStateException("Utility class");
    }
}

