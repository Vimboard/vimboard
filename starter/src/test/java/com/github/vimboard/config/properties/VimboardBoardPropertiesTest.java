package com.github.vimboard.config.properties;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VimboardBoardPropertiesTest {

    @Test
    public void ftest() {
        Pattern p = Pattern.compile("(\\d\\d)-(\\d\\d)");
        Matcher m = p.matcher("abcd 12-34 efghi 56-78 jklm");

        assertTrue(m.find());
        assertEquals(5, m.start());
        assertEquals("12-34", m.group());
        assertEquals(10, m.end());

        assertEquals(5, m.start(1));
        assertEquals("12", m.group(1));
        assertEquals(7, m.end(1));

        assertEquals(8, m.start(2));
        assertEquals("34", m.group(2));
        assertEquals(10, m.end(2));

        assertTrue(m.find());
        assertEquals(17, m.start());
        assertEquals("56-78", m.group());
        assertEquals(22, m.end());

        assertEquals(17, m.start(1));
        assertEquals("56", m.group(1));
        assertEquals(19, m.end(1));

        assertEquals(20, m.start(2));
        assertEquals("78", m.group(2));
        assertEquals(22, m.end(2));
    }

    @Test
    public void getMarkupCode() {
        {
            final String body = "Simple code: [code]echo 'Hello, world'[/code] end";

            Pattern pattern = Pattern.compile("(?si)\\[code\\](.*?)\\[\\/code\\]");
            Matcher matcher = pattern.matcher(body);

            assertTrue("Example 1 template is work",
                    matcher.find());
            assertEquals(1, matcher.groupCount());
            assertEquals("[code]echo 'Hello, world'[/code]",
                    matcher.group(0));
            assertEquals("echo 'Hello, world'",
                    matcher.group(1));

        }
        {
            final String body = "Complex code:\n"
                    + "```bash\n"
                    + "echo 'Hello, world'\n"
                    + "echo 'This is my first program'\n"
                    + "```\n"
                    + "end";

            Pattern pattern = Pattern.compile("(?s)```([a-z0-9-]{0,20})\\n(.*?)\\n?```\\n?");
            Matcher matcher = pattern.matcher(body);

            assertTrue("Example 2 template is work",
                    matcher.find());
            assertEquals(2, matcher.groupCount());
            assertEquals(
                    "```bash\n"
                            + "echo 'Hello, world'\n"
                            + "echo 'This is my first program'\n"
                            + "```\n",
                    matcher.group(0));
            assertEquals("bash",
                    matcher.group(1));
            assertEquals("echo 'Hello, world'\n"
                            + "echo 'This is my first program'",
                    matcher.group(2));
        }
    }
}