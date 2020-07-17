package com.honeycomb.io.aio.client;

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
public class AioClientHandler extends ChannelAdapter {

    public AioClientHandler(AsynchronousSocketChannel socketChannel, Charset charset) {
        super(socketChannel, charset);
    }

    @SneakyThrows
    @Override
    public void channelActive(ChannelHandler ctx) {
        System.out.println("客户端链接信息报告：" + ctx.getChannel().getRemoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandler ctx) {

    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        System.out.println("服务端收到数据: ".concat(new Date().toString()).concat(" ").concat(msg.toString()));
        ctx.writeAndFlush("客户端信息处理成功！\r\n");
    }
}
