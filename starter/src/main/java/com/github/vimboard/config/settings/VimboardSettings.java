package com.github.vimboard.config.settings;

import com.github.vimboard.config.properties.VimboardProperties;

import java.util.Map;

/**
 * Application settings.
 */
public class VimboardSettings {

    /** {@link VimboardProperties#getWww()} */
    private String www;

    // Board settings

    /** {@link VimboardProperties#getAll()} */
    private VimboardBoardSettings all;
    /** {@link VimboardProperties#getCustom()} */
    private Map<String, VimboardBoardSettings> custom;

    // Read-only settings

    /**
     * Application launch mode.
     * {@code true} if the application is launched in CLI mode.
     */
    private boolean runAsCli;

    /**
     * TODO
     */
    private String version;

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
    public VimboardSettings setWww(String www) {
        this.www = www;
        return this;
    }

    /**
     * Getter for {@link #all}.
     *
     * @return field value.
     */
    public VimboardBoardSettings getAll() {
        return all;
    }

    /**
     * Setter for {@link #all}.
     *
     * @param all new field value.
     * @return {@code this}.
     */
    public VimboardSettings setAll(VimboardBoardSettings all) {
        this.all = all;
        return this;
    }

    /**
     * Getter for {@link #custom}.
     *
     * @return field value.
     */
    public Map<String, VimboardBoardSettings> getCustom() {
        return custom;
    }

    /**
     * Setter for {@link #custom}.
     *
     * @param custom new field value.
     * @return {@code this}.
     */
    public VimboardSettings setCustom(Map<String, VimboardBoardSettings> custom) {
        this.custom = custom;
        return this;
    }

    /**
     * Getter for {@link #runAsCli}.
     *
     * @return field value.
     */
    public boolean isRunAsCli() {
        return runAsCli;
    }

    /**
     * Setter for {@link #runAsCli}.
     *
     * @param runAsCli new field value.
     * @return {@code this}.
     */
    public VimboardSettings setRunAsCli(boolean runAsCli) {
        this.runAsCli = runAsCli;
        return this;
    }

    /**
     * Getter for {@link #version}.
     *
     * @return field value.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Setter for {@link #version}.
     *
     * @param version new field value.
     * @return {@code this}.
     */
    public VimboardSettings setVersion(String version) {
        this.version = version;
        return this;
    }
}
