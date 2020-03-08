package com.github.vimboard.model;

public class ConfirmModel {

    /**
     * Request uri to confirm.
     */
    private String request;

    /**
     * Token that is used to confirm request.
     */
    private String token;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public String getRequest() {
        return request;
    }

    public ConfirmModel setRequest(String request) {
        this.request = request;
        return this;
    }

    public String getToken() {
        return token;
    }

    public ConfirmModel setToken(String token) {
        this.token = token;
        return this;
    }
}
