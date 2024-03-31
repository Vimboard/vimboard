package com.github.vimboard.service;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FunctionsTest {

    @Test
    public void extractModifiersTest() {
        {
            final Map<String, String> expected = new HashMap<>();
            expected.put("foo", "111");
            expected.put("bar", "222");
            expected.put("baz", "333");

            final String body =
                    "<tinyboard foo>111</tinyboard>"
                            + "<tinyboard bar>222</tinyboard>\n"
                            + "<tinyboard baz>333</tinyboard>";

            assertEquals("Extract all modifiers",
                    expected, Functions.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();
            expected.put("  key  foo\t\tbar  ", "value");

            final String body = "<tinyboard   key  foo\t\tbar  >value</tinyboard>";

            assertEquals("Modifier key containing spaces",
                    expected, Functions.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();
            expected.put("key", "  value  foo\t\tbar  ");

            final String body = "<tinyboard key>  value  foo\t\tbar  </tinyboard>";

            assertEquals("Modifier value containing spaces",
                    expected, Functions.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();
            expected.put("key\nfoo\rbar\n\rbaz\r\nqux", "value");

            final String body = "<tinyboard key\nfoo\rbar\n\rbaz\r\nqux>value</tinyboard>";

            assertEquals("Modifier key containing newlines - \\n \\r",
                    expected, Functions.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();
            expected.put(String.format("key%nfoo%nbar"), "value");

            final String body = String.format("<tinyboard key%nfoo%nbar>value</tinyboard>");

            assertEquals("Modifier key containing newlines - %n",
                    expected, Functions.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();

            final String body = "<tinyboard key>value\nfoo</tinyboard>";

            assertEquals("Modifier value containing newlines - \\n",
                    expected, Functions.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();

            final String body = "<tinyboard key>value\rbar</tinyboard>";

            assertEquals("Modifier value containing newlines - \\r",
                    expected, Functions.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();

            final String body = String.format("<tinyboard key>value%nbaz</tinyboard>");

            assertEquals("Modifier value containing newlines - %n",
                    expected, Functions.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();
            expected.put("key", "<b>eee</b>");

            final String body = "<tinyboard key>&lt;b&gt;eee&#60;&#47;&#98;&#62;</tinyboard>";

            assertEquals("Unescaped value HTML",
                    expected, Functions.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();

            final String body = "<tinyboard complex-key>value</tinyboard>";

            assertEquals("Complex key",
                    expected, Functions.extractModifiers(body));
        }
        {
            final Map<String, String> expected = new HashMap<>();

            final String body = "<tinyboard escape key>value</tinyboard>";

            assertEquals("Escaped key",
                    expected, Functions.extractModifiers(body));
        }
    }

    @Test
    public void removeModifiersTest() {
        final String expected = "beginend";
        {
            final String body =
                    "<tinyboard foo>111</tinyboard> "
                            + "<tinyboard bar>222</tinyboard>\n"
                            + "<tinyboard baz>333</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("All modifiers",
                    "begin \nend", Functions.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard   key  foo\t\tbar  >value</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier key containing spaces",
                    expected, Functions.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard key>  value  foo\t\tbar  </tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier value containing spaces",
                    expected, Functions.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard key\nfoo\rbar\n\rbaz\r\nqux>value</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier key containing newlines - \\n \\r",
                    expected, Functions.removeModifiers(markedBody));
        }
        {
            final String body = String.format("<tinyboard key%nfoo%nbar>value</tinyboard>");
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier key containing newlines - %n",
                    expected, Functions.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard key>value\nfoo</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier value containing newlines - \\n",
                    markedBody, Functions.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard key>value\rbar</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier value containing newlines - \\r",
                    markedBody, Functions.removeModifiers(markedBody));
        }
        {
            final String body = String.format("<tinyboard key>value%nbaz</tinyboard>");
            final String markedBody = "begin" + body + "end";

            assertEquals("Modifier value containing newlines - %n",
                    markedBody, Functions.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard key>&lt;b&gt;eee&#60;&#47;&#98;&#62;</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Unescaped value HTML",
                    expected, Functions.removeModifiers(markedBody));
        }
        {
            final String body = "begin<tinyboard complex-key>value</tinyboard>end";
            final String markedBody = "begin" + body + "end";

            assertEquals("Complex key",
                    markedBody, Functions.removeModifiers(markedBody));
        }
        {
            final String body = "<tinyboard escape key>value</tinyboard>";
            final String markedBody = "begin" + body + "end";

            assertEquals("Escaped key",
                    expected, Functions.removeModifiers(markedBody));
        }
    }

    @Test
    public void markupTest() {
        assertEquals("foo",
                Functions.markup("foo"));
    }

    @Test
    public void escapeMarkupModifiersTest() {
        assertEquals("Escape modifier",
                "<tinyboard escape key>",
                Functions.escapeMarkupModifiers("<tinyboard key>"));

        assertEquals("Escape modifier with delimiters ignore case",
                "<TinyboarD escape ab\nc\rd\tef gh >",
                Functions.escapeMarkupModifiers("<TinyboarD ab\nc\rd\tef gh >"));

        assertEquals("Skip no tag string",
                "tinyboard abcdefgh",
                Functions.escapeMarkupModifiers("tinyboard abcdefgh"));
        
        assertEquals("Skip no tinyboard tags",
                "<notinyboard abcdefgh>",
                Functions.escapeMarkupModifiers("<notinyboard abcdefgh>"));
    }
}
