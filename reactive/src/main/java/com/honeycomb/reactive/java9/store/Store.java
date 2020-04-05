package com.honeycomb.reactive.java9.store;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Slf4j
public class Store {

    public static void main(String[] args) throws IOException, InterruptedException {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

        /**
         * SubmissionPublisher类说明：
         * SubmissionPublisher在调用subscribe订阅订阅者时，会创建Subscription对象，
         * 且每个Subscription会有下一个订阅者的Subscription引用。
         * Subscription是链表形式存储的。
         * Subscription中的request总数就是订阅者需要元素的总数。但是总数不等于发布总数就出现阻塞任务，等待订阅者去消费剩下的元素。所以订阅者在不需要元素后需要调用Subscription的cancel方法。
         *
         * SubmissionPublisher是建立在push模式上的。
         *
         * 参数说明：
         * 1. executor:线程池 --> 通过线程池发布元素，默认是ForkJoinPool.commonPool(),每个订阅者专属一个线程。
         * 2. 每个订阅者的缓存区大小(和Map一样会扩展至刚好容下指定数量的2的次方数)。 如果超过缓存区大小的数据将会被删除。默认容量256
         * 3. 订阅者的onNext方法抛出异常时的处理器
         */
        SubmissionPublisher<Book> submissionPublisher = new SubmissionPublisher<>(executor, 2, (subscriber, throwable) -> {
            log.info("subscriber has exception, subscribe is : [{}], exception is : [{}].", subscriber, throwable.getMessage());
        });

        log.info("subscriber cache size is : [{}]", submissionPublisher.getMaxBufferCapacity());

        JackSubscriber jackSubscriber = new JackSubscriber();

        //订阅订阅者，并不会直接调用
        submissionPublisher.subscribe(jackSubscriber);

        //简易版订阅者，只关系onNext方法Subscriber.onSubscribe()
//        submissionPublisher.consume(book -> log.info("I got a book, book's name is : [{}]", book.getName()));

        //发布一个元素，如果缓存区数据已满则一直阻塞，最大阻塞时间为Long.MAX_VALUE纳秒。
        submissionPublisher.submit(new Book("人生就像一场戏"));
        submissionPublisher.submit(new Book("人生就像一场戏1"));

        log.info("offer method call");
        /**
         * 非阻塞发布方式
         * 参数说明：
         * 1. 发布的数据。
         * 2. 超时时间。发布元素超时(当缓存区满时)，将调用判定函数(之后调用一次)。
         * 3. 超时时间单位。
         * 4. 判定函数：返回true表示需要重试一次，返回false直接删除该次发布的元素。
         */
        int count = submissionPublisher.offer(new Book("因为有缘才相聚"), 1, TimeUnit.SECONDS, (subscriber, book) -> {
            log.info("Is delete call");
            return true;
        });
        log.info("offer call end, try count is : [{}]", count);

        //调用每一个消息通道，通过其通知发布者发布元素结束。
        //如果有订阅者未取消，且为接受到最后一个发布的元素，则会有线程任务一直等待订阅者处理完最后一个元素。
        submissionPublisher.close();

        Thread.sleep(3000);
        log.info("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                executor.getQueue().size() + "，已执行完的任务数目：" + executor.getCompletedTaskCount());
        executor.shutdown();
    }
}
