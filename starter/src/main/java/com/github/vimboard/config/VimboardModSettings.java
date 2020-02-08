package com.github.vimboard.config;

import com.github.vimboard.domain.Group;

public class VimboardModSettings {

    // Mod settings

    private Long noticeboardDashboard;

    // Mod permissions

    private Group reports;
    private Group viewBanlist;
    private Group newboard;
    private Group manageboards;
    private Group manageusers;
    private Group changePassword;
    private Group noticeboard;
    private Group debugSql;
    private Group editConfig;
    private Group viewBanAppeals;
    private Group editPages;

    //------------------------------------------------------------------------
    // Getters and setters (setters must return void)
    //------------------------------------------------------------------------

    public Long getNoticeboardDashboard() {
        return noticeboardDashboard;
    }

    public void setNoticeboardDashboard(Long noticeboardDashboard) {
        this.noticeboardDashboard = noticeboardDashboard;
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

    public Group getNoticeboard() {
        return noticeboard;
    }

    public void setNoticeboard(Group noticeboard) {
        this.noticeboard = noticeboard;
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

    public Group getEditPages() {
        return editPages;
    }

    public void setEditPages(Group editPages) {
        this.editPages = editPages;
    }

    public Group getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(Group changePassword) {
        this.changePassword = changePassword;
    }
}
