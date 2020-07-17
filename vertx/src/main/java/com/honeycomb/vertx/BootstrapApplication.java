package com.honeycomb.vertx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class BootstrapApplication {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10088);
        Socket accept = serverSocket.accept();
        InputStream inputStream = accept.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println(bufferedReader.readLine());
        serverSocket.close();
    }

}
