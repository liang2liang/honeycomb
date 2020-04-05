package com.honeycomb.java.base.concurrent;

import java.util.concurrent.Semaphore;

/**
 * @author maoliang
 * @className SemaphoreTest
 * @date 2019-03-07 13:14
 */
public class SemaphoreTest {

    static class Park implements Runnable{

        Semaphore semaphore;

        public Park(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {

            try {
                semaphore.acquire();
                System.out.println("车辆进入。。。。。" + Thread.currentThread().getName());

                Thread.sleep(3333);

                System.out.println("车辆驶出。。。。。" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(3, false);

        for (int i = 0; i < 10; i++) {
            new Thread(new Park(semaphore)).start();
        }
    }
}
