package com.github.vimboard.model.domain;

import com.github.vimboard.domain.Board;

public class BoardModel extends Board {

    // TODO older versions
    private String name;

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

    public String getName() {
        return name;
    }

    public BoardModel setName(String name) {
        this.name = name;
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
