package com.honeycomb.java.base.concurrent;

/**
 * @author maoliang
 * @className WaitNotify
 * @date 2019-03-01 18:34
 */
public class WaitNotify {

    static class Wait implements Runnable{

        private Object lock;

        private int i;

        public Wait(Object lock, int i) {
            this.lock = lock;
            this.i = i;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    System.out.println("等待中" + i);
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ok, " + i);
            }
        }
    }

    static class Notify implements Runnable{

        private Object lock;

        public Notify(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("我要开始啦");
            synchronized (lock) {
                lock.notify();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();
        obj.wait();
        new Thread(new Wait(obj,1)).start();
        new Thread(new Wait(obj, 2)).start();
        new Thread(new Wait(obj,3 )).start();
        new Thread(new Notify(obj)).start();
        new Thread(new Notify(obj)).start();
        System.out.println("-------------");
    }
}
