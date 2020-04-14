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

    public void testSubscribeOn1(){
        Disposable subscribe = Observable.range(1, 10)
                .subscribeOn(Schedulers.computation())
                .map(i -> {
                    LogUtil.log(i);
                    return i * 10;
                })
                .doOnDispose(() -> System.out.println(Thread.currentThread().getName()))
                .subscribe(LogUtil::log);
    }
}
