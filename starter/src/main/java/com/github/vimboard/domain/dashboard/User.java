package com.github.vimboard.domain.dashboard;

import com.github.vimboard.domain.Mod;

import java.util.Date;

public class User extends Mod {

    private Date last;
    private String action;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public Date getLast() {
        return last;
    }

    public User setLast(Date last) {
        this.last = last;
        return this;
    }

    public String getAction() {
        return action;
    }

    public User setAction(String action) {
        this.action = action;
        return this;
    }
}
