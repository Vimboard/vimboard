package com.github.vimboard.model;

import com.github.vimboard.domain.Board;

public class BoardModel extends Board {

    private String dir;

    private String url;

    public BoardModel(Board board) {
        setId(board.getId());
        setUri(board.getUri());
        setTitle(board.getTitle());
        setSubtitle(board.getSubtitle());
    }

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    // TODO: for older versions
    public String getName() {
        return getTitle();
    }

    // TODO: for older versions
    public BoardModel setName(String name) {
        setTitle(name);
        return this;
    }

    public String getDir() {
        return dir;
    }

    public BoardModel setDir(String dir) {
        this.dir = dir;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public BoardModel setUrl(String url) {
        this.url = url;
        return this;
    }
}
