package com.honeycomb.reactive.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author maoliang
 * @version 1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogUtil {

    public static void log(Object o){
        System.out.println(Thread.currentThread().getName() + " : " + o);
    }
}
