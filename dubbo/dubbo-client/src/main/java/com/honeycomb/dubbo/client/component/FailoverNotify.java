package com.honeycomb.dubbo.client.component;

/**
 * 注意：
 *    oninvoke方法：
 *      必须具有与真实的被调用方法sayHello相同的入参列表：例如，oninvoke(String name)
 *    onreturn方法：
 *      至少要有一个入参且第一个入参必须与sayHello的返回类型相同，接收返回结果：例如，onreturnWithoutParam(String result)
 *      可以有多个参数，多个参数的情况下，第一个后边的所有参数都是用来接收sayHello入参的：例如， onreturn(String result,String name)
 *    onthrow方法：
 *      至少要有一个入参且第一个入参类型为Throwable或其子类，接收返回结果；例如，onthrow(Throwable ex)
 *      可以有多个参数，多个参数的情况下，第一个后边的所有参数都是用来接收sayHello入参的：例如，onthrow(Throwable ex,String name)
 *    如果是consumer在调用provider的过程中，出现异常时不会走onthrow方法的，onthrow方法只会在provider返回的RpcResult中含有Exception对象时，才会执行。（dubbo中下层服务的Exception会被放在响应RpcResult的exception对象中传递给上层服务）
 */

import org.springframework.stereotype.Component;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Component
public class FailoverNotify {
    /**
     * 调用之前
     */
    public void oninvoke() {
        System.out.println("failover call");
    }

    /**
     * 调用之后
     *
     * @param result
     */
    public void onreturnWithoutParam(String result) {
        System.out.println("result is [" + result + "]");
    }

    /**
     * 出现异常
     *
     * @param ex
     * @param name
     */
    public void onthrow(Throwable ex, String name) {
        ex.printStackTrace();
    }
}
