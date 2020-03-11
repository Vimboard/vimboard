package com.github.vimboard.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * Probably best not to change this unless you are smart enough to figure out
 * what you're doing. If you decide to change it, remember that it is
 * impossible to redefinite/overwrite groups; you may only add new ones.
 */
public enum Group implements GrantedAuthority {

    JANITOR((short) 10),

    MOD((short) 20),

    ADMIN((short) 30),

    // GOD((short) 98),

    DISABLED((short) 99);

    //------------------------------------------------------------------------
    // Definition
    //------------------------------------------------------------------------

    private final short type;

    Group(short type) {
        this.type = type;
    }

    public short getType() {
        return type;
    }

    public static Group valueOf(short type) {
        for (Group g : Group.values()) {
            if (g.getType() == type) {
                return g;
            }
        }
        return null;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + toString();
    }

    /**
     * Checks if this group has rights from the specified one.
     *
     * @param group the specified group.
     * @return {@code true} if this group has rights.
     */
    public boolean hasRole(Group group) {
        return this.ordinal() >= group.ordinal();
    }

    /**
     * Checks if a user with this group can be promoted.
     *
     * @return {@code true} if the user cann't be promoted.
     */
    public boolean canBePromoted() {
        return ordinal() < ADMIN.ordinal();
    }

    /**
     * Checks if a user with this group can be demoted.
     *
     * @return {@code true} if the user cann't be demoted.
     */
    public boolean canBeDemoted() {
        return ordinal() > JANITOR.ordinal() && !equals(DISABLED);
    }
}
