package com.github.vimboard.domain;

public class CitedPost {

    private long id;
    private Long thread;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public long getId() {
        return id;
    }

    public CitedPost setId(long id) {
        this.id = id;
        return this;
    }

    public Long getThread() {
        return thread;
    }

    public CitedPost setThread(Long thread) {
        this.thread = thread;
        return this;
    }
}
