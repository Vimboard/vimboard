package com.github.vimboard.model.domain;

import com.github.vimboard.domain.Pms;

public class PmsModel extends Pms {

    private String username;
    private String snippet;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public String getUsername() {
        return username;
    }

    public PmsModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getSnippet() {
        return snippet;
    }

    public PmsModel setSnippet(String snippet) {
        this.snippet = snippet;
        return this;
    }
}
