package com.github.vimboard.config;

import com.github.vimboard.config.properties.*;
import com.github.vimboard.config.settings.*;
import com.github.vimboard.domain.GenerationStrategy;
import com.github.vimboard.domain.Group;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.github.vimboard.config.SettingsBuilder.throwInvalidProperty;

/**
 * Builder of Vimboard settings.
 */
public class VimboardSettingsBuilder {

    private final VimboardSettings vimboardSettings;

    /**
     * Constructs builder.
     *
     * @param vimboardProperties Vimboard properties.
     * @param runAsCli value for {@link VimboardSettings#isRunAsCli()}.
     */
    public VimboardSettingsBuilder(VimboardProperties vimboardProperties,
            boolean runAsCli) {

        vimboardSettings = new VimboardSettings();

        final String tmp = vimboardProperties.getTmp();
        vimboardSettings.setTmp(tmp == null || tmp.isEmpty()
                ? "/tmp/vimboard/" : tmp);

        final String www = vimboardProperties.getWww();
        vimboardSettings.setWww(www == null || www.isEmpty()
                ? "/var/www/vimboard/public/" : www);

        // Board settings

        vimboardSettings.setAll(buildBoardSettings(
                vimboardProperties.getAll(), null));

        Map<String, VimboardBoardSettings> customSettings = new HashMap<>();
        if (vimboardProperties.getCustom() != null) {
            for (String boardUri : vimboardProperties.getCustom().keySet()) {
                customSettings.put(boardUri, buildBoardSettings(
                        vimboardProperties.getCustom().get(boardUri), boardUri));
            }
        }
        vimboardSettings.setCustom(customSettings);

        // Read-only settings

        vimboardSettings.setRunAsCli(runAsCli);

        vimboardSettings.setWww(VimboardEnv.getVersion());
    }

    /**
     * Builds Vimboard settings.
     *
     * @return Vimboard settings.
     */
    public VimboardSettings build() {
        return vimboardSettings;
    }

    // Builders --------------------------------------------------------------

    private VimboardApiSettings buildApiSettings(
            VimboardApiProperties p, String boardUri) {
        final VimboardApiSettings a = (boardUri == null
                ? null
                : vimboardSettings.getAll().getApi());

        final VimboardApiSettings r = new VimboardApiSettings();

        final SettingsBuilder<VimboardApiProperties, VimboardApiSettings> sb =
                new SettingsBuilder<>(boardUri, "api", p, a, r);

        sb.put("enabled", true);
        sb.put("extraFields", new HashMap<String, String>());

        return sb.build();
    }

    private VimboardBoardSettings buildBoardSettings(
            VimboardBoardProperties p, String boardUri) {
        final VimboardBoardSettings a = (boardUri == null
                ? null
                : vimboardSettings.getAll());

        final VimboardBoardSettings r = new VimboardBoardSettings();

        final SettingsBuilder<VimboardBoardProperties, VimboardBoardSettings> sb =
                new SettingsBuilder<>(boardUri, "", p, a, r);

        // Settings without dependencies

        sb.put("additionalJavascript", new String[] {
                "js/jquery.min.js",
                "js/inline-expanding.js"
        });
        sb.put("additionalJavascriptCompile", false);
        sb.put("allowSubtitleHtml", false);
        sb.put("alwaysRegenerateMarkup", false);
        sb.put("api", buildApiSettings(null, boardUri), this::convertApi);
        sb.put("autoUnicode", true);
        sb.put("banAppeals", false);
        sb.put("boardAbbreviation", "/{uri}/");
        sb.put("boardPath", "{uri}/");
        sb.put("boardRegex", "[0-9a-zA-Z$_\\u0080-\\uFFFF]{1,58}");
        sb.put("boardlistWrapBracket", false);
        sb.put("boards", null, this::convertBoards);
        sb.put("cookies", buildCookiesSettings(null, boardUri), this::convertCookies);
        sb.put("countryFlagsCondensed", true);
        sb.put("countryFlagsCondensedCss", "static/flags/flags.css");
        sb.put("debug", false);
        sb.put("dir", buildDirSettings(null, boardUri), this::convertDir);
        sb.put("embedding", new String[][] {
                {
                        "/^https?:\\/\\/(\\w+\\.)?youtube\\.com\\/watch\\?v=([a-zA-Z0-9\\-_]{10,11})(&.+)?$/i",
                        "<iframe style=\"float: left; margin: 10px 20px;\" width=\"%%tb_width%%\" height=\"%%tb_height%%\" frameborder=\"0\" id=\"ytplayer\" src=\"http://www.youtube.com/embed/$2\"></iframe>"
                },
                {
                        "/^https?:\\/\\/(\\w+\\.)?vimeo\\.com\\/(\\d{2,10})(\\?.+)?$/i",
                        "<iframe style=\"float: left; margin: 10px 20px;\" width=\"%%tb_width%%\" height=\"%%tb_height%%\" src=\"https://player.vimeo.com/video/$2\" frameborder=\"0\"></iframe>"
                },
                {
                        "/^https?:\\/\\/(\\w+\\.)?dailymotion\\.com\\/video\\/([a-zA-Z0-9]{2,10})(_.+)?$/i",
                        "<object style=\"float: left; margin: 10px 20px;\" width=\"%%tb_width%%\" height=\"%%tb_height%%\"><param name=\"movie\" value=\"http://www.dailymotion.com/swf/video/$2\"><param name=\"allowFullScreen\" value=\"true\"><param name=\"allowScriptAccess\" value=\"always\"><param name=\"wmode\" value=\"transparent\"><embed type=\"application/x-shockwave-flash\" src=\"http://www.dailymotion.com/swf/video/$2\" width=\"%%tb_width%%\" height=\"%%tb_height%%\" wmode=\"transparent\" allowfullscreen=\"true\" allowscriptaccess=\"always\"></object>"
                },
                {
                        "/^https?:\\/\\/(\\w+\\.)?metacafe\\.com\\/watch\\/(\\d+)\\/([a-zA-Z0-9_\\-.]+)\\/(\\?[^'\"<>]+)?$/i",
                        "<div style=\"float: left; margin: 10px 20px; width: %%tb_width%%px; height: %%tb_height%%px\"><embed flashVars=\"playerVars=showStats=no|autoPlay=no\" src=\"http://www.metacafe.com/fplayer/$2/$3.swf\" width=\"%%tb_width%%\" height=\"%%tb_height%%\" wmode=\"transparent\" allowFullScreen=\"true\" allowScriptAccess=\"always\" name=\"Metacafe_$2\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\"></div>"
                },
                {
                        "/^https?:\\/\\/video\\.google\\.com\\/videoplay\\?docid=(\\d+)([&#](.+)?)?$/i",
                        "<embed src=\"http://video.google.com/googleplayer.swf?docid=$1&hl=en&fs=true\" style=\"width: %%tb_width%%px; height: %%tb_height%%px; float: left; margin: 10px 20px\" allowFullScreen=\"true\" allowScriptAccess=\"always\" type=\"application/x-shockwave-flash\"></embed>"
                },
                {
                        "/^https?:\\/\\/(\\w+\\.)?vocaroo\\.com\\/i\\/([a-zA-Z0-9]{2,15})$/i",
                        "<object style=\"float: left; margin: 10px 20px;\" width=\"148\" height=\"44\"><param name=\"movie\" value=\"http://vocaroo.com/player.swf?playMediaID=$2&autoplay=0\"><param name=\"wmode\" value=\"transparent\"><embed src=\"http://vocaroo.com/player.swf?playMediaID=$2&autoplay=0\" width=\"148\" height=\"44\" wmode=\"transparent\" type=\"application/x-shockwave-flash\"></object>"
                }
        });
        sb.put("fileBoard", false);
        sb.put("fileIndex", "index.html");
        sb.put("filePage", "{no}.html");
        final String fileScript = (String) sb.put("fileScript", "main.js");
        sb.put("fontAwesome", true);
        sb.put("fontAwesomeCss", "stylesheets/font-awesome/css/font-awesome.min.css");
        sb.put("generationStrategies", new GenerationStrategy[] {
                GenerationStrategy.STRATEGY_SANE,
                GenerationStrategy.STRATEGY_IMMEDIATE
        });
        sb.put("linkPrefix", "");
        sb.put("markup", new String[][] {
                {
                    "'''(.+?)'''",
                    "<strong>$1</strong>"
                },
                {
                    "''(.+?)''",
                    "<em>$1</em>"
                },
                {
                    "\\*\\*(.+?)\\*\\*",
                    "<span class=\"spoiler\">$1</span>"
                },
                {
                    "(?m)^[ |\\t]*==(.+?)==[ |\\t]*$",
                    "<span class=\"heading\">$1</span>"
                }
        });
        sb.put("markupCode", "");
        sb.put("markupRepairTidy", false);
        sb.put("markupUrls", true);
        sb.put("maxCites", 45);
        sb.put("maxLinks", 20);
        sb.put("maxPages", 10);
        sb.put("metaKeywords", null);
        sb.put("minifyHtml", true);
        sb.put("mod", buildModSettings(null, boardUri), this::convertMod);
        sb.put("noko50Count", 50);
        sb.put("noko50Min", 100);
        sb.put("postDate", "MM/dd/yy (EEE) HH:mm:ss");
        sb.put("recaptcha", false);
        sb.put("redirectHttp", (short) 303);
        final String root = (String) sb.put("root", "/");
        final Map<String, String> stylesheets;
        {
            Map<String, String> defaultValue = new HashMap<>(2);
            defaultValue.put("Yotsuba B", "");
            defaultValue.put("Yotsuba", "yotsuba.css");
            //noinspection unchecked
            stylesheets = (Map<String, String>) sb.put("stylesheets", defaultValue);
        }
        sb.put("threadsPerPage", 10);
        sb.put("threadsPreview", 5);
        sb.put("threadsPreviewSticky", 1);
        sb.put("trySmarter", true);

        // Settings with dependencies

        sb.put("additionalJavascriptUrl", root);
        sb.put("defaultStylesheet",
                new String[] {
                        "Yotsuba B",
                        stylesheets.get("Yotsuba B")
                },
                (reader, value) -> convertDefaultStylesheet(reader, value, stylesheets));
        final String uriStylesheets = (String) sb.put("uriStylesheets", root + "stylesheets/");
        sb.put("urlJavascript", root + fileScript);
        sb.put("urlStylesheet", uriStylesheets + "style.css");

        return sb.build();
    }

    private VimboardCookiesSettings buildCookiesSettings(
            VimboardCookiesProperties p, String boardUri) {
        final VimboardCookiesSettings a = (boardUri == null
                ? null
                : vimboardSettings.getAll().getCookies());

        final VimboardCookiesSettings r = new VimboardCookiesSettings();

        final SettingsBuilder<VimboardCookiesProperties, VimboardCookiesSettings> sb =
                new SettingsBuilder<>(boardUri, "cookies", p, a, r);

        sb.put("salt", "abcdefghijklmnopqrstuvwxyz09123456789!@#$%^&*()");

        return sb.build();
    }

    private VimboardDirSettings buildDirSettings(
            VimboardDirProperties p, String boardUri) {
        final VimboardDirSettings a = (boardUri == null
                ? null
                : vimboardSettings.getAll().getDir());

        final VimboardDirSettings r = new VimboardDirSettings();

        final SettingsBuilder<VimboardDirProperties, VimboardDirSettings> sb =
                new SettingsBuilder<>(boardUri, "dir", p, a, r);

        sb.put("img", "img/");
        sb.put("thumb", "thumb/");
        sb.put("res", "res/");

        return sb.build();
    }

    private VimboardModSettings buildModSettings(
            VimboardModProperties p, String boardUri) {
        final VimboardModSettings a = (boardUri == null
                ? null
                : vimboardSettings.getAll().getMod());

        final VimboardModSettings r = new VimboardModSettings();

        final SettingsBuilder<VimboardModProperties, VimboardModSettings> sb =
                new SettingsBuilder<>(boardUri, "mod", p, a, r);

        // Settings without dependencies

        sb.put("dashboardLinks", new HashMap<String, String>());
        sb.put("noticeboardDashboard", 5);
        sb.put("snippetLength", 75);
        // Mod permissions
        sb.put("changePassword", Group.JANITOR);
        sb.put("createPm", Group.JANITOR);
        sb.put("createusers", Group.ADMIN);
        sb.put("debugSql", Group.DISABLED);
        sb.put("deleteusers", Group.ADMIN);
        sb.put("editConfig", Group.ADMIN);
        sb.put("editPages", Group.MOD);
        sb.put("editusers", Group.ADMIN);
        sb.put("manageboards", Group.ADMIN);
        sb.put("manageusers", Group.MOD);
        sb.put("masterPm", Group.ADMIN);
        sb.put("modlog", Group.ADMIN);
        sb.put("newboard", Group.ADMIN);
        sb.put("noticeboard", Group.JANITOR);
        sb.put("promoteusers", Group.ADMIN);
        sb.put("recent", Group.MOD);
        sb.put("reports", Group.JANITOR);
        sb.put("search", Group.JANITOR);
        sb.put("showIp", Group.MOD);
        sb.put("themes", Group.ADMIN);
        sb.put("viewBanAppeals", Group.MOD);
        sb.put("viewBanlist", Group.MOD);
        sb.put("viewNotes", Group.JANITOR);

        // Settings with dependencies

        return sb.build();
    }

    // Converters ------------------------------------------------------------

    private Object convertApi(String boardUri, Object value) {
        final VimboardApiProperties p = (VimboardApiProperties) value;
        return buildApiSettings(p, boardUri);
    }

    @SuppressWarnings("rawtypes")
    private Object[] convertBoards(String boardUri, Object value) {
        final String fieldName = "boards";
        final Map boards = (Map) value;
        if (boards == null) {
            return null;
        }
        final int len = boards.keySet().size();
        Object[] result = new Object[len];
        for (int i = 0; i < len; i++) {
            Object obj = boards.get(Integer.toString(i));
            if (obj == null) {
                throwInvalidProperty("", boardUri, fieldName);
            } else if (obj instanceof Map) {
                Map map = (Map) obj;
                if (map.keySet().size() != 1) {
                    throwInvalidProperty("", boardUri, fieldName);
                }
                for (Object key : map.keySet()) {
                    if (!(key instanceof String)) {
                        throwInvalidProperty("", boardUri, fieldName);
                    }
                    Object val = map.get(key);
                    if (val instanceof Map) {
                        result[i] = new AbstractMap.SimpleEntry<>(
                                key, convertBoards(boardUri, val));
                    } else if (val instanceof String) {
                        result[i] = new AbstractMap.SimpleEntry<>(key, val);
                    } else {
                        throwInvalidProperty("", boardUri, fieldName);
                    }
                }
            } else if (obj instanceof String) {
                result[i] = obj;
            } else {
                throwInvalidProperty("", boardUri, fieldName);
            }
        }
        return result;
    }

    private Object convertDefaultStylesheet(String boardUri, Object value,
            Map<String, String> stylesheets) {
        final String defaultStylesheet = (String) value;
        return new String[] {
                defaultStylesheet, stylesheets.get(defaultStylesheet) };
    }

    private Object convertCookies(String boardUri, Object value) {
        final VimboardCookiesProperties p = (VimboardCookiesProperties) value;
        return buildCookiesSettings(p, boardUri);
    }

    private Object convertDir(String boardUri, Object value) {
        final VimboardDirProperties p = (VimboardDirProperties) value;
        return buildDirSettings(p, boardUri);
    }

    private Object convertMod(String boardUri, Object value) {
        final VimboardModProperties p = (VimboardModProperties) value;
        return buildModSettings(p, boardUri);
    }
}
