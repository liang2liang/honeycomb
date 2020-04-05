package com.honeycomb.java.base.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author maoliang
 * @className ReentrantLockTest
 * @date 2019-03-05 13:20
 */
public class ReentrantLockTest {

    static class Producer implements Runnable {

        private ReentrantLock lock;
        private List<String> list;

        public Producer(ReentrantLock lock, List<String> list) {
            this.lock = lock;
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    for (int i = 0; i < 10; i++) {
                        list.add("wow: " + i);
                        System.out.println("wow: " + i + "进入队列");
                    }
                } finally {
                    System.out.println("生产结束");
                    lock.unlock();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Consumer implements Runnable {

        private ReentrantLock lock;
        private List<String> list;

        public Consumer(ReentrantLock lock, List<String> list) {
            this.lock = lock;
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println("进入");
                try {
                    lock.lock();
                    System.out.println("获取锁成功");
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(list.size());
                        list.remove(0);
                        System.out.println("wow: " + i + "出队列");
                    }
                } finally {
                    lock.unlock();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);
        List<String> list = new ArrayList<>(10);
        new Thread(new Producer(lock, list)).start();
        new Thread(new Consumer(lock, list)).start();
        System.out.println("game over");
    }

}
