package com.honeycomb.reactive.java9;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Slf4j
public class Demo {

    public static void demoSubscribe(DockerXDemoPublisher<Integer> publisher, String subscriberName){
        DockerXDemoSubscriber<Integer> subscriber = new DockerXDemoSubscriber<>( subscriberName, 4L);
        publisher.subscribe(subscriber);
    }

    public static void main(String[] args) {
        ExecutorService executorService = ForkJoinPool.commonPool();
        try(DockerXDemoPublisher<Integer> dockerXDemoPublisher = new DockerXDemoPublisher<>(executorService)){
            demoSubscribe(dockerXDemoPublisher, "one");
            demoSubscribe(dockerXDemoPublisher, "two");
            demoSubscribe(dockerXDemoPublisher, "three");
            IntStream.range(1, 5).forEach(dockerXDemoPublisher::submit);
        }finally {
            try{
                executorService.shutdown();
                int shutdownDelaySec = 1;
                log.info(".......等待 [{}] 秒后结束服务......", shutdownDelaySec);
                executorService.awaitTermination(shutdownDelaySec, TimeUnit.SECONDS);
            }catch (Exception ex){
                log.info("捕获到 executorService.awaitTermination() 方法的异常 ：[{}]", ex.getClass().getName());
            }finally {
                log.info("调用 executorService.shutdownNow() 结束服务...");
                List<Runnable> runnables = executorService.shutdownNow();
                log.info("还剩[{}]个任务等待执行，服务已关闭。", runnables.size());
            }
        }
    }
}
