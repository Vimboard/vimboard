package com.github.vimboard.config.settings;

import com.github.vimboard.config.properties.VimboardLockProperties;
import com.github.vimboard.domain.LockDriver;

public class VimboardLockSettings {

    /** {@link VimboardLockProperties#getEnabled()} */
    private LockDriver enabled;

    //------------------------------------------------------------------------
    // Getters and setters
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
     */
    public void setEnabled(LockDriver enabled) {
        this.enabled = enabled;
    }
}
