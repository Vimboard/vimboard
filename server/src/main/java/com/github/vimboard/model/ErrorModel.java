package com.github.vimboard.model;

public class ErrorModel {

    /**
     * Error message to be displayed.
     */
    private String message;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public String getMessage() {
        return message;
    }

    public ErrorModel setMessage(String message) {
        this.message = message;
        return this;
    }
}
