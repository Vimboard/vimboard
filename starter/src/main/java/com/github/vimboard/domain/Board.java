package com.github.vimboard.domain;

public class Board {

    /**
     * The board identifier.
     */
    private int id;

    /**
     * A board uri wihtout '/' at
     * TODO
     */
    private String uri;

    /**
     * TODO
     */
    private String title;

    /**
     * TODO
     */
    private String subtitle;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    /**
     * Getter for {@link #id}.
     *
     * @return {@link #id}.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for {@link #id}.
     *
     * @param id {@link #id}.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for {@link #uri}.
     *
     * @return {@link #uri}.
     */
    public String getUri() {
        return uri;
    }

    /**
     * Setter for {@link #uri}.
     *
     * @param uri {@link #uri}.
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * Getter for {@link #title}.
     *
     * @return {@link #title}.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for {@link #title}.
     *
     * @param title {@link #title}.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for {@link #subtitle}.
     *
     * @return {@link #subtitle}.
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * Setter for {@link #subtitle}.
     *
     * @param subtitle {@link #subtitle}.
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
