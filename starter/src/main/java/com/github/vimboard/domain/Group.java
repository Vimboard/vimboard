package com.github.vimboard.domain;

/**
 * Probably best not to change this unless you are smart enough to figure out
 * what you're doing. If you decide to change it, remember that it is
 * impossible to redefinite/overwrite groups; you may only add new ones.
 */
public enum Group {

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
}
