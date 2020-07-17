package com.honeycomb.io.aio;

import com.honeycomb.io.aio.server.AioServer;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author maoliang
 * @version 1.0.0
 */
public abstract class ChannelInitializer implements CompletionHandler<AsynchronousSocketChannel, AioServer> {

    @Override
    public void completed(AsynchronousSocketChannel channel, AioServer attachment) {
        initChannel(channel);
        attachment.getSocketChannel().accept(attachment, this);
    }

    @Override
    public void failed(Throwable exc, AioServer attachment) {
        exc.printStackTrace();
    }

    protected abstract void initChannel(AsynchronousSocketChannel channel);
}
