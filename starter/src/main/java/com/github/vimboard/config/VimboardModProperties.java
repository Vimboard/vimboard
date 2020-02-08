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
     * View the report queue.
     */
    private Group reports;

    /**
     * View list of bans.
     */
    private Group viewBanlist;

    /**
     * Create a new board.
     */
    private Group newboard;

    /**
     * Manage existing boards (change title, etc).
     */
    private Group manageboards;

    /**
     * List/manage users.
     */
    private Group manageusers;

    /**
     * Change user's own password.
     */
    private Group changePassword;

    /**
     * Read the moderator noticeboard.
     */
    //$config['mod']['noticeboard'] = JANITOR;
    private Group noticeboard;

    /**
     * Execute un-filtered SQL queries on the database (?/debug/sql).
     */
    private Group debugSql;

    /**
     * Edit the current configuration (via web interface).
     */
    private Group editConfig;

    /**
     * View ban appeals.
     */
    private Group viewBanAppeals;

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

    public Group getReports() {
        return reports;
    }

    public VimboardModProperties setReports(Group reports) {
        this.reports = reports;
        return this;
    }

    public Group getViewBanlist() {
        return viewBanlist;
    }

    public VimboardModProperties setViewBanlist(Group viewBanlist) {
        this.viewBanlist = viewBanlist;
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

    public Group getManageusers() {
        return manageusers;
    }

    public VimboardModProperties setManageusers(Group manageusers) {
        this.manageusers = manageusers;
        return this;
    }

    public Group getChangePassword() {
        return changePassword;
    }

    public VimboardModProperties setChangePassword(Group changePassword) {
        this.changePassword = changePassword;
        return this;
    }

    public Group getNoticeboard() {
        return noticeboard;
    }

    public VimboardModProperties setNoticeboard(Group noticeboard) {
        this.noticeboard = noticeboard;
        return this;
    }

    public Group getDebugSql() {
        return debugSql;
    }

    public VimboardModProperties setDebugSql(Group debugSql) {
        this.debugSql = debugSql;
        return this;
    }

    public Group getEditConfig() {
        return editConfig;
    }

    public VimboardModProperties setEditConfig(Group editConfig) {
        this.editConfig = editConfig;
        return this;
    }

    public Group getViewBanAppeals() {
        return viewBanAppeals;
    }

    public VimboardModProperties setViewBanAppeals(Group viewBanAppeals) {
        this.viewBanAppeals = viewBanAppeals;
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
