package com.honeycomb.resilience4j.demo.timelimiter;

import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeLimiterConfiguration {

    @Autowired
    private TimeLimiterProperties timeLimiterProperties;

    @Bean
    public TimeLimiter timeLimiter(){
        return TimeLimiter.of(timeLimiterConfig());
    }

    private TimeLimiterConfig timeLimiterConfig(){
        return TimeLimiterConfig.custom()
                .timeoutDuration(timeLimiterProperties.getTimeoutDuration())
                .cancelRunningFuture(timeLimiterProperties.isCancelRunningFuture()).build();
    }
}