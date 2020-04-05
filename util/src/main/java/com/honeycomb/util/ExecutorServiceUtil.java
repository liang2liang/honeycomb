package com.honeycomb.util;

import com.honeycomb.util.entity.AsyncThreadPoolTaskExecutor;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class ExecutorServiceUtil {

    private static final AsyncThreadPoolTaskExecutor executorService;

    static{
        executorService = new AsyncThreadPoolTaskExecutor("ExecutorServiceUtil-", Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors() * 2, 60, 100);
        Runtime.getRuntime().addShutdownHook(new Thread(executorService::shutdown));
    }

    public static void execute(Runnable runnable){
        executorService.execute(runnable);
    }
}
