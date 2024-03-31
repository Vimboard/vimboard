package com.github.vimboard.model;

/**
 * TODO
 */
public class ReleaseModel {

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
    // Getters and builder setters
    //------------------------------------------------------------------------

    public int getMassive() {
        return massive;
    }

    public ReleaseModel setMassive(int massive) {
        this.massive = massive;
        return this;
    }

    public int getMajor() {
        return major;
    }

    public ReleaseModel setMajor(int major) {
        this.major = major;
        return this;
    }

    public int getMinor() {
        return minor;
    }

    public ReleaseModel setMinor(int minor) {
        this.minor = minor;
        return this;
    }
}
