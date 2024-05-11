package com.github.vimboard.config;

import com.github.vimboard.config.properties.VimboardBoardProperties;
import com.github.vimboard.config.properties.VimboardProperties;
import com.github.vimboard.config.settings.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static com.github.vimboard.utils.ArrayCheck.checkArray;
import static com.github.vimboard.utils.EntryCheck.checkEntry;
import static java.util.Map.entry;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = VimboardConfig.class)
@EnableAutoConfiguration
@ActiveProfiles("template")
public class VimboardSettingsBuilderTest {

    @Autowired
    VimboardSettings settings;

    @Test
    public void buildTest() {

        VimboardSettings actual = new VimboardSettingsBuilder(
                new VimboardProperties(), false).build();

        // TODO: Можно сократить код, используя AssertJ
        // TODO: или рефлексию

        assertEquals(
                settings.getTmp(),
                actual.getTmp());
        assertEquals(
                settings.getWww(),
                actual.getWww());

        buildBoardSettingsTest(
                settings.getAll(),
                actual.getAll());

        assertFalse(actual.isRunAsCli());
        assertNull(actual.getVersion());
    }

    private void buildBoardSettingsTest(
            VimboardBoardSettings expected, VimboardBoardSettings actual) {
        assertArrayEquals(
                expected.getAdditionalJavascript(),
                actual.getAdditionalJavascript());
        assertEquals(
                expected.getAdditionalJavascriptCompile(),
                actual.getAdditionalJavascriptCompile());
        assertEquals(
                expected.getAdditionalJavascriptUrl(),
                actual.getAdditionalJavascriptUrl());
        assertEquals(
                expected.getAllowSubtitleHtml(),
                actual.getAllowSubtitleHtml());
        assertEquals(
                expected.getAlwaysRegenerateMarkup(),
                actual.getAlwaysRegenerateMarkup());
        buildApiSettingsTest(
                expected.getApi(),
                actual.getApi());
        assertEquals(
                expected.getAutoUnicode(),
                actual.getAutoUnicode());
        assertEquals(
                expected.getBanAppeals(),
                actual.getBanAppeals());
        assertEquals(
                expected.getBoardAbbreviation(),
                actual.getBoardAbbreviation());
        assertEquals(
                expected.getBoardPath(),
                actual.getBoardPath());
        assertEquals(
                expected.getBoardRegex(),
                actual.getBoardRegex());
        assertEquals(
                expected.getBoardlistWrapBracket(),
                actual.getBoardlistWrapBracket());
        assertArrayEquals(
                expected.getBoards(),
                actual.getBoards());
        buildCookiesSettingsTest(
                expected.getCookies(),
                actual.getCookies());
        assertEquals(
                expected.getCountryFlagsCondensed(),
                actual.getCountryFlagsCondensed());
        assertEquals(
                expected.getCountryFlagsCondensedCss(),
                actual.getCountryFlagsCondensedCss());
        assertEquals(
                expected.getDebug(),
                actual.getDebug());
        assertArrayEquals(
                expected.getDefaultStylesheet(),
                actual.getDefaultStylesheet());
        buildDirSettingsTest(
                expected.getDir(),
                actual.getDir());
        assertArrayEquals(
                expected.getEmbedding(),
                actual.getEmbedding());
        assertEquals(
                expected.getFileBoard(),
                actual.getFileBoard());
        assertEquals(
                expected.getFileIndex(),
                actual.getFileIndex());
        assertEquals(
                expected.getFilePage(),
                actual.getFilePage());
        assertEquals(
                expected.getFileScript(),
                actual.getFileScript());
        assertEquals(
                expected.getFontAwesome(),
                actual.getFontAwesome());
        assertEquals(
                expected.getFontAwesomeCss(),
                actual.getFontAwesomeCss());
        assertArrayEquals(
                expected.getGenerationStrategies(),
                actual.getGenerationStrategies());
        assertEquals(
                expected.getLinkPrefix(),
                actual.getLinkPrefix());
        assertArrayEquals(
                expected.getMarkup(),
                actual.getMarkup());
        assertEquals(
                expected.getMarkupCode(),
                actual.getMarkupCode());
        assertEquals(
                expected.getMarkupRepairTidy(),
                actual.getMarkupRepairTidy());
        assertEquals(
                expected.getMarkupUrls(),
                actual.getMarkupUrls());
        assertEquals(
                expected.getMaxCites(),
                actual.getMaxCites());
        assertEquals(
                expected.getMaxLinks(),
                actual.getMaxLinks());
        assertEquals(
                expected.getMaxPages(),
                actual.getMaxPages());
        assertEquals(
                expected.getMetaKeywords(),
                actual.getMetaKeywords());
        assertEquals(
                expected.getMinifyHtml(),
                actual.getMinifyHtml());
        buildModSettingsTest(
                expected.getMod(),
                actual.getMod());
        assertEquals(
                expected.getNoko50Count(),
                actual.getNoko50Count());
        assertEquals(
                expected.getNoko50Min(),
                actual.getNoko50Min());
        assertEquals(
                expected.getPostDate(),
                actual.getPostDate());
        assertEquals(
                expected.getRecaptcha(),
                actual.getRecaptcha());
        assertEquals(
                expected.getRedirectHttp(),
                actual.getRedirectHttp());
        assertEquals(
                expected.getRoot(),
                actual.getRoot());
        assertEquals(
                expected.getStylesheets(),
                actual.getStylesheets());
        assertEquals(
                expected.getThreadsPerPage(),
                actual.getThreadsPerPage());
        assertEquals(
                expected.getThreadsPreview(),
                actual.getThreadsPreview());
        assertEquals(
                expected.getThreadsPreviewSticky(),
                actual.getThreadsPreviewSticky());
        assertEquals(
                expected.getTrySmarter(),
                actual.getTrySmarter());
        assertEquals(
                expected.getUriStylesheets(),
                actual.getUriStylesheets());
        assertEquals(
                expected.getUrlJavascript(),
                actual.getUrlJavascript());
        assertEquals(
                expected.getUrlStylesheet(),
                actual.getUrlStylesheet());
    }

    private void buildApiSettingsTest(
            VimboardApiSettings expected, VimboardApiSettings actual) {
        assertEquals(
                expected.getEnabled(),
                actual.getEnabled());
        assertEquals(
                expected.getExtraFields(),
                actual.getExtraFields());
    }

    private void buildCookiesSettingsTest(
            VimboardCookiesSettings expected, VimboardCookiesSettings actual) {
        assertEquals(
                expected.getSalt(),
                actual.getSalt());
    }

    private void buildDirSettingsTest(
            VimboardDirSettings expected, VimboardDirSettings actual) {
        assertEquals(
                expected.getImg(),
                actual.getImg());
        assertEquals(
                expected.getRes(),
                actual.getRes());
        assertEquals(
                expected.getThumb(),
                actual.getThumb());
    }

    private void buildModSettingsTest(
            VimboardModSettings expected, VimboardModSettings actual) {
        assertEquals(
                expected.getDashboardLinks(),
                actual.getDashboardLinks());
        assertEquals(
                expected.getNoticeboardDashboard(),
                actual.getNoticeboardDashboard());
        assertEquals(
                expected.getSnippetLength(),
                actual.getSnippetLength());
        // Mod permissions
        assertEquals(
                expected.getChangePassword(),
                actual.getChangePassword());
        assertEquals(
                expected.getCreatePm(),
                actual.getCreatePm());
        assertEquals(
                expected.getCreateusers(),
                actual.getCreateusers());
        assertEquals(
                expected.getDebugSql(),
                actual.getDebugSql());
        assertEquals(
                expected.getDeleteusers(),
                actual.getDeleteusers());
        assertEquals(
                expected.getEditConfig(),
                actual.getEditConfig());
        assertEquals(
                expected.getEditPages(),
                actual.getEditPages());
        assertEquals(
                expected.getEditusers(),
                actual.getEditusers());
        assertEquals(
                expected.getManageboards(),
                actual.getManageboards());
        assertEquals(
                expected.getManageusers(),
                actual.getManageusers());
        assertEquals(
                expected.getMasterPm(),
                actual.getMasterPm());
        assertEquals(
                expected.getModlog(),
                actual.getModlog());
        assertEquals(
                expected.getNewboard(),
                actual.getNewboard());
        assertEquals(
                expected.getNoticeboard(),
                actual.getNoticeboard());
        assertEquals(
                expected.getPromoteusers(),
                actual.getPromoteusers());
        assertEquals(
                expected.getRecent(),
                actual.getRecent());
        assertEquals(
                expected.getReports(),
                actual.getReports());
        assertEquals(
                expected.getSearch(),
                actual.getSearch());
        assertEquals(
                expected.getShowIp(),
                actual.getShowIp());
        assertEquals(
                expected.getThemes(),
                actual.getThemes());
        assertEquals(
                expected.getViewBanAppeals(),
                actual.getViewBanAppeals());
        assertEquals(
                expected.getViewBanlist(),
                actual.getViewBanlist());
        assertEquals(
                expected.getViewNotes(),
                actual.getViewNotes());
    }

    @Test
    public void convertBoardsTest() {
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
                    new VimboardSettingsBuilder(vimboardProperties, false).build();

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
                    new VimboardSettingsBuilder(vimboardProperties, false).build();
            assertNull(settings.getAll().getBoards());
        }
    }
}
