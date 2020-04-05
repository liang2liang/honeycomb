package com.honeycomb.java.nine;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class SreamTest {

    public static void main(String[] args) {
        Optional<Integer> reduce = Stream.of(1, 2, 3).reduce((a, b) -> a > b ? a : b);
        System.out.println(reduce.get());
    }
}
