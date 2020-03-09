package com.github.vimboard.config.properties;

import com.github.vimboard.domain.Group;

import java.util.Map;

public class VimboardModProperties {

    //------------------------------------------------------------------------
    // Mod settings
    //------------------------------------------------------------------------

    /**
     * Number of entries to summarize and display on the dashboard.
     */
    private Long noticeboardDashboard;

    //------------------------------------------------------------------------
    // Mod permissions
    //------------------------------------------------------------------------

    // Post Controls ---------------------------------------------------------

    /**
     * View IP addresses.
     */
    private Group showIp;

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
     * View IP address notes.
     */
    private Group viewNotes;

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
     * Promote/demote users.
     */
    private Group promoteusers;

    /**
     * Edit any users' login information.
     */
    private Group editusers;

    /**
     * Create a user.
     */
    private Group createusers;

    /**
     * Change user's own password.
     */
    private Group changePassword;

    /**
     * View the moderation log.
     */
    private Group modlog;

    /**
     * Search through posts, IP address notes and bans.
     */
    private Group search;

    /**
     * Read the moderator noticeboard.
     */
    private Group noticeboard;

    /**
     * Manage and install themes for homepage.
     */
    private Group themes;

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
     * View the recent posts page.
     */
    private Group recent;

    /**
     * Create pages.
     */
    private Group editPages;

    //------------------------------------------------------------------------
    // Other/uncategorized
    //------------------------------------------------------------------------

    /**
     * Add links to dashboard (will all be in a new "Other" category).
     */
    private Map<String, String> dashboardLinks;

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

    public Group getShowIp() {
        return showIp;
    }

    public VimboardModProperties setShowIp(Group showIp) {
        this.showIp = showIp;
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

    public Group getViewNotes() {
        return viewNotes;
    }

    public VimboardModProperties setViewNotes(Group viewNotes) {
        this.viewNotes = viewNotes;
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

    public Group getPromoteusers() {
        return promoteusers;
    }

    public VimboardModProperties setPromoteusers(Group promoteusers) {
        this.promoteusers = promoteusers;
        return this;
    }

    public Group getEditusers() {
        return editusers;
    }

    public VimboardModProperties setEditusers(Group editusers) {
        this.editusers = editusers;
        return this;
    }

    public Group getCreateusers() {
        return createusers;
    }

    public VimboardModProperties setCreateusers(Group createusers) {
        this.createusers = createusers;
        return this;
    }

    public Group getChangePassword() {
        return changePassword;
    }

    public VimboardModProperties setChangePassword(Group changePassword) {
        this.changePassword = changePassword;
        return this;
    }

    public Group getModlog() {
        return modlog;
    }

    public VimboardModProperties setModlog(Group modlog) {
        this.modlog = modlog;
        return this;
    }

    public Group getSearch() {
        return search;
    }

    public VimboardModProperties setSearch(Group search) {
        this.search = search;
        return this;
    }

    public Group getNoticeboard() {
        return noticeboard;
    }

    public VimboardModProperties setNoticeboard(Group noticeboard) {
        this.noticeboard = noticeboard;
        return this;
    }

    public Group getThemes() {
        return themes;
    }

    public VimboardModProperties setThemes(Group themes) {
        this.themes = themes;
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

    public Group getRecent() {
        return recent;
    }

    public VimboardModProperties setRecent(Group recent) {
        this.recent = recent;
        return this;
    }

    public Group getEditPages() {
        return editPages;
    }

    public VimboardModProperties setEditPages(Group editPages) {
        this.editPages = editPages;
        return this;
    }

    public Map<String, String> getDashboardLinks() {
        return dashboardLinks;
    }

    public VimboardModProperties setDashboardLinks(Map<String, String> dashboardLinks) {
        this.dashboardLinks = dashboardLinks;
        return this;
    }
}
