package com.github.vimboard.domain;

import java.util.Date;

/**
 * Moderator noticeboard.
 */
public class Noticeboard {

    private long id;
    private int mod;
    private String username;
    private Date time;
    private String subject;
    private String body;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public long getId() {
        return id;
    }

    public Noticeboard setId(long id) {
        this.id = id;
        return this;
    }

    public int getMod() {
        return mod;
    }

    public Noticeboard setMod(int mod) {
        this.mod = mod;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Noticeboard setUsername(String username) {
        this.username = username;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Noticeboard setTime(Date time) {
        this.time = time;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Noticeboard setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Noticeboard setBody(String body) {
        this.body = body;
        return this;
    }
}
