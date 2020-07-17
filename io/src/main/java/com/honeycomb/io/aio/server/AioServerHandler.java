package com.honeycomb.io.aio.server;

import com.honeycomb.io.aio.ChannelAdapter;
import com.honeycomb.io.aio.ChannelHandler;
import lombok.SneakyThrows;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class AioServerHandler extends ChannelAdapter {

    public AioServerHandler(AsynchronousSocketChannel channel, Charset charset) {
        super(channel, charset);
    }

    @SneakyThrows
    @Override
    public void channelActive(ChannelHandler ctx) {
        System.out.println("服务端链接报告信息：" + ctx.getChannel().getRemoteAddress());
        ctx.writeAndFlush("通知服务端链接建立成功，" + new Date() + " " + ctx.getChannel().getRemoteAddress() + "\r\n");
    }

    @Override
    public void channelInactive(ChannelHandler ctx) {

    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        System.out.println("服务端收到：" + new Date() + " " + msg + "\r\n");
        ctx.writeAndFlush("服务端信息处理成功\r\n");
    }
}
