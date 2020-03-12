package com.github.vimboard.domain;

import java.util.Date;

public class ModLog {

    private int mod;
    private String ip;
    private String board;
    private Date time;
    private String text;

    //------------------------------------------------------------------------
    // UserDetails
    //------------------------------------------------------------------------

    public int getMod() {
        return mod;
    }

    public ModLog setMod(int mod) {
        this.mod = mod;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public ModLog setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getBoard() {
        return board;
    }

    public ModLog setBoard(String board) {
        this.board = board;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public ModLog setTime(Date time) {
        this.time = time;
        return this;
    }

    public String getText() {
        return text;
    }

    public ModLog setText(String text) {
        this.text = text;
        return this;
    }
}
