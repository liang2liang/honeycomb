package com.honeycomb.springboot.controller;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author maoliang
 * @version 1.0.0
 */
@RestController
public class WebFluxController {

    @GetMapping("/sayHello")
    public Mono<String> sayHelloWorld(){
        return Mono.just("Hello World!");
    }

    @GetMapping("/randomNumbers")
    public Flux<ServerSentEvent<Integer>> randomNumbers(){
        return Flux.interval(Duration.ofSeconds(2))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .log()
                .map(data -> ServerSentEvent.<Integer>builder()
                        .event("random")
                        .id(Long.toString(data.getT1()))
                        .data(data.getT2())
                        .build());
    }
}
