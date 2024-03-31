package com.github.vimboard.page;

public class IndexPage {

    /**
     * Content body template file name.
     */
    private String body;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public String getBody() {
        return body;
    }

    public IndexPage setBody(String body) {
        this.body = body;
        return this;
    }
}
