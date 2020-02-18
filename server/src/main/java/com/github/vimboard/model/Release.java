package com.github.vimboard.model;

/**
 * TODO
 */
public class Release {

    /**
     * TODO
     */
    private int massive;

    /**
     * TODO
     */
    private int major;

    /**
     * TODO
     */
    private int minor;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public int getMassive() {
        return massive;
    }

    public Release setMassive(int massive) {
        this.massive = massive;
        return this;
    }

    public int getMajor() {
        return major;
    }

    public Release setMajor(int major) {
        this.major = major;
        return this;
    }

    public int getMinor() {
        return minor;
    }

    public Release setMinor(int minor) {
        this.minor = minor;
        return this;
    }
}
