package com.honeycomb.util.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * 指定属性的别名
 * 用于：
 *   1.{@link com.honeycomb.util.BeanUtil#copyPropertiesByMap(Map, Object, String...)}
 * @author honeycomb
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Alias {

    /**
     * 属性别名
     */
    String value() default "";
}
