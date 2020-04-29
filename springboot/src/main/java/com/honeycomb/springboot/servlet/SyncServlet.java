package com.honeycomb.springboot.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author maoliang
 * @version 1.0.0
 */
@WebServlet(value = "/sync-test")
public class SyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new Thread(() -> {
            System.out.println("执行开始啦");

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                resp.getWriter().write("Hello World!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("执行完成啦");
        }).start();

        System.out.println("反正我结束啦");
    }
}
