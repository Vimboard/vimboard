package com.github.vimboard.model.mod;

import com.github.vimboard.model.domain.PmsModel;

import java.util.List;

public class InboxPage {

    private List<PmsModel> messages;
    private long unread;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public List<PmsModel> getMessages() {
        return messages;
    }

    public InboxPage setMessages(List<PmsModel> messages) {
        this.messages = messages;
        return this;
    }

    public long getUnread() {
        return unread;
    }

    public InboxPage setUnread(long unread) {
        this.unread = unread;
        return this;
    }
}
