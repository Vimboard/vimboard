package com.github.vimboard.model.mod;

public class NewPmPage {

    private int id;
    private String message;
    private String token;
    private String username;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public NewPmPage setId(int id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public NewPmPage setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getToken() {
        return token;
    }

    public NewPmPage setToken(String token) {
        this.token = token;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public NewPmPage setUsername(String username) {
        this.username = username;
        return this;
    }
}
