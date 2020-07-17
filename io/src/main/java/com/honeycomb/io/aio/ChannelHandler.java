package com.honeycomb.io.aio;

import lombok.Data;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Data
public class ChannelHandler {

    private final AsynchronousSocketChannel channel;

    private final Charset charset;

    public ChannelHandler(AsynchronousSocketChannel channel, Charset charset) {
        this.channel = channel;
        this.charset = charset;
    }

    public void writeAndFlush(String msg) {
        byte[] bytes = msg.getBytes(charset);
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer);
    }
}
