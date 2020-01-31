package com.github.vimboard.model;

public class ModModel {

    /**
     * TODO
     */
    private ModPermissionsModel hasPermission = new ModPermissionsModel();

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public ModPermissionsModel getHasPermission() {
        return hasPermission;
    }

    public ModModel setHasPermission(ModPermissionsModel hasPermission) {
        this.hasPermission = hasPermission;
        return this;
    }
}
