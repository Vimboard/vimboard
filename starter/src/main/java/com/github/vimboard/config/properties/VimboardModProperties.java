package com.github.vimboard.config.properties;

import com.github.vimboard.domain.Group;

import java.util.Map;

/**
 * Mod settings mapping to Spring Boot application properties.
 */
public class VimboardModProperties {

    //------------------------------------------------------------------------
    // Mod settings
    //------------------------------------------------------------------------

    /**
     * Number of entries to summarize and display on the dashboard.
     */
    private Integer noticeboardDashboard;

    /**
     * PM snippet (for ?/inbox) length in characters.
     */
    private Integer snippetLength;

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
     * Change user's own password.
     */
    private Group changePassword;

    /**
     * Delete a user.
     */
    private Group deleteusers;

    /**
     * Create a user.
     */
    private Group createusers;

    /**
     * View the moderation log.
     */
    private Group modlog;

    /**
     * Create a PM (viewing mod usernames).
     */
    private Group createPm;

    /**
     * Read any PM, sent to or from anybody.
     */
    private Group masterPm;

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
    // Getters and builder setters
    //------------------------------------------------------------------------

    /**
     * Getter for {@link #noticeboardDashboard}.
     *
     * @return field value.
     */
    public Integer getNoticeboardDashboard() {
        return noticeboardDashboard;
    }

    /**
     * Setter for {@link #noticeboardDashboard}.
     *
     * @param noticeboardDashboard new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setNoticeboardDashboard(Integer noticeboardDashboard) {
        this.noticeboardDashboard = noticeboardDashboard;
        return this;
    }

    /**
     * Getter for {@link #snippetLength}.
     *
     * @return field value.
     */
    public Integer getSnippetLength() {
        return snippetLength;
    }

    /**
     * Setter for {@link #snippetLength}.
     *
     * @param snippetLength new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setSnippetLength(Integer snippetLength) {
        this.snippetLength = snippetLength;
        return this;
    }

    /**
     * Getter for {@link #showIp}.
     *
     * @return field value.
     */
    public Group getShowIp() {
        return showIp;
    }

    /**
     * Setter for {@link #showIp}.
     *
     * @param showIp new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setShowIp(Group showIp) {
        this.showIp = showIp;
        return this;
    }

    /**
     * Getter for {@link #reports}.
     *
     * @return field value.
     */
    public Group getReports() {
        return reports;
    }

    /**
     * Setter for {@link #reports}.
     *
     * @param reports new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setReports(Group reports) {
        this.reports = reports;
        return this;
    }

    /**
     * Getter for {@link #viewBanlist}.
     *
     * @return field value.
     */
    public Group getViewBanlist() {
        return viewBanlist;
    }

    /**
     * Setter for {@link #viewBanlist}.
     *
     * @param viewBanlist new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setViewBanlist(Group viewBanlist) {
        this.viewBanlist = viewBanlist;
        return this;
    }

    /**
     * Getter for {@link #viewNotes}.
     *
     * @return field value.
     */
    public Group getViewNotes() {
        return viewNotes;
    }

    /**
     * Setter for {@link #viewNotes}.
     *
     * @param viewNotes new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setViewNotes(Group viewNotes) {
        this.viewNotes = viewNotes;
        return this;
    }

    /**
     * Getter for {@link #newboard}.
     *
     * @return field value.
     */
    public Group getNewboard() {
        return newboard;
    }

    /**
     * Setter for {@link #newboard}.
     *
     * @param newboard new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setNewboard(Group newboard) {
        this.newboard = newboard;
        return this;
    }

    /**
     * Getter for {@link #manageboards}.
     *
     * @return field value.
     */
    public Group getManageboards() {
        return manageboards;
    }

    /**
     * Setter for {@link #manageboards}.
     *
     * @param manageboards new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setManageboards(Group manageboards) {
        this.manageboards = manageboards;
        return this;
    }

    /**
     * Getter for {@link #manageusers}.
     *
     * @return field value.
     */
    public Group getManageusers() {
        return manageusers;
    }

    /**
     * Setter for {@link #manageusers}.
     *
     * @param manageusers new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setManageusers(Group manageusers) {
        this.manageusers = manageusers;
        return this;
    }

    /**
     * Getter for {@link #promoteusers}.
     *
     * @return field value.
     */
    public Group getPromoteusers() {
        return promoteusers;
    }

    /**
     * Setter for {@link #promoteusers}.
     *
     * @param promoteusers new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setPromoteusers(Group promoteusers) {
        this.promoteusers = promoteusers;
        return this;
    }

    /**
     * Getter for {@link #editusers}.
     *
     * @return field value.
     */
    public Group getEditusers() {
        return editusers;
    }

    /**
     * Setter for {@link #editusers}.
     *
     * @param editusers new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setEditusers(Group editusers) {
        this.editusers = editusers;
        return this;
    }

    /**
     * Getter for {@link #changePassword}.
     *
     * @return field value.
     */
    public Group getChangePassword() {
        return changePassword;
    }

    /**
     * Setter for {@link #changePassword}.
     *
     * @param changePassword new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setChangePassword(Group changePassword) {
        this.changePassword = changePassword;
        return this;
    }

    /**
     * Getter for {@link #deleteusers}.
     *
     * @return field value.
     */
    public Group getDeleteusers() {
        return deleteusers;
    }

    /**
     * Setter for {@link #deleteusers}.
     *
     * @param deleteusers new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setDeleteusers(Group deleteusers) {
        this.deleteusers = deleteusers;
        return this;
    }

    /**
     * Getter for {@link #createusers}.
     *
     * @return field value.
     */
    public Group getCreateusers() {
        return createusers;
    }

    /**
     * Setter for {@link #createusers}.
     *
     * @param createusers new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setCreateusers(Group createusers) {
        this.createusers = createusers;
        return this;
    }

    /**
     * Getter for {@link #modlog}.
     *
     * @return field value.
     */
    public Group getModlog() {
        return modlog;
    }

    /**
     * Setter for {@link #modlog}.
     *
     * @param modlog new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setModlog(Group modlog) {
        this.modlog = modlog;
        return this;
    }

    /**
     * Getter for {@link #createPm}.
     *
     * @return field value.
     */
    public Group getCreatePm() {
        return createPm;
    }

    /**
     * Setter for {@link #createPm}.
     *
     * @param createPm new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setCreatePm(Group createPm) {
        this.createPm = createPm;
        return this;
    }

    /**
     * Getter for {@link #masterPm}.
     *
     * @return field value.
     */
    public Group getMasterPm() {
        return masterPm;
    }

    /**
     * Setter for {@link #masterPm}.
     *
     * @param masterPm new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setMasterPm(Group masterPm) {
        this.masterPm = masterPm;
        return this;
    }

    /**
     * Getter for {@link #search}.
     *
     * @return field value.
     */
    public Group getSearch() {
        return search;
    }

    /**
     * Setter for {@link #search}.
     *
     * @param search new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setSearch(Group search) {
        this.search = search;
        return this;
    }

    /**
     * Getter for {@link #noticeboard}.
     *
     * @return field value.
     */
    public Group getNoticeboard() {
        return noticeboard;
    }

    /**
     * Setter for {@link #noticeboard}.
     *
     * @param noticeboard new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setNoticeboard(Group noticeboard) {
        this.noticeboard = noticeboard;
        return this;
    }

    /**
     * Getter for {@link #themes}.
     *
     * @return field value.
     */
    public Group getThemes() {
        return themes;
    }

    /**
     * Setter for {@link #themes}.
     *
     * @param themes new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setThemes(Group themes) {
        this.themes = themes;
        return this;
    }

    /**
     * Getter for {@link #debugSql}.
     *
     * @return field value.
     */
    public Group getDebugSql() {
        return debugSql;
    }

    /**
     * Setter for {@link #debugSql}.
     *
     * @param debugSql new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setDebugSql(Group debugSql) {
        this.debugSql = debugSql;
        return this;
    }

    /**
     * Getter for {@link #editConfig}.
     *
     * @return field value.
     */
    public Group getEditConfig() {
        return editConfig;
    }

    /**
     * Setter for {@link #editConfig}.
     *
     * @param editConfig new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setEditConfig(Group editConfig) {
        this.editConfig = editConfig;
        return this;
    }

    /**
     * Getter for {@link #viewBanAppeals}.
     *
     * @return field value.
     */
    public Group getViewBanAppeals() {
        return viewBanAppeals;
    }

    /**
     * Setter for {@link #viewBanAppeals}.
     *
     * @param viewBanAppeals new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setViewBanAppeals(Group viewBanAppeals) {
        this.viewBanAppeals = viewBanAppeals;
        return this;
    }

    /**
     * Getter for {@link #recent}.
     *
     * @return field value.
     */
    public Group getRecent() {
        return recent;
    }

    /**
     * Setter for {@link #recent}.
     *
     * @param recent new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setRecent(Group recent) {
        this.recent = recent;
        return this;
    }

    /**
     * Getter for {@link #editPages}.
     *
     * @return field value.
     */
    public Group getEditPages() {
        return editPages;
    }

    /**
     * Setter for {@link #editPages}.
     *
     * @param editPages new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setEditPages(Group editPages) {
        this.editPages = editPages;
        return this;
    }

    /**
     * Getter for {@link #dashboardLinks}.
     *
     * @return field value.
     */
    public Map<String, String> getDashboardLinks() {
        return dashboardLinks;
    }

    /**
     * Setter for {@link #dashboardLinks}.
     *
     * @param dashboardLinks new field value.
     * @return {@code this}.
     */
    public VimboardModProperties setDashboardLinks(Map<String, String> dashboardLinks) {
        this.dashboardLinks = dashboardLinks;
        return this;
    }
}
