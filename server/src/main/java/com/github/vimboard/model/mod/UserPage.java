package com.github.vimboard.model.mod;

import com.github.vimboard.domain.Board;
import com.github.vimboard.domain.Mod;
import com.github.vimboard.domain.ModLog;

import java.util.List;

public class UserPage {

    private List<Board> boards;
    private List<ModLog> logs = null;
    private boolean isNew = false;
    private String token;
    private Mod user = null;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public List<Board> getBoards() {
        return boards;
    }

    public UserPage setBoards(List<Board> boards) {
        this.boards = boards;
        return this;
    }

    public List<ModLog> getLogs() {
        return logs;
    }

    public UserPage setLogs(List<ModLog> logs) {
        this.logs = logs;
        return this;
    }

    public boolean isNew() {
        return isNew;
    }

    public UserPage setNew(boolean isNew) {
        this.isNew = isNew;
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
