package com.github.vimboard.config.settings;

import com.github.vimboard.config.properties.VimboardApiProperties;

/**
 * API settings.
 */
public class VimboardApiSettings {

    /** {@link VimboardApiProperties#getEnabled()} */
    private Boolean enabled;

    //------------------------------------------------------------------------
    // Getters and setters
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
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
