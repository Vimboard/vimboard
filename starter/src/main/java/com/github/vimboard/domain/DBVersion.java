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

    /**
     * Getter for {@link #schemaVersion}.
     *
     * @return {@link #schemaVersion}.
     */
    public String getSchemaVersion() {
        return schemaVersion;
    }

    /**
     * Setter for {@link #schemaVersion}.
     *
     * @param schemaVersion {@link #schemaVersion}.
     */
    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    /**
     * Getter for {@link #serverVersion}.
     *
     * @return {@link #serverVersion}.
     */
    public String getServerVersion() {
        return serverVersion;
    }

    /**
     * Setter for {@link #serverVersion}.
     *
     * @param serverVersion {@link #serverVersion}.
     */
    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }
}
