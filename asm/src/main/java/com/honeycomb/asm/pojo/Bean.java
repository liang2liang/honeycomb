package com.honeycomb.asm.pojo;

/**
 * @author maoliang
 */
public class Bean {

    private int f;

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void checkAndSetF(int f) {
//        final long start = System.currentTimeMillis();
        if (f >= 21) {
            this.f = f;
        } else {
            throw new IllegalArgumentException();
        }
//        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " 消耗时间为：" + (start - System.currentTimeMillis()));
    }

    public static void main(String[] args) {
        new Bean().checkAndSetF(1);
    }
}
