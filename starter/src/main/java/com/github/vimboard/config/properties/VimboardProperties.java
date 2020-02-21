package com.github.vimboard.config.properties;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

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
    // Getters and setters
    //------------------------------------------------------------------------

    public String getWww() {
        return www;
    }

    public VimboardProperties setWww(String www) {
        this.www = www;
        return this;
    }

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
}
