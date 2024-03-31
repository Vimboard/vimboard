package com.github.vimboard.model;

import com.github.vimboard.domain.Group;

public class UserModel {

    private int id;
    private String username;
    private Group type;
    private String[] boards;
    private String last;
    private String action;
    private boolean canBePromoted;
    private boolean canBeDemoted;
    private String promoteToken;
    private String demoteToken;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public UserModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public Group getType() {
        return type;
    }

    public UserModel setType(Group type) {
        this.type = type;
        return this;
    }

    public String[] getBoards() {
        return boards;
    }

    public UserModel setBoards(String[] boards) {
        this.boards = boards;
        return this;
    }

    public String getLast() {
        return last;
    }

    public UserModel setLast(String last) {
        this.last = last;
        return this;
    }

    public String getAction() {
        return action;
    }

    public UserModel setAction(String action) {
        this.action = action;
        return this;
    }

    public boolean isCanBePromoted() {
        return canBePromoted;
    }

    public UserModel setCanBePromoted(boolean canBePromoted) {
        this.canBePromoted = canBePromoted;
        return this;
    }

    public boolean isCanBeDemoted() {
        return canBeDemoted;
    }

    public UserModel setCanBeDemoted(boolean canBeDemoted) {
        this.canBeDemoted = canBeDemoted;
        return this;
    }

    public String getPromoteToken() {
        return promoteToken;
    }

    public UserModel setPromoteToken(String promoteToken) {
        this.promoteToken = promoteToken;
        return this;
    }

    public String getDemoteToken() {
        return demoteToken;
    }

    public UserModel setDemoteToken(String demoteToken) {
        this.demoteToken = demoteToken;
        return this;
    }
}
