package com.honeycomb.reactive.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class FlowableTest {

    @Test
    public void testFlowable() throws InterruptedException {
        Flowable.range(1, 999)
                .map(Customer::new)
                .observeOn(Schedulers.io())
                .subscribe(customer -> {
                    TimeUnit.MILLISECONDS.sleep(20);
                    System.out.println(Thread.currentThread().getName() + "----所获取的Customer的ID为：" + customer.getId());
                });

        TimeUnit.MILLISECONDS.sleep(1000000);
    }

    /**
     * BackpressureStrategy.BUFFER:会按缓存大小，分成多份数据存入容器，
     * 而消费者会从装满数据的容器中获取发布的数据。一旦发布的数据过多，消费者消费速度过慢会产生OOM。
     * BackpressureStrategy.LATEST:在消费者处于繁忙阶段上游只保存最近一个元素。
     * BackpressureStrategy.DROP:在消费者处于繁忙阶段，直接抛弃发布的元素。
     * @throws InterruptedException
     */
    @Test
    public void testFlowableCreate() throws InterruptedException {
        Flowable<Integer> objectFlowable = Flowable.create(emitter -> {
            for (int i = 0; i < 10; i++) {
                if (emitter.isCancelled()) {
                    return;
                }
                emitter.onNext(i);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);

        objectFlowable.observeOn(Schedulers.newThread())
                .subscribe(i -> {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + " ：" + i);
                }, Throwable::printStackTrace, () -> System.out.println("complete"));

        objectFlowable.observeOn(Schedulers.newThread())
                .subscribe(i -> {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + " ：" + i);
                }, Throwable::printStackTrace, () -> System.out.println("complete"));

        TimeUnit.SECONDS.sleep(40);
    }

    @Test
    public void testObservableToFlowable() throws InterruptedException {
        Observable.range(1, 100)
                .toFlowable(BackpressureStrategy.BUFFER)
                .observeOn(Schedulers.computation())
                .subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void testFlowableToObservable() throws InterruptedException {
        Flowable.range(1, 10)
                .subscribeOn(Schedulers.computation())
                .toObservable()
                .subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(3);
    }

    static class Customer{

        private int id;

        public Customer(int id) {
            System.out.println(Thread.currentThread().getName() + "----正在构造Customer: ID[" + id + "]");
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
