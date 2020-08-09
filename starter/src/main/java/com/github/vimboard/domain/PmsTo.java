package com.github.vimboard.domain;

public class PmsTo extends Pms {

    private String toUsername;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public String getToUsername() {
        return toUsername;
    }

    public PmsTo setToUsername(String toUsername) {
        this.toUsername = toUsername;
        return this;
    }
}
