package com.github.vimboard.config.settings;

import com.github.vimboard.config.properties.VimboardQueueProperties;
import com.github.vimboard.domain.QueueDriver;

public class VimboardQueueSettings {

    /** {@link VimboardQueueProperties#getEnabled()} */
    private QueueDriver enabled;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    /**
     * Getter for {@link #enabled}.
     *
     * @return field value.
     */
    public QueueDriver getEnabled() {
        return enabled;
    }

    /**
     * Setter for {@link #enabled}.
     *
     * @param enabled new field value.
     */
    public void setEnabled(QueueDriver enabled) {
        this.enabled = enabled;
    }
}
