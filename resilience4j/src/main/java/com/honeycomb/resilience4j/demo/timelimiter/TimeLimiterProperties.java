package com.honeycomb.resilience4j.demo.timelimiter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@Component
@ConfigurationProperties(prefix = "resilience4j.timelimiter")
public class TimeLimiterProperties {

    private Duration timeoutDuration;

    private boolean cancelRunningFuture;
}