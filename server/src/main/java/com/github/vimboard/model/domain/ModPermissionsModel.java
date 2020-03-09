package com.github.vimboard.model.domain;

public class ModPermissionsModel {

    private boolean changePassword = false;
    private boolean createusers = false;
    private boolean debugSql = false;
    private boolean editConfig = false;
    private boolean editPages = false;
    private boolean editusers = false;
    private boolean manageboards = false;
    private boolean manageusers = false;
    private boolean modlog = false;
    private boolean newboard = false;
    private boolean noticeboard = false;
    private boolean promoteusers = false;
    private boolean recent = false;
    private boolean reports = false;
    private boolean search = false;
    private boolean showIp = false;
    private boolean themes = false;
    private boolean viewBanAppeals = false;
    private boolean viewBanlist = false;
    private boolean viewNotes = false;

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

    public boolean isCreateusers() {
        return createusers;
    }

    public ModPermissionsModel setCreateusers(boolean createusers) {
        this.createusers = createusers;
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

    public boolean isEditusers() {
        return editusers;
    }

    public ModPermissionsModel setEditusers(boolean editusers) {
        this.editusers = editusers;
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

    public boolean isModlog() {
        return modlog;
    }

    public ModPermissionsModel setModlog(boolean modlog) {
        this.modlog = modlog;
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

    public boolean isPromoteusers() {
        return promoteusers;
    }

    public ModPermissionsModel setPromoteusers(boolean promoteusers) {
        this.promoteusers = promoteusers;
        return this;
    }

    public boolean isRecent() {
        return recent;
    }

    public ModPermissionsModel setRecent(boolean recent) {
        this.recent = recent;
        return this;
    }

    public boolean isReports() {
        return reports;
    }

    public ModPermissionsModel setReports(boolean reports) {
        this.reports = reports;
        return this;
    }

    public boolean isSearch() {
        return search;
    }

    public ModPermissionsModel setSearch(boolean search) {
        this.search = search;
        return this;
    }

    public boolean isShowIp() {
        return showIp;
    }

    public ModPermissionsModel setShowIp(boolean showIp) {
        this.showIp = showIp;
        return this;
    }

    public boolean isThemes() {
        return themes;
    }

    public ModPermissionsModel setThemes(boolean themes) {
        this.themes = themes;
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

    public boolean isViewNotes() {
        return viewNotes;
    }

    public ModPermissionsModel setViewNotes(boolean viewNotes) {
        this.viewNotes = viewNotes;
        return this;
    }
}
