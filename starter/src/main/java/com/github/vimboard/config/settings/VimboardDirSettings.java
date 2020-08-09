package com.github.vimboard.config.settings;

import com.github.vimboard.config.properties.VimboardDirProperties;

/**
 * Directory settings.
 */
public class VimboardDirSettings {

    /** {@link VimboardDirProperties#getImg()} */
    private String img;
    /** {@link VimboardDirProperties#getThumb()} */
    private String thumb;
    /** {@link VimboardDirProperties#getRes()} */
    private String res;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    /**
     * Getter for {@link #img}.
     *
     * @return field value.
     */
    public String getImg() {
        return img;
    }

    /**
     * Setter for {@link #img}.
     *
     * @param img new field value.
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * Getter for {@link #thumb}.
     *
     * @return field value.
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * Setter for {@link #thumb}.
     *
     * @param thumb new field value.
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /**
     * Getter for {@link #res}.
     *
     * @return field value.
     */
    public String getRes() {
        return res;
    }

    /**
     * Setter for {@link #res}.
     *
     * @param res new field value.
     */
    public void setRes(String res) {
        this.res = res;
    }
}
