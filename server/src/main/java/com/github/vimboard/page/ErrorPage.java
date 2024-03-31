package com.github.vimboard.page;

public class ErrorPage {

    /**
     * Error message to be displayed.
     */
    private String message;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public String getMessage() {
        return message;
    }

    public ErrorPage setMessage(String message) {
        this.message = message;
        return this;
    }
}
