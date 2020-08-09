package com.github.vimboard.model.domain;

public class ModModel {

    /**
     * TODO
     */
    private int id;

    /**
     * TODO
     */
    private ModPermissionsModel hasPermission = new ModPermissionsModel();

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public ModModel setId(int id) {
        this.id = id;
        return this;
    }

    public ModPermissionsModel getHasPermission() {
        return hasPermission;
    }

    public ModModel setHasPermission(ModPermissionsModel hasPermission) {
        this.hasPermission = hasPermission;
        return this;
    }
}
