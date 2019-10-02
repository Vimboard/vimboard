package com.github.vimboard.model;

import java.util.Map;

public class ConfigModel {

    /**
     * TODO page body data-stylesheet = ( defaultStylesheet[1] is empty ? default : defaultStylesheet[1] )
     */
    private String[] defaultStylesheet = null;

    /**
     * Vimboard version.
     */
    private String version;

    /**
     * Custom stylesheets available for the user to choose. See the
     * "stylesheets/" folder for a list of available stylesheets (or
     * create your own).
     */
    // TODO: unused
    private Map<String, String> stylesheets;

    public String[] getDefaultStylesheet() {
        return defaultStylesheet;
    }

    public ConfigModel setDefaultStylesheet(String[] defaultStylesheet) {
        this.defaultStylesheet = defaultStylesheet;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ConfigModel setVersion(String version) {
        this.version = version;
        return this;
    }

    public Map<String, String> getStylesheets() {
        return stylesheets;
    }

    public ConfigModel setStylesheets(Map<String, String> stylesheets) {
        this.stylesheets = stylesheets;
        return this;
    }
}
