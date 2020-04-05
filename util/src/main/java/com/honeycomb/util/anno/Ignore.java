package com.honeycomb.util.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * 指定不需要处理属性
 * 用于：
 *   1.{@link com.honeycomb.util.BeanUtil#copyPropertiesByMap(Map, Object, String...)}
 * @author honeycomb
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignore {
}
