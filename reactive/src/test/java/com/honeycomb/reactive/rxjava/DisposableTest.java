package com.honeycomb.reactive.rxjava;

import com.honeycomb.reactive.util.LogUtil;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.junit.Test;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class DisposableTest {

    /**
     * .cache() -> ObservableCache
     * ObservableCache既是发布者也是消费者，在订阅订阅者是会将订阅者存储起来，
     * 而真正接受onNext的是自己，在通过自己保存的订阅者，依次发布接受到的数据。
     * 因此关闭订阅的时候会留下一个自己无法关闭，如果是无线流就会导致无线发布数据，而没有真正的订阅者。
     * 为什么需要用链表保存每次发布的内容？
     * 因为每个订阅者都需要接受到完整发布数据，但是每个订阅者被订阅的时间不同，导致后订阅的订阅就会丢失部分发布的数据，
     * 通过链表保存发布的数据后就可以使每个订阅者接受到完整的发布数据。
     * ps：每个订阅者通过CacheDisposable装饰了下，带有offset属性，可以知道当前需要从哪里开始接受数据。
     * @throws InterruptedException
     */
    @Test
    public void infiniteUnsubscribedCacheThreadTest() throws InterruptedException {
        Observable<Object> observable = Observable.create(emitter -> {
            Runnable runnable = () -> {
                BigInteger i = BigInteger.ZERO;
                while (!emitter.isDisposed()) {
                    emitter.onNext(i);
                    i = i.add(BigInteger.ONE);
                    System.out.println(Thread.currentThread().getName() + " : 下一个消费的数字" + i.toString());
                }
            };
            new Thread(runnable).start();
        }).cache();

        Disposable subscribe = observable.subscribe(x -> LogUtil.log("一郎神 ：" + x));


        TimeUnit.MILLISECONDS.sleep(500);

        Disposable subscribe2 = observable.subscribe(x -> LogUtil.log("二郎神 ：" + x));


        subscribe.dispose();
        subscribe2.dispose();

        System.out.println("我取消订阅郎");

        TimeUnit.MILLISECONDS.sleep(1);
        System.out.println("程序结束");
    }
}
