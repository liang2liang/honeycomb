package com.honeycomb.reactive.rxjava;

import com.honeycomb.reactive.util.LogUtil;
import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import org.junit.Test;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class ConnectableObservableTest {

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

    @Test
    public void hotPublishObservable() throws InterruptedException {
        ConnectableObservable<Object> observable = Observable.create(emitter -> {
            System.out.println("Establishing connection");
            emitter.onNext("处理的数字是：" + Math.random() * 100);
            emitter.onNext("处理的数字是：" + Math.random() * 100);
            emitter.onComplete();
        }).publish();

        observable.subscribe(x -> LogUtil.log("一郎神 ：" + x));

        TimeUnit.MILLISECONDS.sleep(5000);

        observable.subscribe(x -> LogUtil.log("二郎神 ：" + x));
        observable.connect();
    }
}
