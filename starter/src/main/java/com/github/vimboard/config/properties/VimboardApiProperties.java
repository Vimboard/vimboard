package com.github.vimboard.config.properties;

/**
 * API settings mapping to Spring Boot application properties.
 */
public class VimboardApiProperties {

    /**
     * Whether or not to enable the 4chan-compatible API, disabled by default.
     * See https://github.com/4chan/4chan-API for API specification.
     */
    private Boolean enabled;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    /**
     * Getter for {@link #enabled}.
     *
     * @return field value.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Setter for {@link #enabled}.
     *
     * @param enabled new field value.
     * @return {@code this}.
     */
    public VimboardApiProperties setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
