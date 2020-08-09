package com.github.vimboard.config.properties;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

/**
 * Application settings mapping to Spring Boot application properties.
 */
public class VimboardProperties {

    /**
     * Static content location.
     */
    private String www;

    //------------------------------------------------------------------------
    // Board settings
    //------------------------------------------------------------------------

    /**
     * The common configuration for all boards.
     */
    @NestedConfigurationProperty
    private VimboardBoardProperties all;

    /**
     * Separate configurations that overrides settings
     * of the common configuration for each board.
     */
    private Map<String, VimboardBoardProperties> custom;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    /**
     * Getter for {@link #www}.
     *
     * @return field value.
     */
    public String getWww() {
        return www;
    }

    /**
     * Setter for {@link #www}.
     *
     * @param www new field value.
     * @return {@code this}.
     */
    public VimboardProperties setWww(String www) {
        this.www = www;
        return this;
    }

    /**
     * Getter for {@link #all}.
     *
     * @return field value.
     */
    public VimboardBoardProperties getAll() {
        return all;
    }

    /**
     * Setter for {@link #all}.
     *
     * @param all new field value.
     * @return {@code this}.
     */
    public VimboardProperties setAll(VimboardBoardProperties all) {
        this.all = all;
        return this;
    }

    /**
     * Getter for {@link #custom}.
     *
     * @return field value.
     */
    public Map<String, VimboardBoardProperties> getCustom() {
        return custom;
    }

    /**
     * Setter for {@link #custom}.
     *
     * @param custom new field value.
     * @return {@code this}.
     */
    public VimboardProperties setCustom(Map<String, VimboardBoardProperties> custom) {
        this.custom = custom;
        return this;
    }
}
