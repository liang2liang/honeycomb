package com.honeycomb.asm.pojo;

import org.openjdk.jol.info.ClassLayout;

/**
 * java object layout:openjdk提供查看对象占用空间
 * @author maoliang
 */
public class JOL {

    private int i;

    private long j;

    // 会填充4bit空格，补充为8N
    private int a;

    public static void main(String[] args) {
        JOL jol = new JOL();
        System.out.println( ClassLayout.parseInstance(jol).toPrintable());
    }
}
