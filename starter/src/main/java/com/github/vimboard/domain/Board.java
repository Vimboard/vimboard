package com.github.vimboard.domain;

public class Board {

    /**
     * The board identifier.
     */
    private int id;

    /**
     * A board uri wihtout '/' at
     */
    private String uri;
    private String title;
    private String subtitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
