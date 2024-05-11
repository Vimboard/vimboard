package com.github.vimboard.service;

import com.github.vimboard.service.types.BodyRef;
import com.github.vimboard.service.types.ServiceException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FunctionsTest {

    private FunctionsService functionsService;

    @Before
    public void beforeClass() {
        functionsService = new FunctionsService(null, null, null, null);
    }

    @Test
    public void extractModifiersTest() {
        final FunctionsService.Functions f = functionsService.create(null);
        {
            final Map<String, String> expected = new HashMap<>();
            expected.put("foo", "111");
            expected.put("bar", "222");
            expected.put("baz", "333");
            expected.put("a b", "444");

            final String body =
                    "<tinyboard foo>111</tinyboard>"
                            + "<tinyboard bar>222</tinyboard>\n"
                            + "<tinyboard baz>333</tinyboard>"
                            + "<tinyboard a b>444</tinyboard>";

            assertEquals("Extract all modifiers",
                    expected, f.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();
            expected.put("  key  foo\t\tbar  ", "value");

            final String body = "<tinyboard   key  foo\t\tbar  >value</tinyboard>";

            assertEquals("Modifier key containing spaces",
                    expected, f.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();
            expected.put("key", "  value  foo\t\tbar  ");

            final String body = "<tinyboard key>  value  foo\t\tbar  </tinyboard>";

            assertEquals("Modifier value containing spaces",
                    expected, f.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();
            expected.put("key\nfoo\rbar\n\rbaz\r\nqux", "value");

            final String body = "<tinyboard key\nfoo\rbar\n\rbaz\r\nqux>value</tinyboard>";

            assertEquals("Modifier key containing newlines - \\n \\r",
                    expected, f.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();
            expected.put(String.format("key%nfoo%nbar"), "value");

            final String body = String.format("<tinyboard key%nfoo%nbar>value</tinyboard>");

            assertEquals("Modifier key containing newlines - %n",
                    expected, f.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();

            final String body = "<tinyboard key>value\nfoo</tinyboard>";

            assertEquals("Modifier value containing newlines - \\n",
                    expected, f.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();

            final String body = "<tinyboard key>value\rbar</tinyboard>";

            assertEquals("Modifier value containing newlines - \\r",
                    expected, f.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();

            final String body = String.format("<tinyboard key>value%nbaz</tinyboard>");

            assertEquals("Modifier value containing newlines - %n",
                    expected, f.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();
            expected.put("key", "<b>eee</b>");

            final String body = "<tinyboard key>&lt;b&gt;eee&#60;&#47;&#98;&#62;</tinyboard>";

            assertEquals("Unescaped value HTML",
                    expected, f.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();

            final String body = "<tinyboard complex-key>value</tinyboard>";

            assertEquals("Complex key",
                    expected, f.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();

            final String body = "<tinyboard escape key>value</tinyboard>";

            assertEquals("Escaped key",
                    expected, f.extractModifiers(body));
        }
    }

    @Test
    public void removeModifiersTest() {
        final FunctionsService.Functions f = functionsService.create(null);
        final String expected = "beginend";
        {
            final String body =
                    "<tinyboard foo>111</tinyboard> "
                            + "<tinyboard bar>222</tinyboard>\n"
                            + "<tinyboard baz>333</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("All modifiers",
                    "begin \nend", f.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard   key  foo\t\tbar  >value</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier key containing spaces",
                    expected, f.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard key>  value  foo\t\tbar  </tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier value containing spaces",
                    expected, f.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard key\nfoo\rbar\n\rbaz\r\nqux>value</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier key containing newlines - \\n \\r",
                    expected, f.removeModifiers(markedBody));
        }
        {
            final String body = String.format("<tinyboard key%nfoo%nbar>value</tinyboard>");
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier key containing newlines - %n",
                    expected, f.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard key>value\nfoo</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier value containing newlines - \\n",
                    markedBody, f.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard key>value\rbar</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier value containing newlines - \\r",
                    markedBody, f.removeModifiers(markedBody));
        }
        {
            final String body = String.format("<tinyboard key>value%nbaz</tinyboard>");
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier value containing newlines - %n",
                    markedBody, f.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard key>&lt;b&gt;eee&#60;&#47;&#98;&#62;</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Unescaped value HTML",
                    expected, f.removeModifiers(markedBody));
        }
        {
            final String body = "begin<tinyboard complex-key>value</tinyboard>end";
            final String markedBody = "begin" + body + "end";

            assertEquals("Complex key",
                    markedBody, f.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard escape key>value</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Escaped key",
                    expected, f.removeModifiers(markedBody));
        }
    }

    @Test
    public void markupTest() {
        final FunctionsService.Functions f = functionsService.create(null);
        BodyRef bodyRef = new BodyRef("foo");
        try {
            f.markup(bodyRef);
        } catch (ServiceException ex) {
            fail();
        }
        assertEquals("foo", bodyRef.body);
    }

    @Test
    public void escapeMarkupModifiersTest() {
        final FunctionsService.Functions f = functionsService.create(null);
        assertEquals("Escape modifier",
                "<tinyboard escape key>",
                f.escapeMarkupModifiers("<tinyboard key>"));

        assertEquals("Escape modifier with delimiters ignore case",
                "<TinyboarD escape ab\nc\rd\tef gh >",
                f.escapeMarkupModifiers("<TinyboarD ab\nc\rd\tef gh >"));

        assertEquals("Skip no tag string",
                "tinyboard abcdefgh",
                f.escapeMarkupModifiers("tinyboard abcdefgh"));
        
        assertEquals("Skip no tinyboard tags",
                "<notinyboard abcdefgh>",
                f.escapeMarkupModifiers("<notinyboard abcdefgh>"));
    }

    @Test
    public void removeMarkupModifiersStringTest() {
        final FunctionsService.Functions f = functionsService.create(null);
        assertEquals("Remove modifier",
                "",
                f.removeMarkupModifiersString("<tinyboard key>Content</tinyboard>"));

        assertEquals("Remove modifier with delimiters",
                "",
                f.removeMarkupModifiersString("<tinyboard ab\nc\rd\tef gh >Content</tinyboard>"));

        assertEquals("Skip no tag string",
                "tinyboard abcdefgh",
                f.removeMarkupModifiersString("tinyboard abcdefgh"));

        assertEquals("Skip no tinyboard tags",
                "<notinyboard abcdefgh>Content</notinyboard>",
                f.removeMarkupModifiersString("<notinyboard abcdefgh>Content</notinyboard>"));
    }

    @Test
    public void unescapeMarkupModifiers() {
        final FunctionsService.Functions f = functionsService.create(null);
        assertEquals("Unescape modifier",
                "<tinyboard key>",
                f.unescapeMarkupModifiers("<tinyboard escape key>"));

        assertEquals("Escape modifier with delimiters ignore case",
                "<TinyboarD ab\nc\rd\tef gh >",
                f.unescapeMarkupModifiers("<TinyboarD escape ab\nc\rd\tef gh >"));

        assertEquals("Skip no tag string",
                "tinyboard escape abcdefgh",
                f.unescapeMarkupModifiers("tinyboard escape abcdefgh"));

        assertEquals("Skip no tinyboard tags",
                "<notinyboard escape abcdefgh>",
                f.unescapeMarkupModifiers("<notinyboard escape abcdefgh>"));
    }
}
