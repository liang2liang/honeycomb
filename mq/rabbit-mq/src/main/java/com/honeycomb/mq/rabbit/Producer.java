package com.honeycomb.mq.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        //设置 RabbitMQ 地址
        factory.setHost("49.232.164.192");
        //设置 RabbitMQ 端口
        factory.setPort(5672);
        //建立到代理服务器到连接
        Connection conn = factory.newConnection();
        //获得信道
        Channel channel = conn.createChannel();
        //声明交换器
        String exchangeName = "hello-exchange";
        channel.exchangeDeclare(exchangeName, "direct", true);

        String routingKey = "hola";
        //发布消息
        byte[] messageBodyBytes = "quit".getBytes();
        channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);

        channel.close();
        conn.close();
    }
}