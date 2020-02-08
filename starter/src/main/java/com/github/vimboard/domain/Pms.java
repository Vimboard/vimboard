package com.github.vimboard.domain;

import java.util.Date;

public class Pms {

    private long id;
    private long sender;
    private long to;
    private String message;
    private Date time;
    private boolean unread;

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

    public long getSender() {
        return sender;
    }

    public Pms setSender(long sender) {
        this.sender = sender;
        return this;
    }

    public long getTo() {
        return to;
    }

    public Pms setTo(long to) {
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
}
