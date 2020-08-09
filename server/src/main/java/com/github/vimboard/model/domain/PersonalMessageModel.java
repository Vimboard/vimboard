package com.github.vimboard.model.domain;

public class PersonalMessageModel {

    // TODO
    private long id;

    // TODO
    private long waiting;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public long getId() {
        return id;
    }

    public PersonalMessageModel setId(long id) {
        this.id = id;
        return this;
    }

    public long getWaiting() {
        return waiting;
    }

    public PersonalMessageModel setWaiting(long waiting) {
        this.waiting = waiting;
        return this;
    }
}
