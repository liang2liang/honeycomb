package com.honeycomb.reactive.rxjava;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class SechedulerTest {

    @Test
    public void testNewThreadScheduler(){
        Schedulers.newThread().createWorker().schedule(() -> System.out.println(Thread.currentThread().getName()));
    }

    @Test
    public void testIOScheduler() throws InterruptedException {
//        System.setProperty("rx2.io-keep-alive-time", "2");
        System.out.println(System.getProperty("rx2.io-keep-alive-time"));
        Scheduler.Worker worker = Schedulers.io().createWorker();
        worker.schedule(() -> {
            try {
                System.out.println("开始时间：" + LocalDateTime.now());
                TimeUnit.SECONDS.sleep(6);
                System.out.println("结束时间：" + LocalDateTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        worker.schedule(() -> System.out.println(Thread.currentThread().getName() + " ：" + LocalDateTime.now()));

        Thread.sleep(8000);
    }
}
