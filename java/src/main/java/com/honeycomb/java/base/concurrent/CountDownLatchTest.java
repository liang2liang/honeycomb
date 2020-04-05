package com.honeycomb.java.base.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author maoliang
 * @className CountDownLatchTest
 * @date 2019-03-06 13:23
 */
public class CountDownLatchTest {

    static class Operator implements Runnable {

        private CountDownLatch countDownLatch;

        public Operator(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println("我要开始暂停了。。。");
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("暂停结束，开始干活。。。");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        System.out.println("程序开始。。。。。");

        new Thread(new Operator(countDownLatch)).start();
        new Thread(new Operator(countDownLatch)).start();
        new Thread(new Operator(countDownLatch)).start();
        new Thread(new Operator(countDownLatch)).start();

        for (int i = 0; i < 7; i++) {
            Thread.sleep(3000);
            System.out.println(5 - i);
        }

        countDownLatch.countDown();

        System.out.println("game over");
    }
}
