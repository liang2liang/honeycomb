package com.honeycomb.springboot.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
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
@WebServlet(value = "/async-test", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext asyncContext = req.startAsync();
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                System.out.println("complete");
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                System.out.println("timeout");
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                System.out.println("error:" + event.getThrowable());
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                System.out.println("start async timeout is :" + event.getAsyncContext().getTimeout());
            }
        });

        asyncContext.start(() -> {
            System.out.println("执行开始啦");

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                asyncContext.getResponse().getWriter().write("Hello World!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("执行完成啦");
            asyncContext.complete();
        });

        System.out.println("反正我结束啦");
    }
}
