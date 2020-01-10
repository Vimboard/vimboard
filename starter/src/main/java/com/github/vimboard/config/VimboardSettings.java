package com.github.vimboard.config;

import java.util.Map;

public class VimboardSettings {

    private VimboardBoardSettings all;
    private Map<String, VimboardBoardSettings> custom;
    private String www;

    public VimboardBoardSettings getAll() {
        return all;
    }

    public VimboardSettings setAll(VimboardBoardSettings all) {
        this.all = all;
        return this;
    }

    public Map<String, VimboardBoardSettings> getCustom() {
        return custom;
    }

    public VimboardSettings setCustom(Map<String, VimboardBoardSettings> custom) {
        this.custom = custom;
        return this;
    }

    public String getWww() {
        return www;
    }

    public VimboardSettings setWww(String www) {
        this.www = www;
        return this;
    }
}
