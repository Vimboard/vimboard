package com.github.vimboard.service;

import org.junit.Test;
import static org.junit.Assert.*;

public class FunctionsTest {

    @Test
    public void escapeMarkupModifiersTest() {
        assertEquals("tinyboard abcdefgh",
                Functions.escapeMarkupModifiers("tinyboard abcdefgh"));
        assertEquals("<notinyboard abcdefgh>",
                Functions.escapeMarkupModifiers("<notinyboard abcdefgh>"));
        assertEquals("<tinyboard escape abcdefg>",
                Functions.escapeMarkupModifiers("<tinyboard abcdefg>"));
        assertEquals("<TinyboarD escape ab\ncd\tef gh >",
                Functions.escapeMarkupModifiers("<TinyboarD ab\ncd\tef gh >"));
    }
}
