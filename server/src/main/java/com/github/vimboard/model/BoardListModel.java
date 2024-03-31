package com.github.vimboard.model;

/**
 * Board list bar model.
 */
public class BoardListModel {

    /**
     * String containing html for the top bar.
     */
    private String top;

    /**
     * String containing html for the bottom bar.
     */
    private String bottom;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public String getTop() {
        return top;
    }

    public BoardListModel setTop(String top) {
        this.top = top;
        return this;
    }

    public String getBottom() {
        return bottom;
    }

    public BoardListModel setBottom(String bottom) {
        this.bottom = bottom;
        return this;
    }
}
