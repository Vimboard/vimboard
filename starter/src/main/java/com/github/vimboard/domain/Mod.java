package com.github.vimboard.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO
 */
public class Mod implements UserDetails {

    /**
     * TODO
     */
    private int id;

    /**
     * TODO
     */
    private String username;

    /**
     * TODO
     */
    private String password;

    /**
     * TODO
     */
    private Group type;

    /**
     * TODO
     */
    private String[] boards;

    //------------------------------------------------------------------------
    // UserDetails
    //------------------------------------------------------------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Group> authorities = new ArrayList<>(1);
        authorities.add(type);
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public Mod setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Mod setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Mod setPassword(String password) {
        this.password = password;
        return this;
    }

    public Group getType() {
        return type;
    }

    public Mod setType(Group type) {
        this.type = type;
        return this;
    }

    public String[] getBoards() {
        return boards;
    }

    public Mod setBoards(String[] boards) {
        this.boards = boards;
        return this;
    }
}
