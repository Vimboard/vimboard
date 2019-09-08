package com.github.vimboard.starter;

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
