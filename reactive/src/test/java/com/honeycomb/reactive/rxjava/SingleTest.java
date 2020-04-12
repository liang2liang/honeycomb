package com.honeycomb.reactive.rxjava;

import io.reactivex.Single;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Single表示只能发布一次数据的Observable
 * @author maoliang
 * @version 1.0.0
 */
public class SingleTest {

    @Test
    public void testSingle(){
        Single.just(1)
                .subscribe(System.out::println);
    }

    /**
     * concat操作和merge操作相似，但是concat会保证订阅者接受到的数据顺序一定是发布的顺序，而merge没有这个操作.
     */
    @Test
    public void testConcat() throws InterruptedException {
        Single.merge(Single.just(1).delay(1, TimeUnit.SECONDS), Single.just("honeyocmb"))
                .subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(2);
        System.out.println("**********************");

        Single.concat(Single.just(1).delay(1, TimeUnit.SECONDS), Single.just("honeyocmb"))
                .subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(6);
    }
}
