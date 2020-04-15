package com.honeycomb.reactive.rxjava;

import com.honeycomb.reactive.util.LogUtil;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ThreadOperationTest {

    @Test
    public void testBlocking(){
        Observable.intervalRange(1,2, 1, 1, TimeUnit.SECONDS)
                .map(i -> {
                    LogUtil.log(i);
                    return i;
                })
                .blockingSubscribe(System.out::println);

        Observable.intervalRange(1,2, 1, 1, TimeUnit.SECONDS)
                .map(i -> {
                    LogUtil.log(i);
                    return i;
                })
                .blockingSubscribe(LogUtil::log);
    }

    /**
     * 指定Observable自身在哪个调度器上执行,如果只使用了subscribeOn而未使用observeOn，观察者也会在发布者线程上执行
     * @throws InterruptedException
     */
    @Test
    public void testSubscribeOn() throws InterruptedException {
        Disposable subscribe = Observable.range(1, 10)
                .subscribeOn(Schedulers.computation())
                .map(i -> {
                    LogUtil.log(i);
                    return i * 10;
                })
//                .observeOn(Schedulers.newThread())
                .doOnDispose(() -> System.out.println(Thread.currentThread().getName()))
                .subscribe(LogUtil::log);

        subscribe.dispose();

        TimeUnit.SECONDS.sleep(3);
    }

    /**
     * 指定一个观察者在哪个调度器上观察这个Observable
     * @throws InterruptedException
     */
    @Test
    public void testObserveOn() throws InterruptedException {
        Disposable subscribe = Observable.range(1, 10)
                .observeOn(Schedulers.computation())
                .map(i -> {
                    LogUtil.log(i);
                    return i * 10;
                })
                .doOnDispose(() -> System.out.println(Thread.currentThread().getName()))
                .subscribe(LogUtil::log);

        TimeUnit.SECONDS.sleep(3);
    }
}
