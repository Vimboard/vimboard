package com.github.vimboard.model;

import com.github.vimboard.domain.Board;

import java.util.List;

public class DashboardModel {

    private List<Board> boards;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public List<Board> getBoards() {
        return boards;
    }

    public DashboardModel setBoards(List<Board> boards) {
        this.boards = boards;
        return this;
    }
}
