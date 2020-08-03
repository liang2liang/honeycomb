package com.honeycomb.springboot.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class MockKafkaSender implements InitializingBean {

    private Map<String, Object> listenerMap;

    private AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    public MockKafkaSender() {
        this.listenerMap = new HashMap<>(16);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (atomicBoolean.get()) {
            List<String> list = new ArrayList<>(10);
            new Thread(new MessageProcess(list, listenerMap)).start();
            new Thread(new Sender(list)).start();
            atomicBoolean.set(false);
        }
    }

    public void add(String topic, Object o) {
        listenerMap.put(topic, o);
    }

    public class Sender implements Runnable {

        private final List<String> list;

        public Sender(List<String> list) {
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (list) {
                    list.add(LocalDateTime.now().toString());
                }
            }
        }
    }

    public class MessageProcess implements Runnable {

        private final List<String> list;

        private final Map<String, Object> listenerMap;

        public MessageProcess(List<String> list, Map<String, Object> listenerMap) {
            this.list = list;
            this.listenerMap = listenerMap;
        }

        @Override
        public void run() {
            while (true) {
                if (!CollectionUtils.isEmpty(list)) {
                    synchronized (list) {
                        if (!list.isEmpty()) {
                            String remove = list.remove(0);
                            Object test = listenerMap.get("test");
                            try {
                                test.getClass().getMethod("listen", String.class).invoke(test, remove);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}
