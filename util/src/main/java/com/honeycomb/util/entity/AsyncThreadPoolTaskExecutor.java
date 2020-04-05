package com.honeycomb.util.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 异步线程池
 */
public class AsyncThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    private static final Logger logger = LoggerFactory.getLogger(AsyncThreadPoolTaskExecutor.class);

    private long leftTask = 0;

    private long initExecutorQueueSize;

    private DefaultThreadRejectedExecutionHandler defaultThreadRejectedExecutionHandler;

    private static final int DEFAULT_LOAD_RATIO_SCALE = 2;

    private String asyncThreadNamePrefix;

    public AsyncThreadPoolTaskExecutor(String threadNamePrefix,
                                       Integer corePoolSize,
                                       Integer maxPoolSize,
                                       Integer keepAliveSeconds,
                                       Integer queueCapacity) {
        this(threadNamePrefix, corePoolSize, maxPoolSize, keepAliveSeconds, queueCapacity, null);
    }


    public AsyncThreadPoolTaskExecutor(String threadNamePrefix,
                                       Integer corePoolSize,
                                       Integer maxPoolSize,
                                       Integer keepAliveSeconds,
                                       Integer queueCapacity,
                                       RejectedExecutionHandler rejectedExecutionHandler) {
        super();
        this.setThreadNamePrefix(threadNamePrefix);
        this.setCorePoolSize(corePoolSize);
        this.setMaxPoolSize(maxPoolSize);
        this.setKeepAliveSeconds(keepAliveSeconds);
        this.setQueueCapacity(queueCapacity);
        this.setRejectedExecutionHandler(Optional.ofNullable(rejectedExecutionHandler).orElse(new DefaultThreadRejectedExecutionHandler()));
        this.initExecutorQueueSize = queueCapacity;
        this.asyncThreadNamePrefix = threadNamePrefix;
        initialize();
        startMonitor();
    }

    /**
     * 负载比率
     *
     * @return
     */
    public double getLoadRatio() {
        long taskCount = this.getThreadPoolExecutor().getTaskCount();
        long completedTaskCount = this.getThreadPoolExecutor().getCompletedTaskCount();
        Long leftTaskCount = (taskCount - completedTaskCount);
        Long totalLoad = this.getMaxPoolSize() + this.initExecutorQueueSize;
        DefaultThreadRejectedExecutionHandler defaultThreadRejectedExecutionHandler = this.defaultThreadRejectedExecutionHandler;
        int rejectQueueSize = 0;
        if (defaultThreadRejectedExecutionHandler != null) {
            rejectQueueSize = defaultThreadRejectedExecutionHandler.rejectedQueue.size();
        }
        Long currentLoad = leftTaskCount + rejectQueueSize;
        return BigDecimal.valueOf(currentLoad)
                .divide(BigDecimal.valueOf(totalLoad), DEFAULT_LOAD_RATIO_SCALE, RoundingMode.HALF_UP)
                .doubleValue();
    }


    /**
     * 监控线程
     */
    private void startMonitor() {
        try {
            Thread.sleep(10000);
            new AsyncPoolTaskMonitorThread(this).start();
        } catch (InterruptedException e) {
            logger.error(" AsyncThreadPoolTaskExecutor startMonitor sleep current exception : {}", this.asyncThreadNamePrefix, e);
            Thread.currentThread().interrupt();
        }
    }


    /**
     * 监控线程
     */
    class AsyncPoolTaskMonitorThread extends Thread {

        private AsyncThreadPoolTaskExecutor asyncThreadPoolTaskExecutor;

        public AsyncPoolTaskMonitorThread(AsyncThreadPoolTaskExecutor asyncThreadPoolTaskExecutor) {
            this.asyncThreadPoolTaskExecutor = asyncThreadPoolTaskExecutor;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    int poolSize = asyncThreadPoolTaskExecutor.getPoolSize();
                    int largestPoolSize = asyncThreadPoolTaskExecutor.getThreadPoolExecutor().getLargestPoolSize();
                    int maxPoolSize = asyncThreadPoolTaskExecutor.getMaxPoolSize();
                    int activeCount = asyncThreadPoolTaskExecutor.getActiveCount();
                    long taskCount = asyncThreadPoolTaskExecutor.getThreadPoolExecutor().getTaskCount();
                    long completedTaskCount = asyncThreadPoolTaskExecutor.getThreadPoolExecutor().getCompletedTaskCount();
                    int executorQueueSize = asyncThreadPoolTaskExecutor.getThreadPoolExecutor().getQueue().size();
                    int rejectedRecoverTaskTimes = 0;
                    if (asyncThreadPoolTaskExecutor.defaultThreadRejectedExecutionHandler != null) {
                        rejectedRecoverTaskTimes = asyncThreadPoolTaskExecutor.defaultThreadRejectedExecutionHandler.rejectedRecoverTaskTimes.intValue();
                    }
                    RejectedExecutionHandler rejectedExecutionHandler = asyncThreadPoolTaskExecutor.getThreadPoolExecutor().getRejectedExecutionHandler();
                    DefaultThreadRejectedExecutionHandler defaultThreadRejectedExecutionHandler = null;
                    if (rejectedExecutionHandler != null && rejectedExecutionHandler instanceof DefaultThreadRejectedExecutionHandler) {
                        defaultThreadRejectedExecutionHandler = asyncThreadPoolTaskExecutor.defaultThreadRejectedExecutionHandler;
                    }
                    int rejectQueueSize = 0;
                    if (defaultThreadRejectedExecutionHandler != null) {
                        rejectQueueSize = defaultThreadRejectedExecutionHandler.rejectedQueue.size();
                    }
                    //  赋值剩余任务
                    leftTask = (taskCount - completedTaskCount);
                    logger.info(" AsyncThreadPoolTaskExecutor threadNamePrefix : {} monitor active : {} , poolSize : {}, maxPoolSize: {} , largestPoolSize : {} , taskCount : {} , completedTaskCount : {} , leftTask : {} , executorQueueSize : {} , rejectQueueSize : {}, rejectedRecoverTaskTimes : {} , loadRatio : {} , initExecutorQueueSize : {} ",
                            asyncThreadNamePrefix,
                            activeCount,
                            poolSize,
                            maxPoolSize,
                            largestPoolSize,
                            taskCount,
                            completedTaskCount,
                            leftTask,
                            executorQueueSize,
                            rejectQueueSize,
                            rejectedRecoverTaskTimes,
                            getLoadRatio(),
                            initExecutorQueueSize
                    );
                    if (rejectQueueSize > 0) {
                        logger.warn(" AsyncThreadPoolTaskExecutor threadNamePrefix : {} monitor rejectedQueue appears data active : {} , poolSize : {}, maxPoolSize: {} , largestPoolSize : {} , taskCount : {} , completedTaskCount : {} , leftTask : {} , executorQueueSize : {} , rejectQueueSize : {}, rejectedRecoverTaskTimes : {}  , loadRatio : {} , initExecutorQueueSize : {}",
                                asyncThreadNamePrefix,
                                activeCount,
                                poolSize,
                                maxPoolSize,
                                largestPoolSize,
                                taskCount,
                                completedTaskCount,
                                leftTask,
                                executorQueueSize,
                                rejectQueueSize,
                                rejectedRecoverTaskTimes,
                                getLoadRatio(),
                                initExecutorQueueSize
                        );
                    }
                } catch (Exception e) {
                    logger.error(" asyncThreadPoolTaskExecutor monitor current exception ", e);
                } finally {
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        logger.error("  asyncThreadPoolTaskExecutor monitor sleep current exception executor : {}", asyncThreadPoolTaskExecutor, e);
                    }
                }
            }
        }
    }

    /**
     * 拒绝处理类的handler
     */
    public class DefaultThreadRejectedExecutionHandler implements RejectedExecutionHandler {

        private final Logger logger = LoggerFactory.getLogger(AsyncThreadPoolTaskExecutor.class);

        private BlockingQueue<Runnable> rejectedQueue = new LinkedBlockingQueue<Runnable>();

        private AtomicInteger rejectedRecoverTaskTimes = new AtomicInteger(0);

        private AsyncThreadPoolTaskExecutor asyncThreadPoolTaskExecutor;

        public DefaultThreadRejectedExecutionHandler() {
            super();
            asyncThreadPoolTaskExecutor = AsyncThreadPoolTaskExecutor.this;
            this.startRejected();
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            logger.error(" ThreadAsyncExecutor rejectedExecution has triggered rejected task : {} ; rejectedFrom : {}  ", r, getThreadNamePrefix());
            try {
                rejectedQueue.offer(r);
            } catch (Exception e) {
                logger.error(" rejectedExecution offer to queue current exception rejectedFrom : {} ", getThreadNamePrefix(), e);
            }
        }

        /**
         * 开启拒绝恢复
         */
        public void startRejected() {
            new AsyncPoolTaskRejectedRecoverThread(AsyncThreadPoolTaskExecutor.this).start();
        }

        /**
         * 执行决绝处理
         *
         * @throws InterruptedException
         */
        public void executeReject() throws Exception {
            long leftTask = asyncThreadPoolTaskExecutor.leftTask;
            long initExecutorQueueSize = asyncThreadPoolTaskExecutor.initExecutorQueueSize;
            if (leftTask < (initExecutorQueueSize + asyncThreadPoolTaskExecutor.getMaxPoolSize())) {
                Runnable task = rejectedQueue.take();
                asyncThreadPoolTaskExecutor.execute(task);
                rejectedRecoverTaskTimes.incrementAndGet();
            } else {
                Thread.sleep(500);
            }
        }

        /**
         * 拒绝任务恢复线程
         */
        public class AsyncPoolTaskRejectedRecoverThread extends Thread {

            private AsyncThreadPoolTaskExecutor asyncThreadPoolTaskExecutor;

            AsyncPoolTaskRejectedRecoverThread(AsyncThreadPoolTaskExecutor asyncThreadPoolTaskExecutor) {
                this.asyncThreadPoolTaskExecutor = asyncThreadPoolTaskExecutor;
            }

            @Override
            public void run() {
                while (true) {
                    try {
                        executeReject();
                    } catch (Throwable e) {
                        logger.error(" rejectedQueue recover execute task current exception executor : {} ", asyncThreadPoolTaskExecutor, e);
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e1) {
                            logger.error("  rejectedQueue recover execute sleep current exception executor : {}", asyncThreadPoolTaskExecutor, e1);
                        }
                    }
                }
            }
        }
    }

    /**
     * 设置拒绝策略
     *
     * @param rejectedExecutionHandler
     */
    @Override
    public void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        super.setRejectedExecutionHandler(rejectedExecutionHandler);
        if (rejectedExecutionHandler instanceof DefaultThreadRejectedExecutionHandler) {
            this.defaultThreadRejectedExecutionHandler = ((DefaultThreadRejectedExecutionHandler) rejectedExecutionHandler);
        }
    }


    @Override
    public String toString() {

        return "AsyncThreadPoolTaskExecutor{" +
                "corePoolSize=" + this.getCorePoolSize() +
                ", maxPoolSize=" + this.getMaxPoolSize() +
                ", keepAliveSeconds=" + this.getKeepAliveSeconds() +
                ", leftTask=" + leftTask +
                ", executorQueueSize=" + initExecutorQueueSize +
                ", asyncThreadNamePrefix=" + asyncThreadNamePrefix +
                '}';
    }
}