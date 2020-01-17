package com.github.vimboard.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class Mod extends User {

    private Group type;
    private String[] boards;

    public Mod(String username, String password,
            Collection<? extends GrantedAuthority> authorities,
            Group type, String[] boards) {
        super(username, password, authorities);
        this.type = type;
        this.boards = boards;
    }

    public Mod(String username, String password, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities,
            Group type, String[] boards) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
        this.type = type;
        this.boards = boards;
    }

    public Group getType() {
        return type;
    }

    public String[] getBoards() {
        return boards;
    }
}
