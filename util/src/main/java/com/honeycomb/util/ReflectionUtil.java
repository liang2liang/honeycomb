package com.honeycomb.util;

import org.springframework.core.ResolvableType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ReflectionUtil {

    /**
     * 获取指定包含泛型类的第N个泛型对象
     *
     * @param target       目标类
     * @param genericClass 包含泛型的类或者接口
     * @param number       N
     * @return
     */
    public static Object obtainDeclaredClass(Class<?> target, Class<?> genericClass, int number) {
        ResolvableType[] resolvableTypes = resolveDeclaredType(target, genericClass);
        if(number > resolvableTypes.length){
            return null;
        }
        return TryCatchUtil.safeDisposal(() -> resolvableTypes[number - 1].resolve().newInstance());
    }

    /**
     * 获取泛型对象集合
     *
     * @param target       目标类
     * @param genericClass 包含泛型的类或者接口
     * @return 泛型对象类型集合
     */
    public static ResolvableType[] resolveDeclaredType(Class<?> target, Class<?> genericClass) {
        ResolvableType resolvableType = ResolvableType.forClass(target).as(genericClass);
        if (!resolvableType.hasGenerics()) {
            return new ResolvableType[0];
        }
        return resolvableType.getGenerics();
    }

    /**
     * 判断指定范型类名集合是在在目标类中的范型中
     *
     * @param target       目标类
     * @param genericClass 包含泛型的类或者接口
     * @param matcherNames 待匹配的范型类名集合
     * @return {@code true}:匹配 </br>
     * {@code false}:不匹配
     */
    public static boolean matchDeclaredType(Class<?> target, Class<?> genericClass, String... matcherNames) {
        if (ArrayUtil.isEmpty(matcherNames)) {
            return false;
        }

        ResolvableType[] resolvableTypes = resolveDeclaredType(target, genericClass);
        if (ArrayUtil.isEmpty(resolvableTypes)) {
            return false;
        }
        List<String> list = Arrays.stream(resolvableTypes).map(ResolvableType::toString).collect(Collectors.toList());
        return list.containsAll(Arrays.asList(matcherNames));
    }

    private ReflectionUtil() {
        throw new IllegalStateException("Utility class");
    }
}
