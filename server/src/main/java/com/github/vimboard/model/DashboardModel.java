package com.github.vimboard.model;

import com.github.vimboard.domain.Board;
import com.github.vimboard.domain.Noticeboard;

import java.util.List;

public class DashboardModel {

    private List<Board> boards;

    private List<Noticeboard> noticeboard;

    private long unreadPms = 0;

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

    public long getUnreadPms() {
        return unreadPms;
    }

    public DashboardModel setUnreadPms(long unreadPms) {
        this.unreadPms = unreadPms;
        return this;
    }
}
