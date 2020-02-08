package com.github.vimboard.domain;

/**
 * Database version info.
 */
public class DBVersion {

    /**
     * Vimboard database schema version.
     */
    private String schemaVersion;

    /**
     * Database server softvare version.
     */
    private String serverVersion;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public DBVersion setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
        return this;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public DBVersion setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
        return this;
    }
}
