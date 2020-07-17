package com.honeycomb.io.aio;

import lombok.SneakyThrows;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * @author maoliang
 * @version 1.0.0
 */
public abstract class ChannelAdapter implements CompletionHandler<Integer, Object> {

    private final AsynchronousSocketChannel channel;

    private final Charset charset;

    public ChannelAdapter(AsynchronousSocketChannel channel, Charset charset) {
        this.channel = channel;
        this.charset = charset;
        if (channel.isOpen()) {
            channelActive(new ChannelHandler(channel, charset));
        }
    }

    @Override
    public void completed(Integer result, Object attachment) {
        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        final long timeout = 60 * 60L;
        channel.read(buffer, timeout, TimeUnit.SECONDS, attachment, new CompletionHandler<Integer, Object>() {
            @SneakyThrows
            @Override
            public void completed(Integer result, Object attachment) {
                if (result == -1) {
                    channelInactive(new ChannelHandler(channel, charset));
                    channel.close();
                    return;
                }

                buffer.flip();
                channelRead(new ChannelHandler(channel, charset), charset.decode(buffer));
                buffer.clear();
                channel.read(buffer, timeout, TimeUnit.SECONDS, attachment, this);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        exc.printStackTrace();
    }

    public abstract void channelActive(ChannelHandler ctx);

    public abstract void channelInactive(ChannelHandler ctx);

    public abstract void channelRead(ChannelHandler ctx, Object msg);
}
