package com.honeycomb.java.base.type;

/**
 * @author maoliang
 */
public class Test {

    private int block;

    private int catchBlock;

    private int finallyBlock;

    private int methodBlock;

    public static void main(String[] args) {

    }

    public void test(){
        try{
            block = 2;
        }catch (Exception e){
            catchBlock = 3;
        }finally {
            finallyBlock = 4;
        }
        methodBlock = 5;
    }
}
