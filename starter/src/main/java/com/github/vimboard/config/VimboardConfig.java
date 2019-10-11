package com.github.vimboard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class VimboardConfig {

    @Bean
    @ConfigurationProperties(prefix = "vimboard")
    public VimboardProperties getVimboardProperties() {
        return new VimboardProperties();
    }
}
