package com.github.vimboard.domain;

import java.util.Date;

public class Pms {

    private long id;
    private int sender;
    private int to;
    private String message;
    private Date time;
    private boolean unread;
    private String username;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public long getId() {
        return id;
    }

    public Pms setId(long id) {
        this.id = id;
        return this;
    }

    public int getSender() {
        return sender;
    }

    public Pms setSender(int sender) {
        this.sender = sender;
        return this;
    }

    public int getTo() {
        return to;
    }

    public Pms setTo(int to) {
        this.to = to;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Pms setMessage(String message) {
        this.message = message;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Pms setTime(Date time) {
        this.time = time;
        return this;
    }

    public boolean isUnread() {
        return unread;
    }

    public Pms setUnread(boolean unread) {
        this.unread = unread;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Pms setUsername(String username) {
        this.username = username;
        return this;
    }
}
