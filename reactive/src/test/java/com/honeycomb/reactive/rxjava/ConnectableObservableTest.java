package com.honeycomb.reactive.rxjava;

import com.honeycomb.reactive.util.LogUtil;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * <a href="https://www.jianshu.com/p/575ce5b98389?open_source=weibo_search">ConnectableObservable操作详解</a>
 */
public class ConnectableObservableTest {

    /**
     * ConnectableObservable数据并不是当观察者订阅时就发送数据，只
     *    调用了connect()方法，才开始发送数据。
     */
    @Test
    public void infinitePublishTest(){
        ConnectableObservable<Object> observable = Observable.create(emitter -> {
            BigInteger i = BigInteger.ZERO;
            while(true){
                emitter.onNext(i);
                i = i.add(BigInteger.ONE);
            }
        }).publish();
        observable.subscribe(LogUtil::log);
        observable.connect();
    }

    /**
     *
     * @throws InterruptedException
     */
    @Test
    public void testReconnect() throws InterruptedException {
        ConnectableObservable<Long> publish = Observable.interval(200, TimeUnit.MILLISECONDS).publish();
        Disposable connect = publish.connect();
        publish.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("close connect");
        connect.dispose();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("Reconnect");
        //重联之后需要有新的订阅者，会重新调用发布方法
        publish.connect();
        publish.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(1);
    }

    /**
     * ConnectableObservable:每个消费者只会接收到，订阅后发布的数据(热发布)，
     * 且发布只有每一个消费者接收到数据后才会发布第二数据。
     * 备注：冷发布的数据，每个订阅者都会收到完成的发布数据。热发布的数据，订阅者只会收到订阅后发布的数据。(replay缓存操作除外)
     * 注意: 1. 在Observable转ConnectableObservable之前设置的subscribeOn/observeOn有效。
     *      2. 在ConnectableObservable调用connect之前设置的subscribeOn/observeOn无效。
     *      3. 在ConnectableObservable调用connect之后设置的subscribeOn/observeOn有效。
     */
    @Test
    public void hotPublishObservable() throws InterruptedException {
        ConnectableObservable<Object> observable = Observable.create(emitter -> {
            System.out.println("Establishing connection");
            emitter.onNext("处理的数字是：" + Math.random() * 100);
            TimeUnit.SECONDS.sleep(3);
            emitter.onNext("处理的数字是：" + Math.random() * 100);
            emitter.onComplete();
        })
//                在此处设置的subscribeOn有效
//                .subscribeOn(Schedulers.computation())
                .publish();

        //在此处设置的subscribeOn无效
        observable.subscribeOn(Schedulers.computation()).subscribe(x -> LogUtil.log("一郎神 ：" + x));
        observable.connect();
        TimeUnit.MILLISECONDS.sleep(1000);

        //在此处设置的subscribeOn有效
        //此处消费者不会接收到任何发布数据，因为在订阅之前发布的数据已经发布完了，除非发布者改成异步，即在publish()方法前调用.subscribeOn(Schedulers.computation())
        observable.subscribeOn(Schedulers.computation()).subscribe(x -> LogUtil.log("二郎神 ：" + x));

        TimeUnit.SECONDS.sleep(4);
    }

    /**
     * replay()操作符生成的ConnectableObservable，无论观察者什么时候订阅，都能收到Observable发送的所有数据
     * replay()会缓存所有发布的数据。
     * @throws InterruptedException
     */
    @Test
    public void testReplayObservable() throws InterruptedException {
        ConnectableObservable<Object> observable = Observable.create(emitter -> {
            System.out.println("Establishing connection");
            emitter.onNext("处理的数字是：" + Math.random() * 100);
            emitter.onNext("处理的数字是：" + Math.random() * 100);
            emitter.onComplete();
        }).replay();

        observable.subscribe(x -> LogUtil.log("一郎神 ：" + x));
        observable.connect();
        TimeUnit.MILLISECONDS.sleep(1000);

        observable.subscribe(x -> LogUtil.log("二郎神 ：" + x));
    }

    /**
     * 1. replay(buffersize):
     * buffersize：ConnectableObservable缓存数据源的个数，凡是在connect()之后订阅的观察者，只能收到缓存的数据，connect()之前订阅的可以收到所有数据。说白了，replay()相当于缓存了所有数据源，而replay(int buffersize)缓存指定个数的数据源。
     * 2. replay(long time, TimeUnit unit):
     * time：ConnectableObservable换存数据源的时间，凡是在connect之后time时间内订阅的，可接收到所有数据，time时间之后订阅的，收不到任何数据。connect()之前订阅的，可收到所有数据。
     * 3. replay(Scheduler scheduler):
     * 指定订阅和观察所在的工作线程。
     * 4. replay(int buffersize, long time, TimenUnit unit):
     * 这个方法没什么可说的，结合2、3和容易理解。
     * 5. replay(int buffersize, Scheduler scheduler):
     * 指定工作线程，并指定ConnectableObservable缓存数据源的个数。
     * 6. replay(long time, Timeunit unit, Scheduler scheduler):
     * 指定工作线程，并指定数据源缓存时间。
     * 7. replay(int buffersize, long time, TimeUnit unit, Scheduler scheduler):
     * 这个replay涵盖了以上所有的情况，我们在此用完整代码写一遍，并验证ConnectableObservable线程切换的问题。
     *
     * TODO:replay与cache的区别？
     */
    @Test
    public void testReplayObservable1() throws InterruptedException {
        ConnectableObservable<Object> observable = Observable.create(emitter -> {
            System.out.println("Establishing connection");
            emitter.onNext("处理的数字是：" + Math.random() * 100);
            emitter.onNext("处理的数字是：" + Math.random() * 100);
            emitter.onComplete();
        }).replay(1);
        observable.connect();
        observable.subscribeOn(Schedulers.computation()).subscribe(x -> LogUtil.log("一郎神 ：" + x));

        TimeUnit.MILLISECONDS.sleep(1000);

        observable.subscribeOn(Schedulers.computation()).subscribe(x -> LogUtil.log("二郎神 ：" + x));
    }
}
