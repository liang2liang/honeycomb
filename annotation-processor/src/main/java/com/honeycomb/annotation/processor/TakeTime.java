package com.honeycomb.annotation.processor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface TakeTime {

    /**
     * 标记前缀,无实质作用,只是为了方便查找
     * @return
     */
    String tag() default "";
}