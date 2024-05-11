package com.github.vimboard.service;

import com.github.vimboard.service.types.SecurityUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SecurityUtilsTest {

    @Test
    public void sha1() {
        assertEquals("b1b3773a05c0ed0176787a4f1574ff0075f7521e",
                SecurityUtils.sha1("qwerty"));
        assertEquals("88b184adea10bf987b15257a5d6c5cb94eba69d3",
                SecurityUtils.sha1("яблоко"));
    }
}
