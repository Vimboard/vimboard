package com.github.vimboard.model;

public class ModPermissionsModel {

    private boolean changePassword = false;
    private boolean debugSql = false;
    private boolean editConfig = false;
    private boolean editPages = false;
    private boolean manageboards = false;
    private boolean manageusers = false;
    private boolean newboard = false;
    private boolean noticeboard = false;
    private boolean reports = false;
    private boolean viewBanAppeals = false;
    private boolean viewBanlist = false;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public boolean isChangePassword() {
        return changePassword;
    }

    public ModPermissionsModel setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
        return this;
    }

    public boolean isDebugSql() {
        return debugSql;
    }

    public ModPermissionsModel setDebugSql(boolean debugSql) {
        this.debugSql = debugSql;
        return this;
    }

    public boolean isEditConfig() {
        return editConfig;
    }

    public ModPermissionsModel setEditConfig(boolean editConfig) {
        this.editConfig = editConfig;
        return this;
    }

    public boolean isEditPages() {
        return editPages;
    }

    public ModPermissionsModel setEditPages(boolean editPages) {
        this.editPages = editPages;
        return this;
    }

    public boolean isManageboards() {
        return manageboards;
    }

    public ModPermissionsModel setManageboards(boolean manageboards) {
        this.manageboards = manageboards;
        return this;
    }

    public boolean isManageusers() {
        return manageusers;
    }

    public ModPermissionsModel setManageusers(boolean manageusers) {
        this.manageusers = manageusers;
        return this;
    }

    public boolean isNewboard() {
        return newboard;
    }

    public ModPermissionsModel setNewboard(boolean newboard) {
        this.newboard = newboard;
        return this;
    }

    public boolean isNoticeboard() {
        return noticeboard;
    }

    public ModPermissionsModel setNoticeboard(boolean noticeboard) {
        this.noticeboard = noticeboard;
        return this;
    }

    public boolean isReports() {
        return reports;
    }

    public ModPermissionsModel setReports(boolean reports) {
        this.reports = reports;
        return this;
    }

    public boolean isViewBanAppeals() {
        return viewBanAppeals;
    }

    public ModPermissionsModel setViewBanAppeals(boolean viewBanAppeals) {
        this.viewBanAppeals = viewBanAppeals;
        return this;
    }

    public boolean isViewBanlist() {
        return viewBanlist;
    }

    public ModPermissionsModel setViewBanlist(boolean viewBanlist) {
        this.viewBanlist = viewBanlist;
        return this;
    }
}
