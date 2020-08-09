package com.github.vimboard.config.settings;

import com.github.vimboard.config.properties.VimboardCookiesProperties;

/**
 * Cookie settings.
 */
public class VimboardCookiesSettings {

    /** {@link VimboardCookiesProperties#getSalt()} */
    private String salt;

    //------------------------------------------------------------------------
    // Getters and setters
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
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }
}
