package com.github.vimboard.model.domain;

import com.github.vimboard.domain.Pms;

import java.util.Date;

public class PmsModel {

    private long id;
    private long sender;
    private long to;
    private String message;
    private Date time;
    private boolean unread;
    private String username;
    private String snippet;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public long getId() {
        return id;
    }

    public PmsModel setId(long id) {
        this.id = id;
        return this;
    }

    public long getSender() {
        return sender;
    }

    public PmsModel setSender(long sender) {
        this.sender = sender;
        return this;
    }

    public long getTo() {
        return to;
    }

    public PmsModel setTo(long to) {
        this.to = to;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public PmsModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public PmsModel setTime(Date time) {
        this.time = time;
        return this;
    }

    public boolean isUnread() {
        return unread;
    }

    public PmsModel setUnread(boolean unread) {
        this.unread = unread;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public PmsModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getSnippet() {
        return snippet;
    }

    public PmsModel setSnippet(String snippet) {
        this.snippet = snippet;
        return this;
    }
}
