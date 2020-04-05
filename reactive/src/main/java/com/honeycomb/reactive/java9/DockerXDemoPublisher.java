package com.honeycomb.reactive.java9;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Slf4j
public class DockerXDemoPublisher<T> implements Flow.Publisher<T>, AutoCloseable {

    private final ExecutorService executor;

    private CopyOnWriteArrayList<DockerXDemoSubscription> list = new CopyOnWriteArrayList<>();

    public DockerXDemoPublisher(ExecutorService executor) {
        this.executor = executor;
    }

    public void submit(T item) {
        log.info("********* 开始发布元素 ********");
        list.forEach(e -> {
            executor.submit(() -> {
                e.subscriber.onNext(item);
            });
        });
    }

    @Override
    public void close() {
        list.forEach(e -> {
            executor.submit(e.subscriber::onComplete);
        });
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        DockerXDemoSubscription<? super T> dockerXDemoSubscription = new DockerXDemoSubscription<>(subscriber, executor);
        subscriber.onSubscribe(dockerXDemoSubscription);
        list.add(dockerXDemoSubscription);
    }

    static class DockerXDemoSubscription<T> implements Flow.Subscription {

        private final Flow.Subscriber<? super T> subscriber;

        private final ExecutorService executor;

        private Future<?> future;

        private T item;

        private boolean completed;

        DockerXDemoSubscription(Flow.Subscriber<? super T> subscriber, ExecutorService executor) {
            this.subscriber = subscriber;
            this.executor = executor;
        }

        @Override
        public void request(long n) {
            if (n != 0 && !completed) {
                if (n < 0) {
                    executor.execute(() -> subscriber.onError(new IllegalArgumentException()));
                } else {
                    future = executor.submit(() -> subscriber.onNext(item));
                }
            } else {
                subscriber.onComplete();
            }
        }

        @Override
        public void cancel() {
            completed = true;
            if (future != null && !future.isCancelled()) {
                this.future.cancel(true);
            }
        }
    }
}
