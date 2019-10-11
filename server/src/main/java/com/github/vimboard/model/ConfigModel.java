package com.github.vimboard.model;

public class ConfigModel {

    private String[] defaultStylesheet = null; // TODO replace array. `0` value is used only in `main.js`

    private String version;

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
}
