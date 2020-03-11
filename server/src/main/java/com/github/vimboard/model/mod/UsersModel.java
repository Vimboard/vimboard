package com.github.vimboard.model.mod;

import com.github.vimboard.model.domain.UserModel;

import java.util.List;

public class UsersModel {

    private List<UserModel> list;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public List<UserModel> getList() {
        return list;
    }

    public UsersModel setList(List<UserModel> list) {
        this.list = list;
        return this;
    }
}
