package com.github.vimboard.model;

import java.util.Map;

public class ModModel {

    /**
     * TODO
     */
    private Map<String, Boolean> hasPermission;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public Map<String, Boolean> getHasPermission() {
        return hasPermission;
    }

    public ModModel setHasPermission(Map<String, Boolean> hasPermission) {
        this.hasPermission = hasPermission;
        return this;
    }
}
