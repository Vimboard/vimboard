package com.github.vimboard.config;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

public class VimboardProperties {

    /**
     * The common configuration for all boards.
     */
    @NestedConfigurationProperty
    private BoardProperties all;

    /**
      * Separate configurations that overrides settings
      * of the common configuration for each board.
      */
    private Map<String, BoardProperties> custom;

    /**
      * Static content location.
      */
    private String www;

    // Init ------------------------------------------------------------------

    @PostConstruct
    public void init() {

        if (all == null) {
            all = new BoardProperties();
        }
        BoardProperties.init(all, null);

        if (custom == null) {
            custom = new HashMap<>();
        }
        for (String boardUrl : custom.keySet()) {
            BoardProperties b = custom.get(boardUrl);
            BoardProperties.init(b, this);
        }

        if (www == null || www.isEmpty()) {
            www = "/var/www/vimboard/public/";
        }
    }

    // Getters and setters ---------------------------------------------------

    public BoardProperties getAll() {
        return all;
    }

    public VimboardProperties setAll(BoardProperties all) {
        this.all = all;
        return this;
    }

    public Map<String, BoardProperties> getCustom() {
        return custom;
    }

    public VimboardProperties setCustom(Map<String, BoardProperties> custom) {
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

    // Static ----------------------------------------------------------------

    public static BoardProperties props(
            VimboardProperties vimboardProperties, String boardUri) {
        final BoardProperties result = vimboardProperties.custom.get(boardUri);
        return (result == null) ? vimboardProperties.all : result;
    }
}
