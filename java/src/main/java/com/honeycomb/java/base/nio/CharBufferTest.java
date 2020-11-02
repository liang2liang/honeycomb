package com.honeycomb.java.base.nio;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

//非直接缓冲区：每次向硬盘存取数据时需要将数据暂存在JVM的中间缓冲区，
//如果有频繁操作数据的情况发生，则在每次操作时都会将数据暂时存在JVM的中间缓冲区，
//再交给ByteBuffer处理，这样做就大大降低软件对数据的吞吐量，提高内存占有率，造成软件运行效率降低。
//直接缓冲区：每次向硬盘存取数据时直接与内存打交道。但是创建慢。


/**
 * @author maoliang
 * @version 1.0.0
 */
public class CharBufferTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        Constructor<CharBufferTest> constructor = CharBufferTest.class.getConstructor();
        CharBufferTest charBufferTest = constructor.newInstance();



        var charArray = new char[]{'a', 'b', 'c', 'd', 'e'};
        wrap(charArray);
//        allocate(10);
//        allocateDirect(10);
    }

    //wrap:offset只是设置缓冲区的position值，length确定limit（offset + length）值。
    public static void wrap(char[] charArray){
        CharBuffer charBuffer = CharBuffer.wrap(charArray);
        CharBuffer charBuffer1 = CharBuffer.wrap(charArray, 2, 2);

        System.out.println("A capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position() + " isDirect() = " + charBuffer.isDirect());

        System.out.println("B capacity() = " + charBuffer1.capacity() + " limit() = " + charBuffer1.limit()
                + " position() = " + charBuffer1.position() + " isDirect() = " + charBuffer1.isDirect());

        charBuffer1.rewind();

        System.out.println("C capacity() = " + charBuffer1.capacity() + " limit() = " + charBuffer1.limit()
                + " position() = " + charBuffer1.position() + " isDirect() = " + charBuffer1.isDirect());
    }

    //rewind:重绕缓冲区，将位置设置为0并丢弃标记。
    public static void rewind(char[] charArray){

        CharBuffer charBuffer = CharBuffer.wrap(charArray);

        charBuffer.position(3);
        charBuffer.mark();

        System.out.println("A capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position() + " isDirect() = " + charBuffer.isDirect());

        charBuffer.rewind();

        System.out.println("B capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position() + " isDirect() = " + charBuffer.isDirect());

        charBuffer.reset();
    }

    //flip:反转此缓冲区（写-->读|读-->写）。首先将限制设置为当前位置，然后将位置设置为0。如果已定义了标记，则丢弃该标记。
    public static void flip(char[] charArray){
        CharBuffer charBuffer = CharBuffer.wrap(charArray);
        charBuffer.position(2);
        charBuffer.mark();

        System.out.println("A capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position() + " isDirect() = " + charBuffer.isDirect());

        charBuffer.flip();

        System.out.println("A capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position() + " isDirect() = " + charBuffer.isDirect());

        charBuffer.reset();
    }

    //clear:还原缓冲区到初始的状态。（将位置设置为0，将限制设置为容量，并丢弃标记。）
    //注：clear并不会删除原来数组中的数据，只是修改标记位置。通过新增数据来覆盖原来数据的方式达到删除原来数据的目的。
    public static void clear(char[] charArray){
        CharBuffer charBuffer = CharBuffer.wrap(charArray);

        charBuffer.limit(4);
        charBuffer.position(2);
        charBuffer.mark();
        System.out.println("A capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position() + " isDirect() = " + charBuffer.isDirect());

        charBuffer.clear();

        System.out.println("B capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position() + " isDirect() = " + charBuffer.isDirect());
    }

    //创建直接缓冲区，并指定其大小。
    public static void allocateDirect(int size){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(size);
        System.out.println("A capacity() = " + byteBuffer.capacity() + " limit() = " + byteBuffer.limit()
                + " position() = " + byteBuffer.position() + " isDirect() = " + byteBuffer.isDirect());
    }

    //allocate:开辟指定空间大小的缓存区（非直接缓冲区）
    public static void allocate(int size){
        CharBuffer charBuffer = CharBuffer.allocate(size);
        System.out.println("A capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position() + " isDirect() = " + charBuffer.isDirect());
    }

    //在此缓存区的位置设置标记
    public static void mark(char[] charArray){
        CharBuffer charBuffer = CharBuffer.wrap(charArray);
        System.out.println("A capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position() + " isDirect() = " + charBuffer.isDirect());

        charBuffer.position(2);
        charBuffer.mark();

        System.out.println("A capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position());

        charBuffer.position(4);
        System.out.println("A capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position());

        charBuffer.reset();

        System.out.println("A capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position());
    }


    public static void remaining(char[] charArray){
        CharBuffer charBuffer = CharBuffer.wrap(charArray);
        System.out.println("A capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position());

        charBuffer.position(2);

        System.out.println("B capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position());

        //remaining = limit - position
        System.out.println("C remaining() = " + charBuffer.remaining());
    }

    //position:下一个读取或写入操作的index
    public static void position(char[] charArray) {
        CharBuffer charBuffer = CharBuffer.wrap(charArray);
        System.out.println("A capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position());

        charBuffer.position(2);

        System.out.println("B capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit()
                + " position() = " + charBuffer.position());

        charBuffer.put('z');

        for (var c : charArray) {
            System.out.println(c);
        }
    }

    //0 <= makr <= position <= limit <= capacity
    //limit:索引从0开始。limit表示第一个不应该读取或写入的元素的index
    public static void limit(char[] charArray) {
        CharBuffer charBuffer = CharBuffer.wrap(charArray);
        System.out.println("A capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit());
        charBuffer.limit(3);
        System.out.println();
        System.out.println("B capacity() = " + charBuffer.capacity() + " limit() = " + charBuffer.limit());
        charBuffer.put(0, 'o');
        charBuffer.put(1, 'p');
        charBuffer.put(2, 'q');

        charBuffer.put(3, 'r');
        charBuffer.put(4, 's');
        charBuffer.put(5, 't');
    }
}
