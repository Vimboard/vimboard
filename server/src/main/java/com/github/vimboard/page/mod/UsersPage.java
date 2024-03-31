package com.github.vimboard.page.mod;

import com.github.vimboard.model.UserModel;

import java.util.List;

public class UsersPage {

    private List<UserModel> list;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public List<UserModel> getList() {
        return list;
    }

    public UsersPage setList(List<UserModel> list) {
        this.list = list;
        return this;
    }
}
