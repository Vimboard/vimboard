package com.github.vimboard.model;

import com.github.vimboard.domain.Board;
import com.github.vimboard.domain.Noticeboard;

import java.util.List;

public class DashboardModel {

    private List<Board> boards;

    private List<Noticeboard> noticeboard;

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

    public List<Noticeboard> getNoticeboard() {
        return noticeboard;
    }

    public DashboardModel setNoticeboard(List<Noticeboard> noticeboard) {
        this.noticeboard = noticeboard;
        return this;
    }
}
