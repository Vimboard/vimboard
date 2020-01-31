package com.github.vimboard.config;

import com.github.vimboard.domain.Group;

public class VimboardModSettings {

    private Group editPages;
    private Group manageboards;
    private Group newboard;

    //------------------------------------------------------------------------
    // Getters and setters (setters must return void)
    //------------------------------------------------------------------------

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
}
