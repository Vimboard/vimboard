package com.github.vimboard.domain;

public class NumPosts {

    private int replies;
    private int images;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public int getReplies() {
        return replies;
    }

    public NumPosts setReplies(int replies) {
        this.replies = replies;
        return this;
    }

    public int getImages() {
        return images;
    }

    public NumPosts setImages(int images) {
        this.images = images;
        return this;
    }
}
