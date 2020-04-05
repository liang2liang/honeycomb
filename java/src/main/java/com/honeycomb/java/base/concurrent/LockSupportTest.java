package com.honeycomb.java.base.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * @author maoliang
 * @className LockSupportTest
 * @date 2019-03-06 12:03
 */
public class LockSupportTest {

    static class Operator implements Runnable {

        private Thread thread;

        public Operator(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("哈哈， 我要激活你啦");
            LockSupport.unpark(thread);
        }
    }

    public static void main(String[] args) {
        System.out.println("我被冻住啦。。。。");
        new Thread(new Operator(Thread.currentThread())).start();
        LockSupport.park();
        System.out.println();
    }
}
