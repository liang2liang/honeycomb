package com.honeycomb.reactive.java9.store;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Slf4j
public class JackSubscriber implements Flow.Subscriber<Book> {

    private Flow.Subscription subscription;

    /**
     * 该方法在发布信息时才正在调用。通过线程任务调用。且该方法和其他Subscriber的方法可能不是一个线程。（其他方法一定是一个线程）
     * 和通道建立关系，需要将subscription保存在本地，后续获取发布元素/取消订阅需要通过subscription
     * @param subscription
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        log.info("init subscription called!");
        this.subscription = subscription;
        //需要请求一次，告诉发布者我准备好了。
        subscription.request(1);
    }

    @SneakyThrows
    @Override
    public void onNext(Book item) {
        log.info("jack is reading book : [{}]", item.getName());
        Thread.sleep(10000);
//        throw new RuntimeException("jack goto restroom");
        subscription.request(1);
//        subscription.cancel();
//        log.info("jack cancel subscribe book");
    }

    /**
     * 发布元素过程中产生异常会调用该方法，包括发布者和订阅抛出的异常
     */
    @Override
    public void onError(Throwable throwable) {
        log.error(throwable.getMessage());
    }

    /**
     * 调用时机：
     * 1. 当发布者发布完所有消息，调用close方法时。如果订阅者request的数量和发布数量一致会调用改方法。否者会有线程任务一直等待订阅者处理完所有元素。
     */
    @Override
    public void onComplete() {
        log.info("订阅数据结束");
    }
}
