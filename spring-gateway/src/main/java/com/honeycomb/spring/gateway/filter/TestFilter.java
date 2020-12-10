package com.honeycomb.spring.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author maoliang
 */
@Component
public class TestFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println(exchange.getResponse().getStatusCode());

//        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//            ServerHttpResponse response = exchange.getResponse();
//            //Manipulate the response in some way
//        }));
        Mono<Void> then = chain.filter(exchange).then(Mono.fromRunnable(() -> {
            System.out.println(exchange.getResponse().getHeaders());
        }));
        return then.doOnSuccess(aVoid -> {
            System.out.println(exchange.getResponse().getHeaders());
        });
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
