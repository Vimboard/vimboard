package com.github.vimboard.config.properties;

public class VimboardDirProperties {

    /**
     * Directory for images.
     */
    private String img;

    /**
     * Directory for thumbnails.
     */
    private String thumb;

    /**
     * Directory for resources.
     */
    private String res;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public String getImg() {
        return img;
    }

    public VimboardDirProperties setImg(String img) {
        this.img = img;
        return this;
    }

    public String getThumb() {
        return thumb;
    }

    public VimboardDirProperties setThumb(String thumb) {
        this.thumb = thumb;
        return this;
    }

    public String getRes() {
        return res;
    }

    public VimboardDirProperties setRes(String res) {
        this.res = res;
        return this;
    }
}
