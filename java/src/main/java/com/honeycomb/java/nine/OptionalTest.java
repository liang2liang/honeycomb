package com.honeycomb.java.nine;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class OptionalTest {

    public static void main(String[] args) {
        Integer integer = Optional.of(1).flatMap(i -> Optional.of(2)).orElse(0);
        System.out.println(integer);



        List<Optional<String>> list = Arrays.asList (
                Optional.empty(),
                Optional.of("A"),
                Optional.empty(),
                Optional.of("B"));

        //filter the list based to print non-empty values

        //if optional is non-empty, get the value in stream, otherwise return empty
        List<String> filteredList = list.stream()
                .flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty())
                .collect(Collectors.toList());

        //Optional::stream method will return a stream of either one
        //or zero element if data is present or not.
        List<String> filteredListJava9 = list.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        System.out.println(filteredList);
        System.out.println(filteredList.size());
        System.out.println(filteredListJava9);
        System.out.println(filteredListJava9.size());


        Optional.of(1).ifPresentOrElse(i -> System.out.println(i), () -> System.out.println("error"));

        Optional.empty().or(() -> Optional.of(2)).ifPresent(i -> System.out.println(i));
        Optional.of(1).or(() -> Optional.of(2)).ifPresent(i -> System.out.println(i));
    }
}
