package com.github.vimboard.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("boardConfig")
@ConfigurationProperties(prefix = "board")
public class BoardConfig {

    private String log;
    private String www;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }
}
