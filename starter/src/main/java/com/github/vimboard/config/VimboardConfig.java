package com.github.vimboard.config;

import com.github.vimboard.config.properties.VimboardProperties;
import com.github.vimboard.config.settings.VimboardSettings;
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

    @Bean
    public VimboardSettings getConfig(VimboardProperties vimboardProperties) {
        return new VimboardSettingsBuilder(vimboardProperties, false).build(); // TODO: runAsCli parameter
    }
}
