package com.github.vimboard.domain;

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

    /**
     * Getter for {@link #id}.
     *
     * @return {@link #id}.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for {@link #id}.
     *
     * @param id {@link #id}.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for {@link #username}.
     *
     * @return {@link #username}.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for {@link #username}.
     *
     * @param username {@link #username}.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for {@link #password}.
     *
     * @return {@link #password}.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for {@link #password}.
     *
     * @param password {@link #password}.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
