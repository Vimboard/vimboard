package com.github.vimboard.config.settings;

import com.github.vimboard.domain.Group;

import java.util.Map;

/**
 * Mod settings. TODO
 */
public class VimboardModSettings {

    // Mod settings

    private Integer noticeboardDashboard;
    private Integer snippetLength;

    // Mod permissions

    // Post Controls
    private Group showIp;

    // Administration
    private Group reports;
    private Group viewBanlist;
    private Group viewNotes;
    private Group newboard;
    private Group manageboards;
    private Group manageusers;
    private Group masterPm;
    private Group promoteusers;
    private Group editusers;
    private Group changePassword;
    private Group deleteusers;
    private Group createusers;
    private Group modlog;
    private Group createPm;
    private Group search;
    private Group noticeboard;
    private Group themes;
    private Group debugSql;
    private Group editConfig;
    private Group viewBanAppeals;
    private Group recent;
    private Group editPages;

    // Other/uncategorized

    private Map<String, String> dashboardLinks;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public Integer getNoticeboardDashboard() {
        return noticeboardDashboard;
    }

    public void setNoticeboardDashboard(Integer noticeboardDashboard) {
        this.noticeboardDashboard = noticeboardDashboard;
    }

    public Integer getSnippetLength() {
        return snippetLength;
    }

    public void setSnippetLength(Integer snippetLength) {
        this.snippetLength = snippetLength;
    }

    public Group getShowIp() {
        return showIp;
    }

    public void setShowIp(Group showIp) {
        this.showIp = showIp;
    }

    public Group getReports() {
        return reports;
    }

    public void setReports(Group reports) {
        this.reports = reports;
    }

    public Group getViewBanlist() {
        return viewBanlist;
    }

    public void setViewBanlist(Group viewBanlist) {
        this.viewBanlist = viewBanlist;
    }

    public Group getViewNotes() {
        return viewNotes;
    }

    public void setViewNotes(Group viewNotes) {
        this.viewNotes = viewNotes;
    }

    public Group getNewboard() {
        return newboard;
    }

    public void setNewboard(Group newboard) {
        this.newboard = newboard;
    }

    public Group getManageboards() {
        return manageboards;
    }

    public void setManageboards(Group manageboards) {
        this.manageboards = manageboards;
    }

    public Group getManageusers() {
        return manageusers;
    }

    public void setManageusers(Group manageusers) {
        this.manageusers = manageusers;
    }

    public Group getMasterPm() {
        return masterPm;
    }

    public void setMasterPm(Group masterPm) {
        this.masterPm = masterPm;
    }

    public Group getPromoteusers() {
        return promoteusers;
    }

    public void setPromoteusers(Group promoteusers) {
        this.promoteusers = promoteusers;
    }

    public Group getEditusers() {
        return editusers;
    }

    public void setEditusers(Group editusers) {
        this.editusers = editusers;
    }

    public Group getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(Group changePassword) {
        this.changePassword = changePassword;
    }

    public Group getDeleteusers() {
        return deleteusers;
    }

    public void setDeleteusers(Group deleteusers) {
        this.deleteusers = deleteusers;
    }

    public Group getCreateusers() {
        return createusers;
    }

    public void setCreateusers(Group createusers) {
        this.createusers = createusers;
    }

    public Group getModlog() {
        return modlog;
    }

    public void setModlog(Group modlog) {
        this.modlog = modlog;
    }

    public Group getCreatePm() {
        return createPm;
    }

    public void setCreatePm(Group createPm) {
        this.createPm = createPm;
    }

    public Group getSearch() {
        return search;
    }

    public void setSearch(Group search) {
        this.search = search;
    }

    public Group getNoticeboard() {
        return noticeboard;
    }

    public void setNoticeboard(Group noticeboard) {
        this.noticeboard = noticeboard;
    }

    public Group getThemes() {
        return themes;
    }

    public void setThemes(Group themes) {
        this.themes = themes;
    }

    public Group getDebugSql() {
        return debugSql;
    }

    public void setDebugSql(Group debugSql) {
        this.debugSql = debugSql;
    }

    public Group getEditConfig() {
        return editConfig;
    }

    public void setEditConfig(Group editConfig) {
        this.editConfig = editConfig;
    }

    public Group getViewBanAppeals() {
        return viewBanAppeals;
    }

    public void setViewBanAppeals(Group viewBanAppeals) {
        this.viewBanAppeals = viewBanAppeals;
    }

    public Group getRecent() {
        return recent;
    }

    public void setRecent(Group recent) {
        this.recent = recent;
    }

    public Group getEditPages() {
        return editPages;
    }

    public void setEditPages(Group editPages) {
        this.editPages = editPages;
    }

    public Map<String, String> getDashboardLinks() {
        return dashboardLinks;
    }

    public void setDashboardLinks(Map<String, String> dashboardLinks) {
        this.dashboardLinks = dashboardLinks;
    }
}
