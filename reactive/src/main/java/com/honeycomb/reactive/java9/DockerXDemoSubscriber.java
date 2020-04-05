package com.honeycomb.reactive.java9;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Slf4j
public class DockerXDemoSubscriber<T> implements Flow.Subscriber<T> {

    private String name;

    private final long bufferSize;

    private long count;

    private Flow.Subscription subscription;

    public DockerXDemoSubscriber(String name, long bufferSize) {
        this.name = name;
        this.bufferSize = bufferSize;
    }

    @Override
    @SneakyThrows
    public void onSubscribe(Flow.Subscription subscription) {
//        count = this.bufferSize - this.bufferSize / 2;
        this.subscription = subscription;
        this.subscription.request(bufferSize);
        log.info("开始 onSubscribe 订阅");
        Thread.sleep(100);
    }

    @SneakyThrows
    @Override
    public void onNext(T item) {
//        if (--count <= 0) {
//            this.subscription.request(this.bufferSize / 2);
//        }
        log.info(" ##### [{}] name: [{}] item: [{}] #####", Thread.currentThread().getName(), this.name, item);
        log.info("{} received {}", this.name, item);
        Thread.sleep(10);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        log.info("Completed");
    }

    public String getName() {
        return name;
    }

    public Flow.Subscription getSubscription() {
        return subscription;
    }
}
