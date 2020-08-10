package com.github.vimboard.config;

import com.github.vimboard.config.properties.VimboardBoardProperties;
import com.github.vimboard.config.properties.VimboardProperties;
import com.github.vimboard.config.settings.VimboardSettings;
import org.junit.Test;

import java.util.Map;

import static com.github.vimboard.utils.ArrayCheck.checkArray;
import static com.github.vimboard.utils.EntryCheck.checkEntry;
import static java.util.Map.entry;
import static org.junit.Assert.*;

public class SettingsBeanTest {

    @Test
    public void getTest() {
        {
            final VimboardProperties vimboardProperties = new VimboardProperties()
                    .setAll(new VimboardBoardProperties()
                            .setBoards(Map.ofEntries(
                                    entry("0", Map.of("Foo", Map.ofEntries(
                                            entry("0", "a"),
                                            entry("1", "b")
                                    ))),
                                    entry("1", Map.of("Bar", Map.ofEntries(
                                            entry("0", "c")
                                    ))),
                                    entry("2", "d"),
                                    entry("3", Map.of("e", "http://example.org/e")),
                                    entry("4", Map.of("Inner", Map.ofEntries(
                                            entry("0", "f"),
                                            entry("1", Map.of("Subinner", Map.ofEntries(
                                                    entry("0", "g"),
                                                    entry("1", Map.of("h", "http://example.org/h"))
                                            )))
                                    )))
                            )))
                    .setCustom(Map.of(
                            "a", new VimboardBoardProperties()
                                    .setBoards(Map.ofEntries(
                                            entry("0", "a")
                                    )),
                            "b", new VimboardBoardProperties()
                                    .setBoards(null)));

            VimboardSettings settings =
                    new SettingsBean(vimboardProperties, false).build();

            final Object[] all = settings.getCustom(null).getBoards();

            assertNotSame(all, settings.getCustom("a").getBoards());
            assertSame(all, settings.getCustom("b").getBoards());

            assertTrue(checkArray(all, Object[].class, 5)
                    .item(i -> assertTrue(checkEntry(i, "Foo")
                            .value(v -> checkArray(v, Object.class, 2)
                                    .item(i3 -> assertEquals("a", i3))
                                    .item(i3 -> assertEquals("b", i3))
                                    .end())))
                    .item(i -> assertTrue(checkEntry(i, "Bar")
                            .value(v -> checkArray(v, Object.class, 1)
                                    .item(i3 -> assertEquals("c", i3))
                                    .end())))
                    .item(i -> assertEquals("d", i))
                    .item(i -> assertTrue(checkEntry(i, "e", "http://example.org/e")))
                    .item(i -> assertTrue(checkEntry(i, "Inner")
                            .value(v -> checkArray(v, Object.class, 2)
                                    .item(i3 -> assertEquals("f", i3))
                                    .item(i3 -> assertTrue(checkEntry(i3, "Subinner")
                                            .value(v4 -> checkArray(v4, Object.class, 2)
                                                    .item(i5 -> assertEquals("g", i5))
                                                    .item(i5 -> assertTrue(checkEntry(i5, "h", "http://example.org/h")))))))))
                    .end());

            assertTrue(checkArray(settings.getCustom("a").getBoards(), Object.class, 1)
                    .item(i -> assertEquals("a", i))
                    .end());
        }
        {
            final VimboardProperties vimboardProperties = new VimboardProperties()
                    .setAll(new VimboardBoardProperties()
                            .setBoards(null));

            VimboardSettings settings =
                    new SettingsBean(vimboardProperties, false).build();
            assertNull(settings.getAll().getBoards());
        }
    }
}
