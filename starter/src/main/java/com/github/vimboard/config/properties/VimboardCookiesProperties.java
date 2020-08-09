package com.github.vimboard.config.properties;

/**
 * Cookie settings mapping to Spring Boot application properties.
 */
public class VimboardCookiesProperties {

    /**
     * Make this something long and random for security.
     */
    private String salt;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    /**
     * Getter for {@link #salt}.
     *
     * @return field value.
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Setter for {@link #salt}.
     *
     * @param salt new field value.
     * @return {@code this}.
     */
    public VimboardCookiesProperties setSalt(String salt) {
        this.salt = salt;
        return this;
    }
}
