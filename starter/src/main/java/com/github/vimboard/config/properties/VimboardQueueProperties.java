package com.github.vimboard.config.properties;

import com.github.vimboard.domain.QueueDriver;

public class VimboardQueueProperties {

    /**
     * Define a queue driver.
     */
    private QueueDriver enabled;

    //------------------------------------------------------------------------
    // Getters and builder setters
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
     * @return {@code this}.
     */
    public VimboardQueueProperties setEnabled(QueueDriver enabled) {
        this.enabled = enabled;
        return this;
    }
}
