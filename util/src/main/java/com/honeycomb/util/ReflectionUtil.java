package com.honeycomb.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ReflectionUtil {

    private static final Logger log = LogManager.getLogger(ReflectionUtil.class);

    /**
     * 根据传入的方法名字符串，获取对应的方法
     *
     * @param clazz          类的Class对象
     * @param name           方法名
     * @param parameterTypes 方法的形参对应的Class类型【可以不写】
     * @return 方法对象【Method类型】
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            if (log.isDebugEnabled())
                log.error("在类[" + clazz.getName() + "]中不存在方法名为" + name, e);
        }
        return null;
    }

    /**
     * 根据传入的方法对象，调用对应的方法
     *
     * @param method 方法对象
     * @param obj    要调用的实例对象【如果是调用静态方法，则可以传入null】
     * @param args   传入方法的实参【可以不写】
     * @return 方法的返回值【没有返回值，则返回null】
     */
    public static Object invokeMethod(Method method, Object obj, Object... args) {
        method.setAccessible(true);
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("在类[" + Optional.ofNullable(obj).map(Object::getClass).map(Class::getName).orElse("静态类") + "]中调用方法名为" + method.getName() + "的方法出错", e);
        }
        return null;
    }

    /**
     * 判断一个属性是否为boolean/Boolean类型
     * <p>
     * 当{@code field}为null则返回{@code false}
     * </p>
     *
     * @param field 属性对象
     * @return {@code true} 是 {@code false} 不是
     * @see Field
     */
    public static boolean isBoolean(Field field) {
        if (field == null)
            return false;
        return field.getType().isAssignableFrom(boolean.class) || field.getType().isAssignableFrom(Boolean.class);
    }

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
