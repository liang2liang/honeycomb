package com.honeycomb.util;

import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 获取bean信息
 * @author maoliang
 * @version 1.0.0
 */
public class BeanInfoUtil {

    /**
     * 获取BeanInfo对象
     * @param clazz 类
     * @return BeanInfo对象
     */
    public static BeanInfo obtainBeanInfo(Class clazz){
        return Objects.isNull(clazz) ? null : TryCatchUtil.safeDisposal(() -> Introspector.getBeanInfo(clazz));
    }

    /**
     * 获取类的属性读方法 -> getXXXX
     * @param clazz 类
     * @return {@link Map.Entry#getKey()}:属性名 <br>
     *     {@link Map.Entry#getValue()}:读方法
     */
    public static Map<String, Method> obtainPropertyReadMethod(Class clazz){
        BeanInfo beanInfo = obtainBeanInfo(clazz);
        if(Objects.nonNull(beanInfo)){
            return Arrays.stream(beanInfo.getPropertyDescriptors())
                //过滤Object中的getClass方法
                .filter(propertyDescriptor -> !"class".equals(propertyDescriptor.getName()))
                .collect(Collectors.toMap(FeatureDescriptor::getName, PropertyDescriptor::getReadMethod));
        }
        return Collections.emptyMap();
    }

    private BeanInfoUtil() {
        throw new IllegalStateException("Utility class");
    }
}
