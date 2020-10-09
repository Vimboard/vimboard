package com.github.vimboard.config.properties;

import java.util.Map;

/**
 * API settings mapping to Spring Boot application properties.
 */
public class VimboardApiProperties {

    /**
     * Whether or not to enable the 4chan-compatible API, disabled by default.
     * See https://github.com/4chan/4chan-API for API specification.
     */
    private Boolean enabled;

    /**
     * TODO docs
     * Extra fields in to be shown in the array that are not in the 4chan-API.
     * You can get these by taking a look at the schema for posts_ tables. The
     * array should be formatted as $db_column => $translated_name.
     * Example: Adding the pre-markup post body to the API as "com_nomarkup".
     *     extra-fields:
     *       body_nomarkup: com_nomarkup
     */
    private Map<String, String> extraFields;

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
     * @return {@code this}.
     */
    public VimboardApiProperties setExtraFields(Map<String, String> extraFields) {
        this.extraFields = extraFields;
        return this;
    }
}
