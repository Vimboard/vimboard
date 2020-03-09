package com.github.vimboard.model.mod;

public class LoginModel {

    private String error;
    private String username;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public String getError() {
        return error;
    }

    public LoginModel setError(String error) {
        this.error = error;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LoginModel setUsername(String username) {
        this.username = username;
        return this;
    }
}
