package com.github.vimboard.config.properties;

import com.github.vimboard.domain.LockDriver;

public class VimboardLockProperties {

    /**
     * Define a lock driver.
     */
    private LockDriver enabled;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    /**
     * Getter for {@link #enabled}.
     *
     * @return field value.
     */
    public LockDriver getEnabled() {
        return enabled;
    }

    /**
     * Setter for {@link #enabled}.
     *
     * @param enabled new field value.
     * @return {@code this}.
     */
    public VimboardLockProperties setEnabled(LockDriver enabled) {
        this.enabled = enabled;
        return this;
    }
}
