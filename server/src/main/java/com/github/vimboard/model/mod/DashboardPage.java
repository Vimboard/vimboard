package com.github.vimboard.model.mod;

import com.github.vimboard.domain.Board;
import com.github.vimboard.domain.Noticeboard;
import com.github.vimboard.model.domain.ReleaseModel;

import java.util.List;

public class DashboardPage {

    private List<Board> boards;

    private String logoutToken = "";

    private ReleaseModel newerRelease;

    private List<Noticeboard> noticeboard;

    private long unreadPms = 0L;

    private long reports = 0L;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public List<Board> getBoards() {
        return boards;
    }

    public DashboardPage setBoards(List<Board> boards) {
        this.boards = boards;
        return this;
    }

    public String getLogoutToken() {
        return logoutToken;
    }

    public DashboardPage setLogoutToken(String logoutToken) {
        this.logoutToken = logoutToken;
        return this;
    }

    public ReleaseModel getNewerRelease() {
        return newerRelease;
    }

    public DashboardPage setNewerRelease(ReleaseModel newerRelease) {
        this.newerRelease = newerRelease;
        return this;
    }

    public List<Noticeboard> getNoticeboard() {
        return noticeboard;
    }

    public DashboardPage setNoticeboard(List<Noticeboard> noticeboard) {
        this.noticeboard = noticeboard;
        return this;
    }

    public long getReports() {
        return reports;
    }

    public DashboardPage setReports(long reports) {
        this.reports = reports;
        return this;
    }

    public long getUnreadPms() {
        return unreadPms;
    }

    public DashboardPage setUnreadPms(long unreadPms) {
        this.unreadPms = unreadPms;
        return this;
    }
}
