package com.github.vimboard.domain;

/**
 * TODO
 */
public class User {

    /**
     * TODO
     */
    private int id;

    /**
     * TODO
     */
    private String username;

    /**
     * TODO
     */
    private String password;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
