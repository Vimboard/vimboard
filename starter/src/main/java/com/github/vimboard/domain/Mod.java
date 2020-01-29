package com.github.vimboard.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

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
    // Getters and setters
    //------------------------------------------------------------------------

    /**
     * Getter for {@link #id}.
     *
     * @return {@link #id}.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for {@link #id}.
     *
     * @param id {@link #id}.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for {@link #username}.
     *
     * @return {@link #username}.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for {@link #username}.
     *
     * @param username {@link #username}.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for {@link #password}.
     *
     * @return {@link #password}.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for {@link #password}.
     *
     * @param password {@link #password}.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for {@link #type}.
     *
     * @return {@link #type}.
     */
    public Group getType() {
        return type;
    }

    /**
     * Setter for {@link #type}.
     *
     * @param type {@link #type}.
     */
    public void setType(Group type) {
        this.type = type;
    }

    /**
     * Getter for {@link #boards}.
     *
     * @return {@link #boards}.
     */
    public String[] getBoards() {
        return boards;
    }

    /**
     * Setter for {@link #boards}.
     *
     * @param boards {@link #boards}.
     */
    public void setBoards(String[] boards) {
        this.boards = boards;
    }
}
