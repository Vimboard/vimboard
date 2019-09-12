package com.github.vimboard.server.domain.templates;

import java.util.Map;

public class ConfigModel {

    /**
     * Vimboard version.
     */
    private String version;

    /**
     * Custom stylesheets available for the user to choose. See the
     * "stylesheets/" folder for a list of available stylesheets (or
     * create your own).
     */
    private Map<String, String> stylesheets;

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
