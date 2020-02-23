package com.github.vimboard.config.properties;

public class VimboardCookiesProperties {

    /**
     * Make this something long and random for security.
     */
    private String salt;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public String getSalt() {
        return salt;
    }

    public VimboardCookiesProperties setSalt(String salt) {
        this.salt = salt;
        return this;
    }
}
