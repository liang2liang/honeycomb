package com.honeycomb.java.eight;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test();
    }

    public static void testGetAndJoin() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return 100;
        });
//        future.join();
        future.get();
    }

    public static void test() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(System.currentTimeMillis());
            return IntStream.range(1, 10).parallel().reduce(0, (one, two) -> one + two);
        })
                .thenApplyAsync(sum -> {
                    System.out.println(System.currentTimeMillis());
                    return IntStream.range(1, 10).reduce(sum, (one, two) -> one + two);
                })
                .thenCompose(sum -> {
                    System.out.println(System.currentTimeMillis());
                    return CompletableFuture.completedFuture("success");
                });
        System.out.println(completableFuture.join());
    }
}
