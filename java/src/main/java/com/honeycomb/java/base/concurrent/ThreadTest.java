package com.honeycomb.java.base.concurrent;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ThreadTest {

    public static void main1(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int num = 0;
            while (true){

                if(Thread.currentThread().isInterrupted()){
                    System.out.println("当前线程 isInterrupted");
                    break;
                }

                num++;

                //sleep一下
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    //抛出异常后会重置终端状态
                    System.out.println(Thread.currentThread().isInterrupted());
                    e.printStackTrace();
                }


                if(num % 100 == 0){
                    System.out.println("num : " + num);
                }
            }
        });

        thread.start();

//        Thread.sleep(3000);
        //会进入catch中
        thread.interrupt();
        thread.join();
    }

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {throw  new RuntimeException("a");}, "线程1");
        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.printf("线程名:[%s], 异常内容是:[%s]", t.getName(), e.getMessage());
        });
        thread.start();
    }
}
