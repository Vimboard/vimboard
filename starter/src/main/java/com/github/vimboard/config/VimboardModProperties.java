package com.github.vimboard.config;

import com.github.vimboard.domain.Group;

public class VimboardModProperties {

    /**
     * Create pages.
     */
    private Group editPages;

    /**
     * Manage existing boards (change title, etc).
     */
    private Group manageboards;

    /**
     * Create a new board.
     */
    private Group newboard;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public Group getEditPages() {
        return editPages;
    }

    public VimboardModProperties setEditPages(Group editPages) {
        this.editPages = editPages;
        return this;
    }

    public Group getManageboards() {
        return manageboards;
    }

    public VimboardModProperties setManageboards(Group manageboards) {
        this.manageboards = manageboards;
        return this;
    }

    public Group getNewboard() {
        return newboard;
    }

    public VimboardModProperties setNewboard(Group newboard) {
        this.newboard = newboard;
        return this;
    }
}
