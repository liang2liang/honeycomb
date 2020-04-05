package com.honeycomb.java.base.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author maoliang
 * @className ReentrantReadWriteLockTest
 * @date 2019-03-05 16:19
 */
public class ReentrantReadWriteLockTest {

    static class Producer implements Runnable {

        private ReadWriteLock readWriteLock;

        private List<Integer> list;

        public Producer(ReadWriteLock readWriteLock, List<Integer> list) {
            this.readWriteLock = readWriteLock;
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                readWriteLock.writeLock().lock();
                try {
                    for (int i = 0; i < 3; i++) {
                        System.out.println("生产了wow：" + i);
                        list.add(i);
                    }
                } finally {
                    readWriteLock.writeLock().unlock();
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

        private ReadWriteLock readWriteLock;

        public Consumer(ReadWriteLock readWriteLock) {
            this.readWriteLock = readWriteLock;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true) {
                readWriteLock.readLock().lock();
                System.out.println(Thread.currentThread().getId() + "我在读");
                readWriteLock.readLock().unlock();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        List<Integer> list = new ArrayList<>(3);
        new Thread(new Producer(readWriteLock, list)).start();
        new Thread(new Consumer(readWriteLock)).start();
        new Thread(new Consumer(readWriteLock)).start();
        System.out.println("game over");
    }
}
