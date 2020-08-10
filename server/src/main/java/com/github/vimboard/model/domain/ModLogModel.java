package com.github.vimboard.model.domain;

import java.util.Date;

public class ModLogModel {

    private int mod;
    private String ip;
    private String board;
    private Date time;
    private String last;
    private String text;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------


    public int getMod() {
        return mod;
    }

    public ModLogModel setMod(int mod) {
        this.mod = mod;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public ModLogModel setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getBoard() {
        return board;
    }

    public ModLogModel setBoard(String board) {
        this.board = board;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public ModLogModel setTime(Date time) {
        this.time = time;
        return this;
    }

    public String getLast() {
        return last;
    }

    public ModLogModel setLast(String last) {
        this.last = last;
        return this;
    }

    public String getText() {
        return text;
    }

    public ModLogModel setText(String text) {
        this.text = text;
        return this;
    }
}
