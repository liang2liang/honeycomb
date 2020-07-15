package com.honeycomb.java.socket;

import lombok.SneakyThrows;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * 长连接：
 * 优点：除了第一次之外，客户端不需要每次传输数据时都先与服务端进行3次握手，这样减少了握手确认的时间，直接传输数据，提高程序运行效率。
 * 缺点：在服务端保存了多个Socket对象，大量占用服务器资源。
 * 短连接：
 * 优点：在服务端不需要保存多个Socket对象，降低内存占用率。
 * 缺点：每次传输数据前都要重新创建连接，也就是每次都要进行3次握手，增加处理的时间。
 */
public class Server {

    @SneakyThrows
    public static void main(String[] args) throws IOException {
        // 1. accept阻塞测试
//        new Thread(() -> accept()).start();
//        //确保先打开服务监听
//        TimeUnit.SECONDS.sleep(1);
//        new Thread(() -> connect()).start();
//        TimeUnit.SECONDS.sleep(1);

        // 2. httpServer测试
        // 在IE中可以，在google中不可以，感觉是google把非https的请求拦截了
        httpServer();
    }

    /**
     * accept方法时阻塞的。此方法在连接传入之前一直阻塞、
     *
     * @throws IOException
     */
    @SneakyThrows
    public static void accept() {
        ServerSocket serverSocket = new ServerSocket(8088);
        System.out.println("server 阻塞开始 = " + System.currentTimeMillis());
        serverSocket.accept();
        System.out.println("server 阻塞结束 = " + System.currentTimeMillis());
        serverSocket.close();
    }

    @SneakyThrows
    public static void connect() {
        System.out.println("client 连接准备 = " + System.currentTimeMillis());
        Socket socket = new Socket("localhost", 8088);
        System.out.println("client 连接关闭 = " + System.currentTimeMillis());
        socket.close();
    }

    @SneakyThrows
    public static void httpServer(){
        ServerSocket serverSocket = new ServerSocket(6666);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String readStr = "";
        while(!"".equals(readStr = bufferedReader.readLine())){
            System.out.println(readStr);
        }

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
        outputStream.write("<html><body><a href='http://www.baidu.com'>I am baidu.com welcome you!</a><body><html>".getBytes());
        outputStream.flush();

        inputStream.close();
        outputStream.close();
        socket.close();
        serverSocket.close();
    }
}
