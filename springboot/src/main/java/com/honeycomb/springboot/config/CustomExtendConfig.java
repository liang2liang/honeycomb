package com.honeycomb.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author maoliang
 * @version 1.0.0
 */
@Configuration
@Import(CustomBeanDefinitionRegister.class)
public class CustomExtendConfig {
}
