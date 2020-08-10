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
     * Vimboard version.
     */
    private String version;

    /**
     * Returns board settings by the board uri.
     *
     * @param boardUri the board uri.
     * @return board settings.
     */
    public VimboardBoardSettings getCustom(String boardUri) {
        final VimboardBoardSettings result =  getCustom().get(boardUri);
        return result == null ? getAll() : result;
    }

    //------------------------------------------------------------------------
    // Getters and setters
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
     */
    public void setWww(String www) {
        this.www = www;
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
     */
    public void setAll(VimboardBoardSettings all) {
        this.all = all;
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
     */
    public void setCustom(Map<String, VimboardBoardSettings> custom) {
        this.custom = custom;
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
     */
    public void setRunAsCli(boolean runAsCli) {
        this.runAsCli = runAsCli;
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
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
