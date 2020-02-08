package com.github.vimboard.domain;

/**
 * TODO
 */
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

    public int getId() {
        return id;
    }

    public Board setId(int id) {
        this.id = id;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public Board setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Board setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Board setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }
}
