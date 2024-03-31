package com.github.vimboard.model;

public class BoardModelFileboard extends BoardModel {

    private int threadCount;

    public BoardModelFileboard(BoardModel boardModel) {
        super(boardModel);
        setName(boardModel.getName());
        setDir(boardModel.getDir());
        setUrl(boardModel.getUrl());
    }

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public int getThreadCount() {
        return threadCount;
    }

    public BoardModelFileboard setThreadCount(int threadCount) {
        this.threadCount = threadCount;
        return this;
    }
}
