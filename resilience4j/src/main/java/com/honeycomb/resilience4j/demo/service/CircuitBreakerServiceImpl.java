package com.honeycomb.resilience4j.demo.service;

import com.honeycomb.resilience4j.demo.util.CircuitBreakerUtil;
import com.honeycomb.resilience4j.demo.util.RetryUtil;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

@Slf4j
@Service
public class CircuitBreakerServiceImpl {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    private final HelloWorldService helloWorldService;

    private final TimeLimiter timeLimiter;

    private final RetryRegistry retryRegistry;

    public CircuitBreakerServiceImpl(CircuitBreakerRegistry circuitBreakerRegistry, HelloWorldService helloWorldService, TimeLimiter timeLimiter, RetryRegistry retryRegistry) {
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.helloWorldService = helloWorldService;
        this.timeLimiter = timeLimiter;
        this.retryRegistry = retryRegistry;
    }

    public String circuitBreakerNotAOP() throws Throwable {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("backendA");
        CircuitBreakerUtil.getCircuitBreakerStatus("执行之前：", circuitBreaker);
        String result = circuitBreaker.executeCheckedSupplier(helloWorldService::sayName);
        CircuitBreakerUtil.getCircuitBreakerStatus("执行之后：", circuitBreaker);
        return result;
    }

    public String circuitBreakerNotAOP1() {
        // 通过注册器获取熔断器的实例
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("backendA");
        CircuitBreakerUtil.getCircuitBreakerStatus("执行开始前：", circuitBreaker);
        // 使用熔断器包装连接器的方法
        CheckedFunction0<String> checkedSupplier = CircuitBreaker.
                decorateCheckedSupplier(circuitBreaker, helloWorldService::exception);
        // 使用Try.of().recover()调用并进行降级处理
        Try<String> result = Try.of(checkedSupplier).
                recover(CallNotPermittedException.class, throwable -> {
                    log.info("熔断器已经打开，拒绝访问被保护方法~");
                    CircuitBreakerUtil
                            .getCircuitBreakerStatus("熔断器打开中:", circuitBreaker);
                    return "failed";
                })
                .recover(throwable -> {
                    log.info(throwable.getLocalizedMessage() + ",方法被降级了~~");
                    CircuitBreakerUtil
                            .getCircuitBreakerStatus("降级方法中:", circuitBreaker);
                    return "failed1";
                });
        CircuitBreakerUtil.getCircuitBreakerStatus("执行结束后：", circuitBreaker);
        return result.get();
    }

    public String circuitBreakerTimeLimiter() {
        // 通过注册器获取熔断器的实例
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("backendA");
        CircuitBreakerUtil.getCircuitBreakerStatus("执行开始前：", circuitBreaker);
        // 创建单线程的线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();
        //将被保护方法包装为能够返回Future的supplier函数
        Supplier<Future<String>> futureSupplier = () -> pool.submit(helloWorldService::timelimiter);
        // 先用限时器包装，再用熔断器包装
        Callable<String> restrictedCall = TimeLimiter.decorateFutureSupplier(timeLimiter, futureSupplier);
        Callable<String> chainedCallable = CircuitBreaker.decorateCallable(circuitBreaker, restrictedCall);
        // 使用Try.of().recover()调用并进行降级处理
        Try<String> result = Try.of(chainedCallable::call)
                .recover(CallNotPermittedException.class, throwable -> {
                    log.info("熔断器已经打开，拒绝访问被保护方法~");
                    CircuitBreakerUtil.getCircuitBreakerStatus("熔断器打开中", circuitBreaker);
                    return "熔断打开";
                })
                .recover(throwable -> {
                    log.info(throwable.getLocalizedMessage() + ",方法被降级了~~");
                    CircuitBreakerUtil.getCircuitBreakerStatus("降级方法中:", circuitBreaker);
                    return "降级";
                });
        CircuitBreakerUtil.getCircuitBreakerStatus("执行结束后：", circuitBreaker);
        return result.get();
    }

    @PostConstruct
    public void init() {
        Retry retry = retryRegistry.retry("backendA");
        //增加监听器
        RetryUtil.addRetryListener(retry);
    }

    public String circuitBreakerRetryNotAOP() {
        // 通过注册器获取熔断器的实例
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("backendA");
        // 通过注册器获取重试组件实例
        Retry retry = retryRegistry.retry("backendA");
        CircuitBreakerUtil.getCircuitBreakerStatus("执行开始前：", circuitBreaker);
        // 先用重试组件包装，再用熔断器包装
        CheckedFunction0<String> checkedSupplier = Retry.decorateCheckedSupplier(retry, helloWorldService::retry);
        CheckedFunction0<String> chainedSupplier = CircuitBreaker.decorateCheckedSupplier(circuitBreaker, checkedSupplier);
        // 使用Try.of().recover()调用并进行降级处理
        Try<String> result = Try.of(chainedSupplier).
                recover(CallNotPermittedException.class, throwable -> {
                    log.info("已经被熔断，停止重试");
                    return "熔断";
                })
                .recover(throwable -> {
                    log.info("重试失败: " + throwable.getLocalizedMessage());
                    return "失败";
                });
        RetryUtil.getRetryStatus("执行结束: ", retry);
        CircuitBreakerUtil.getCircuitBreakerStatus("执行结束：", circuitBreaker);
        return result.get();
    }
}