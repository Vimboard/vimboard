package com.github.vimboard.domain;

import java.util.Date;

/**
 * TODO
 */
public class Report {

    private long id;
    private Date time;
    private String ip;
    private String board; // uri
    private long post;
    private String reason;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public long getId() {
        return id;
    }

    public Report setId(long id) {
        this.id = id;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Report setTime(Date time) {
        this.time = time;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Report setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getBoard() {
        return board;
    }

    public Report setBoard(String board) {
        this.board = board;
        return this;
    }

    public long getPost() {
        return post;
    }

    public Report setPost(long post) {
        this.post = post;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public Report setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
