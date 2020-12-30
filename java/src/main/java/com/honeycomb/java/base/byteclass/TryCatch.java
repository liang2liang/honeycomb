package com.honeycomb.java.base.byteclass;

/**
 * @author maoliang
 */
public class TryCatch {

    private int block;

    private int catchBlock;

    private int finallyBlock;

    private int methodBlock;

    // 编译后字节码文件，try和catch都会在结束后跳转到final语句上。
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
