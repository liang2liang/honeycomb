package com.honeycomb.agent;

import com.honeycomb.agent.transformaer.TimingInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.concurrent.Callable;

/**
 * @author maoliang
 */
public class AgentMain {

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        //  asm
        //        inst.addTransformer(new CostStatisticTransformer());

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
//                System.out.println("onDiscovery " + s);
            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
//                System.out.println("onTransformation ");
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {
//                System.out.println("onIgnored ");
            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {
                System.out.println("onError " + throwable.getMessage());
            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
//                System.out.println("onComplete " + s);
            }
        };

        // byte-buddy
        new AgentBuilder.Default()
                // 匹配被拦截方法
                .type(ElementMatchers.nameEndsWith("Bean"))
                .transform(
                        (builder, type, classLoader, module) ->
                                builder.method(ElementMatchers.any()) // 拦截任意方法
                                        .intercept(MethodDelegation.to(TimingInterceptor.class))
                )
                .with(listener)
                .installOn(inst);
    }

    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
    }

    public static void agentmain(String agentArgs, Instrumentation instrumentation) throws ClassNotFoundException, UnmodifiableClassException {
        instrumentation.addTransformer(new DefineTransformer(), true);
        //指定需要转化的类
        instrumentation.retransformClasses(Class.forName("com.honeycomb.asm.pojo.Bean"));
    }

    static class DefineTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            System.out.println("premain load Class:" + className);
            return classfileBuffer;
        }
    }

    public static void agentmain(String agentArgs) {
        System.out.println("agentmain");
    }
}

// 必须单独拉文件？
class TimingInterceptor1 {
    @RuntimeType
    public static Object intercept(@net.bytebuddy.implementation.bind.annotation.Origin Method method,
                                   // 调用该注解后的Runnable/Callable，会导致调用被代理的非抽象父方法
                                   @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            // 原有函数执行
            return callable.call();
        } finally {
            System.out.println(method + ": took " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
