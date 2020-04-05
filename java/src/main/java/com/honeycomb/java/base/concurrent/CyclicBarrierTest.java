package com.honeycomb.java.base.concurrent;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author maoliang
 * @className CyclicBarrierTest
 * @date 2019-03-05 18:13
 */
public class CyclicBarrierTest {

    static class Operator implements Runnable {

        private CyclicBarrier cyclicBarrier;

        public Operator(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("骚年，我操作完了。" + Thread.currentThread().getId());

//            try {
//                cyclicBarrier.await();
//            } catch (InterruptedException | BrokenBarrierException e) {
//                e.printStackTrace();
//            }

            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            System.out.println("骚年，我又开始了。" + Thread.currentThread().getId());
        }
    }

    public static void main(String[] args) {
        /**
         * 参数说明：
         * 1. 信号量数量
         * 2. 当信号量为0时执行的动作
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> System.out.println("good job" + Thread.currentThread().getId()));
        for (int i = 0; i < 2; i++) {
            new Thread(new Operator(cyclicBarrier)).start();
        }
    }
}
