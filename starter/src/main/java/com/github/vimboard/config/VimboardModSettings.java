package com.github.vimboard.config;

import com.github.vimboard.domain.Group;

public class VimboardModSettings {

    // Mod settings

    private Long noticeboardDashboard;

    // Mod permissions

    private Group editPages;
    private Group manageboards;
    private Group newboard;
    private Group noticeboard;

    //------------------------------------------------------------------------
    // Getters and setters (setters must return void)
    //------------------------------------------------------------------------

    public Long getNoticeboardDashboard() {
        return noticeboardDashboard;
    }

    public void setNoticeboardDashboard(Long noticeboardDashboard) {
        this.noticeboardDashboard = noticeboardDashboard;
    }

    public Group getEditPages() {
        return editPages;
    }

    public void setEditPages(Group editPages) {
        this.editPages = editPages;
    }

    public Group getManageboards() {
        return manageboards;
    }

    public void setManageboards(Group manageboards) {
        this.manageboards = manageboards;
    }

    public Group getNewboard() {
        return newboard;
    }

    public void setNewboard(Group newboard) {
        this.newboard = newboard;
    }

    public Group getNoticeboard() {
        return noticeboard;
    }

    public void setNoticeboard(Group noticeboard) {
        this.noticeboard = noticeboard;
    }
}
