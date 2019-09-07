package com.github.vimboard.starter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FooProperties.class)
public class FooConfig {

    @Bean
    public FooBean fooBean(FooProperties fooProperties) {
        return new FooBean(fooProperties);
    }
}
