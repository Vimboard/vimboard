package com.github.vimboard.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PmsServiceTest {

    @Test
    public void snippetTest() {
        assertEquals("<em>abcdefghij</em>",
                PmsService.snippet("abcdefghij", 10));
        assertEquals("<em>abcdefghij&hellip;</em>",
                PmsService.snippet("abcdefghijklmnop", 10));

        assertEquals("<em>First line.  Second line.</em>",
                PmsService.snippet("First line.<br>Second line.", 100));
        assertEquals("<em>First line.  Second line.</em>",
                PmsService.snippet("First line.<BR>Second line.", 100));
        assertEquals("<em>First line.  Second line.</em>",
                PmsService.snippet("First line.<Br>Second line.", 100));
        assertEquals("<em>First line.  Second line.</em>",
                PmsService.snippet("First line.<br/>Second line.", 100));
        assertEquals("<em>First line.  Second line.</em>",
                PmsService.snippet("First line.<BR/>Second line.", 100));
        assertEquals("<em>First line.  Second line.</em>",
                PmsService.snippet("First line.<bR/>Second line.", 100));

        assertEquals("<em>Hello, &amp;wo&hellip;</em>",
                PmsService.snippet("<foo>Hello<bar>, <b>&amp;world!</b>", 10));

        assertEquals("<em>&amp; &lt; &gt; &quot; &amp;apos;</em>",
                PmsService.snippet("&amp; &lt; &gt; &quot; &apos;", 100));
    }
}
