package com.github.vimboard.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableConfigurationProperties(VimboardProperties.class)
@EnableTransactionManagement
public class VimboardConfig {

    @Bean
    public VimboardBean getVimboardBean(VimboardProperties vimboardProperties) {
        return new VimboardBean(vimboardProperties);
    }
}
