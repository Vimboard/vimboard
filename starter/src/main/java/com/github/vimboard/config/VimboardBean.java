package com.github.vimboard.config;

import org.springframework.beans.factory.annotation.Autowired;

public class VimboardBean {

    private final VimboardProperties vimboardProperties;

    @Autowired
    public VimboardBean(VimboardProperties vimboardProperties) {
        this.vimboardProperties = vimboardProperties;
    }

    public String getWww() {
        return vimboardProperties.getWww();
    }
}
