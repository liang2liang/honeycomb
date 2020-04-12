package com.honeycomb.reactive.rxjava;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * RxJava的每一步链式增强操作，都是通过装饰者模式进行增强操作的。
 * @author maoliang
 * @version 1.0.0
 */
public class ObservableDemo {

    /**
     * Rxjava 中 Observable -> java 9 中的Publisher
     *           Observer -> java 9 中的Subscriber
     *           Disposable -> java 9 中的Subscription
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("处理的数字是：" + Math.random() * 100);
            emitter.onComplete();
        });

        observable.subscribe(str -> System.out.println("当前线程名是:" + Thread.currentThread().getName() + ", 订阅者1收到的信息是: " + str));
        observable.subscribe(str -> System.out.println("当前线程名是:" + Thread.currentThread().getName() + ", 订阅者2收到的信息是: " + str));

        //带缓存的发布者，会通过链表缓存每次发布的数据，使得每个订阅者收到的数据一直。
        //不可用于无线流，会导致内存异常。
        Observable<String> cache = observable.cache();
        cache.subscribe(str -> System.out.println("当前线程名是:" + Thread.currentThread().getName() + ", 订阅者1收到的信息是: " + str));
        cache.subscribe(str -> System.out.println("当前线程名是:" + Thread.currentThread().getName() + ", 订阅者2收到的信息是: " + str));

        Observable<Integer> observable1 = Observable.just(1, 2, 3, 4);

        observable1.subscribe(i -> System.out.println("当前线程名是:" + Thread.currentThread().getName() + ", 订阅者3收到的信息是: " + i));
        observable1.subscribe(i -> System.out.println("当前线程名是:" + Thread.currentThread().getName() + ", 订阅者4收到的信息是: " + i));

        Observable<Integer> observable2 = Observable.fromArray(1);
        observable2.subscribe(i -> System.out.println("当前线程名是:" + Thread.currentThread().getName() + ", 订阅者3收到的信息是: " + i));
        observable2.subscribe(i -> System.out.println("当前线程名是:" + Thread.currentThread().getName() + ", 订阅者4收到的信息是: " + i));


        //延迟创建ObservableSource对象，只有subscribe时才创建。
        Disposable disposable = Observable.defer(() -> emitter -> {
            emitter.onNext("处理的数字是：" + Math.random() * 100);
            emitter.onComplete();
        }).subscribe(System.out::println);

        System.out.println(disposable);

        //无线流
        Observable.create(emitter -> {
            new Thread(() -> {
                while (true) {
                    emitter.onNext(Math.random());
                }
            }).start();
        }).subscribe(System.out::println);

        Thread.sleep(1000);
    }
}
