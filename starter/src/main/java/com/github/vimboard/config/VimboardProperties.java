package com.github.vimboard.config;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

public class VimboardProperties {

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

    /**
      * Static content location.
      */
    private String www;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public VimboardBoardProperties getAll() {
        return all;
    }

    public VimboardProperties setAll(VimboardBoardProperties all) {
        this.all = all;
        return this;
    }

    public Map<String, VimboardBoardProperties> getCustom() {
        return custom;
    }

    public VimboardProperties setCustom(Map<String, VimboardBoardProperties> custom) {
        this.custom = custom;
        return this;
    }

    public String getWww() {
        return www;
    }

    public VimboardProperties setWww(String www) {
        this.www = www;
        return this;
    }
}
