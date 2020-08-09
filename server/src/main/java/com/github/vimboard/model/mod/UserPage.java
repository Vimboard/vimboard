package com.github.vimboard.model.mod;

import com.github.vimboard.domain.Board;
import com.github.vimboard.domain.Group;
import com.github.vimboard.domain.Mod;
import com.github.vimboard.model.domain.ModLogModel;

import java.util.List;

public class UserPage {

    private List<Board> boards;
    private Group[] groups = Group.values();
    private boolean isNew = false;
    private List<ModLogModel> logs = null;
    private String token;
    private Mod user = null;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public List<Board> getBoards() {
        return boards;
    }

    public UserPage setBoards(List<Board> boards) {
        this.boards = boards;
        return this;
    }

    public Group[] getGroups() {
        return groups;
    }

    public UserPage setGroups(Group[] groups) {
        this.groups = groups;
        return this;
    }

    public boolean isNew() {
        return isNew;
    }

    public UserPage setNew(boolean aNew) {
        isNew = aNew;
        return this;
    }

    public List<ModLogModel> getLogs() {
        return logs;
    }

    public UserPage setLogs(List<ModLogModel> logs) {
        this.logs = logs;
        return this;
    }

    public String getToken() {
        return token;
    }

    public UserPage setToken(String token) {
        this.token = token;
        return this;
    }

    public Mod getUser() {
        return user;
    }

    public UserPage setUser(Mod user) {
        this.user = user;
        return this;
    }
}
