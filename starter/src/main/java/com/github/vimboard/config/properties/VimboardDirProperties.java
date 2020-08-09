package com.github.vimboard.config.properties;

/**
 * Directory settings mapping to Spring Boot application properties.
 */
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
    // Getters and builder setters
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
     * @return {@code this}.
     */
    public VimboardDirProperties setImg(String img) {
        this.img = img;
        return this;
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
     * @return {@code this}.
     */
    public VimboardDirProperties setThumb(String thumb) {
        this.thumb = thumb;
        return this;
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
     * @return {@code this}.
     */
    public VimboardDirProperties setRes(String res) {
        this.res = res;
        return this;
    }
}
