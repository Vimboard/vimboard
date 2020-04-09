package com.github.vimboard.model.mod;

import com.github.vimboard.domain.Pms;

import java.util.List;

public class InboxPage {

    private List<Pms> messages;
    private long unread;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public List<Pms> getMessages() {
        return messages;
    }

    public InboxPage setMessages(List<Pms> messages) {
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
