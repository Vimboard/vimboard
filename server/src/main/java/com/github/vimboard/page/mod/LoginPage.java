package com.github.vimboard.page.mod;

public class LoginPage {

    private String error;
    private String username;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public String getError() {
        return error;
    }

    public LoginPage setError(String error) {
        this.error = error;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LoginPage setUsername(String username) {
        this.username = username;
        return this;
    }
}
