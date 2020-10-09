package com.github.vimboard.config.settings;

import com.github.vimboard.config.properties.VimboardApiProperties;

import java.util.Map;

/**
 * API settings.
 */
public class VimboardApiSettings {

    /** {@link VimboardApiProperties#getEnabled()} */
    private Boolean enabled;
    /** {@link VimboardApiProperties#getExtraFields()} */
    private Map<String, String> extraFields;

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

    /**
     * Getter for {@link #extraFields}.
     *
     * @return field value.
     */
    public Map<String, String> getExtraFields() {
        return extraFields;
    }

    /**
     * Setter for {@link #extraFields}.
     *
     * @param extraFields new field value.
     */
    public void setExtraFields(Map<String, String> extraFields) {
        this.extraFields = extraFields;
    }
}
