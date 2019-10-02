package com.github.vimboard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "vimboard")
public class VimboardProperties {

    private String www;

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }
}
