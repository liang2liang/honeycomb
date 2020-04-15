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

    /**
     * Work = ExecutorService + runnable
     * IOScheduler: 创建的work在工作结束后会保留起来，等待下次任务。如果没有下次任务，会在指定时间内关系所有work
     * IOScheduler：适合IO操作，每次来任务都会创建新的Work，让其执行，因为都是IO操作，所以可以让其每次都执行，不会产生等待cpu。
     * @throws InterruptedException
     */
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

    /**
     * ComputationScheduler在启动是会创建cpu核数的work，每次来work时都会在这个几个work上工作。
     * ComputationScheduler：适合CPU密集型操作，当超过cpu核数的任务过来时，必须等待，因为没有多余的work去执行工作。
     * @throws InterruptedException
     */
    @Test
    public void testComputationScheduler() throws InterruptedException {
//        System.setProperty("rx2.io-keep-alive-time", "2");
        System.out.println(System.getProperty("rx2.io-keep-alive-time"));
        Scheduler.Worker worker = Schedulers.computation().createWorker();
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
