package com.github.vimboard.config;

import com.github.vimboard.domain.Group;

public class VimboardModProperties {

    //------------------------------------------------------------------------
    // Mod settings
    //------------------------------------------------------------------------

    /**
     * Number of entries to summarize and display on the dashboard.
     */
    //$config['mod']['noticeboard_dashboard'] = 5;
    private Long noticeboardDashboard;

    //------------------------------------------------------------------------
    // Mod permissions
    //------------------------------------------------------------------------

    // Post Controls ---------------------------------------------------------

    // Administration --------------------------------------------------------

    /**
     * Create a new board.
     */
    private Group newboard;

    /**
     * Manage existing boards (change title, etc).
     */
    private Group manageboards;

    /**
     * Read the moderator noticeboard.
     */
    //$config['mod']['noticeboard'] = JANITOR;
    private Group noticeboard;

    /**
     * Create pages.
     */
    private Group editPages;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public Long getNoticeboardDashboard() {
        return noticeboardDashboard;
    }

    public VimboardModProperties setNoticeboardDashboard(Long noticeboardDashboard) {
        this.noticeboardDashboard = noticeboardDashboard;
        return this;
    }

    public Group getNewboard() {
        return newboard;
    }

    public VimboardModProperties setNewboard(Group newboard) {
        this.newboard = newboard;
        return this;
    }

    public Group getManageboards() {
        return manageboards;
    }

    public VimboardModProperties setManageboards(Group manageboards) {
        this.manageboards = manageboards;
        return this;
    }

    public Group getNoticeboard() {
        return noticeboard;
    }

    public VimboardModProperties setNoticeboard(Group noticeboard) {
        this.noticeboard = noticeboard;
        return this;
    }

    public Group getEditPages() {
        return editPages;
    }

    public VimboardModProperties setEditPages(Group editPages) {
        this.editPages = editPages;
        return this;
    }
}
