package com.github.vimboard.config;

import org.junit.Test;
import static com.github.vimboard.utils.ArrayCheck.*;
import static com.github.vimboard.utils.EntryCheck.*;
import static org.junit.Assert.*;

import java.util.Map;
import static java.util.Map.*;

public class BoardListBeanTest {

    @Test
    public void getTest() {
        {
            final VimboardProperties vimboardProperties = new VimboardProperties()
                    .setAll(new BoardProperties()
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
                            "a", new BoardProperties()
                                    .setBoards(Map.ofEntries(
                                            entry("0", "a")
                                    )),
                            "b", new BoardProperties()
                                    .setBoards(null)));
            vimboardProperties.init();

            BoardListBean bean = new BoardListBean(vimboardProperties);

            final Object[] all = bean.get("");

            assertNotSame(all, bean.get("a"));
            assertSame(all, bean.get("b"));

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

            assertTrue(checkArray(bean.get("a"), Object.class, 1)
                    .item(i -> assertEquals("a", i))
                    .end());
        }
        {
            final VimboardProperties vimboardProperties = new VimboardProperties()
                    .setAll(new BoardProperties()
                            .setBoards(null));
            vimboardProperties.init();

            BoardListBean bean = new BoardListBean(vimboardProperties);
            assertNull(bean.get(""));
        }
    }
}
