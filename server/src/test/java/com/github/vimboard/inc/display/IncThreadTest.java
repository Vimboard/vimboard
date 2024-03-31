package com.github.vimboard.inc.display;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IncThreadTest {

    @Test
    public void fixInternalLinksTest() {
        final String boardRegex = "[0-9a-zA-Z$_\u0080-\uFFFF]{1,58}";
        {
            final String expected = "<a href=\"?/a/index.html\">link</a>";
            final String body = "<a href=\"/a/index.html\">link</a>";
            final String root = "/";
            final String boardPath = "{uri}/";

            assertEquals("Index page of /a/",
                    expected,
                    IncThread.fixInternalLinks(body, root, boardPath, boardRegex));
        }
        {
            final String expected = "<a one=foo two=\"bar\" href=\"?/a/index.html\" three=baz>link</a>";
            final String body = "<a one=foo two=\"bar\" href=\"/a/index.html\" three=baz>link</a>";
            final String root = "/";
            final String boardPath = "{uri}/";

            assertEquals("Additional attributes",
                    expected,
                    IncThread.fixInternalLinks(body, root, boardPath, boardRegex));
        }
        {
            final String expected = "<a href=\"?/a/index.html\">link</a>";
            final String body = "<a href=\"http://example.com/a/index.html\">link</a>";
            final String root = "http://example.com/";
            final String boardPath = "{uri}/";

            assertEquals("Complex root",
                    expected,
                    IncThread.fixInternalLinks(body, root, boardPath, boardRegex));
        }
        {
            final String expected = "<a href=\"?/boards/a-board/index.html\">link</a>";
            final String body = "<a href=\"http://example.com/boards/a-board/index.html\">link</a>";
            final String root = "http://example.com/";
            final String boardPath = "boards/{uri}-board/";

            assertEquals("Complex board path",
                    expected,
                    IncThread.fixInternalLinks(body, root, boardPath, boardRegex));
        }
    }
}
