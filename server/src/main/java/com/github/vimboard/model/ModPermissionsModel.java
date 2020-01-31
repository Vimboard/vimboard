package com.github.vimboard.model;

public class ModPermissionsModel {

    private boolean editPages = false;
    private boolean manageboards = false;
    private boolean newboard = false;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

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

    public boolean isNewboard() {
        return newboard;
    }

    public ModPermissionsModel setNewboard(boolean newboard) {
        this.newboard = newboard;
        return this;
    }
}
