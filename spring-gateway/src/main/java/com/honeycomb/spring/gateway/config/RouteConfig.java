package com.honeycomb.spring.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author maoliang
 */
@Configuration(proxyBeanMethods = false)
public class RouteConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("path_route", r -> r.path("/get")
                        .filters(f -> f.addRequestHeader("honeycomb", "HONEY"))
                        .uri("http://httpbin.org"))
                .build();
    }

}
