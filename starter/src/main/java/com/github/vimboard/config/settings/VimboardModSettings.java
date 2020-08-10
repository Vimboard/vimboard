package com.github.vimboard.config.settings;

import com.github.vimboard.config.properties.VimboardModProperties;
import com.github.vimboard.domain.Group;

import java.util.Map;

/**
 * Mod settings.
 */
public class VimboardModSettings {

    // Mod settings

    /** {@link VimboardModProperties#getNoticeboardDashboard()} */
    private Integer noticeboardDashboard;
    /** {@link VimboardModProperties#getSnippetLength()} */
    private Integer snippetLength;

    // Mod permissions

    // Post Controls

    /** {@link VimboardModProperties#getShowIp()} */
    private Group showIp;

    // Administration

    /** {@link VimboardModProperties#getReports()} */
    private Group reports;
    /** {@link VimboardModProperties#getViewBanlist()} */
    private Group viewBanlist;
    /** {@link VimboardModProperties#getViewNotes()} */
    private Group viewNotes;
    /** {@link VimboardModProperties#getNewboard()} */
    private Group newboard;
    /** {@link VimboardModProperties#getManageboards()} */
    private Group manageboards;
    /** {@link VimboardModProperties#getManageusers()} */
    private Group manageusers;
    /** {@link VimboardModProperties#getMasterPm()} */
    private Group masterPm;
    /** {@link VimboardModProperties#getPromoteusers()} */
    private Group promoteusers;
    /** {@link VimboardModProperties#getEditusers()} */
    private Group editusers;
    /** {@link VimboardModProperties#getChangePassword()} */
    private Group changePassword;
    /** {@link VimboardModProperties#getDeleteusers()} */
    private Group deleteusers;
    /** {@link VimboardModProperties#getCreateusers()} */
    private Group createusers;
    /** {@link VimboardModProperties#getModlog()} */
    private Group modlog;
    /** {@link VimboardModProperties#getCreatePm()} */
    private Group createPm;
    /** {@link VimboardModProperties#getSearch()} */
    private Group search;
    /** {@link VimboardModProperties#getNoticeboard()} */
    private Group noticeboard;
    /** {@link VimboardModProperties#getThemes()} */
    private Group themes;
    /** {@link VimboardModProperties#getDebugSql()} */
    private Group debugSql;
    /** {@link VimboardModProperties#getEditConfig()} */
    private Group editConfig;
    /** {@link VimboardModProperties#getViewBanAppeals()} */
    private Group viewBanAppeals;
    /** {@link VimboardModProperties#getRecent()} */
    private Group recent;
    /** {@link VimboardModProperties#getEditPages()} */
    private Group editPages;

    // Other/uncategorized

    /** {@link VimboardModProperties#getDashboardLinks()} */
    private Map<String, String> dashboardLinks;

    //------------------------------------------------------------------------
    // Getters and setters
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
     */
    public void setNoticeboardDashboard(Integer noticeboardDashboard) {
        this.noticeboardDashboard = noticeboardDashboard;
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
     */
    public void setSnippetLength(Integer snippetLength) {
        this.snippetLength = snippetLength;
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
     */
    public void setShowIp(Group showIp) {
        this.showIp = showIp;
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
     */
    public void setReports(Group reports) {
        this.reports = reports;
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
     */
    public void setViewBanlist(Group viewBanlist) {
        this.viewBanlist = viewBanlist;
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
     */
    public void setViewNotes(Group viewNotes) {
        this.viewNotes = viewNotes;
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
     */
    public void setNewboard(Group newboard) {
        this.newboard = newboard;
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
     */
    public void setManageboards(Group manageboards) {
        this.manageboards = manageboards;
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
     */
    public void setManageusers(Group manageusers) {
        this.manageusers = manageusers;
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
     */
    public void setMasterPm(Group masterPm) {
        this.masterPm = masterPm;
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
     */
    public void setPromoteusers(Group promoteusers) {
        this.promoteusers = promoteusers;
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
     */
    public void setEditusers(Group editusers) {
        this.editusers = editusers;
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
     */
    public void setChangePassword(Group changePassword) {
        this.changePassword = changePassword;
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
     */
    public void setDeleteusers(Group deleteusers) {
        this.deleteusers = deleteusers;
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
     */
    public void setCreateusers(Group createusers) {
        this.createusers = createusers;
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
     */
    public void setModlog(Group modlog) {
        this.modlog = modlog;
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
     */
    public void setCreatePm(Group createPm) {
        this.createPm = createPm;
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
     */
    public void setSearch(Group search) {
        this.search = search;
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
     */
    public void setNoticeboard(Group noticeboard) {
        this.noticeboard = noticeboard;
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
     */
    public void setThemes(Group themes) {
        this.themes = themes;
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
     */
    public void setDebugSql(Group debugSql) {
        this.debugSql = debugSql;
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
     */
    public void setEditConfig(Group editConfig) {
        this.editConfig = editConfig;
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
     */
    public void setViewBanAppeals(Group viewBanAppeals) {
        this.viewBanAppeals = viewBanAppeals;
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
     */
    public void setRecent(Group recent) {
        this.recent = recent;
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
     */
    public void setEditPages(Group editPages) {
        this.editPages = editPages;
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
     */
    public void setDashboardLinks(Map<String, String> dashboardLinks) {
        this.dashboardLinks = dashboardLinks;
    }
}
