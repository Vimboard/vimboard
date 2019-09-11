package com.github.vimboard.starter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(VimboardProperties.class)
public class VimboardConfig {

    @Bean
    public VimboardBean getVimboardBean(VimboardProperties vimboardProperties) {
        return new VimboardBean(vimboardProperties);
    }
}
