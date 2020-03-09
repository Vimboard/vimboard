package com.github.vimboard.domain.dashboard;

import com.github.vimboard.domain.Mod;

import java.util.Date;

public class User extends Mod {

    private Date time;
    private String text;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public Date getTime() {
        return time;
    }

    public User setTime(Date time) {
        this.time = time;
        return this;
    }

    public String getText() {
        return text;
    }

    public User setText(String text) {
        this.text = text;
        return this;
    }
}
