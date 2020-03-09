package com.github.vimboard.model.mod;

import com.github.vimboard.domain.dashboard.User;

import java.util.List;

public class UsersModel {

    private List<User> list;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public List<User> getList() {
        return list;
    }

    public UsersModel setList(List<User> list) {
        this.list = list;
        return this;
    }
}
