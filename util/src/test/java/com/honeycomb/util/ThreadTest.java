package com.honeycomb.util;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ThreadTest {

    public static void main(String[] args) throws InterruptedException {
        Aobling aobling = new Aobling();
        new Thread(aobling).start();
        for (; ; ) {
//            TimeUnit.SECONDS.sleep(1);
            if (aobling.isFlag()) {
                System.out.println("有点东西");
            }
        }
    }

    static class Aobling implements Runnable {

        private boolean flag = false;

        public boolean isFlag() {
            return flag;
        }

        @SneakyThrows
        @Override
        public void run() {
            TimeUnit.SECONDS.sleep(1);
            flag = true;
            System.out.println("flag = " + flag);
        }
    }
}
