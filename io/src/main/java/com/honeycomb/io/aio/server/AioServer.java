package com.honeycomb.io.aio.server;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class AioServer extends Thread {

    private AsynchronousServerSocketChannel socketChannel;

    @SneakyThrows
    @Override
    public void run() {
        socketChannel = AsynchronousServerSocketChannel.open(AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), 10));
        socketChannel.bind(new InetSocketAddress(7397));
        System.out.println("AIO server start done!");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        socketChannel.accept(this, new AioServerChannelInitializer());
        countDownLatch.await();
    }

    public AsynchronousServerSocketChannel getSocketChannel() {
        return socketChannel;
    }

    public static void main(String[] args) {
        new AioServer().start();
    }
}
