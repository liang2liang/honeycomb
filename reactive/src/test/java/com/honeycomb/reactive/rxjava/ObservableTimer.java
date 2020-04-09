package com.honeycomb.reactive.rxjava;

import com.honeycomb.reactive.util.LogUtil;
import io.reactivex.Observable;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ObservableTimer {

    /**
     * timer是一次性的
     */
    @Test
    public void test1() throws InterruptedException {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(LogUtil::log);
        Thread.sleep(10000);
    }

    /**
     * 间隔性的Observable
     */
    @Test
    public void test2() throws InterruptedException {
        Observable.interval(2, TimeUnit.SECONDS).subscribe(LogUtil::log);
        Thread.sleep(10000);
    }
}
