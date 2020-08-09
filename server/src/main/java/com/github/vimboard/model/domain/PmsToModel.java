package com.github.vimboard.model.domain;

import com.github.vimboard.domain.PmsTo;

public class PmsToModel extends PmsTo {

    private String last;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public String getLast() {
        return last;
    }

    public PmsToModel setLast(String last) {
        this.last = last;
        return this;
    }
}
