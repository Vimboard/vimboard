package com.github.vimboard.config;

import com.github.vimboard.config.properties.*;
import com.github.vimboard.config.settings.*;
import com.github.vimboard.domain.GenerationStrategy;
import com.github.vimboard.domain.Group;

import javax.validation.ValidationException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: refactor with SettingsBuilder
 */
public class SettingsBean {

    private static final String INVALID_PROPERTY = "Invalid '%s' property value";

    private final VimboardSettings vimboardSettings;

    public SettingsBean(VimboardProperties vimboardProperties,
            boolean runAsCli) {

        vimboardSettings = new VimboardSettings();

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

        vimboardSettings.setWww(VimboardVersion.get());
    }

    /**
     * TODO:
     *
     * @return
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
        sb.put("api", buildApiSettings(null, boardUri), this::convertApi);
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
        sb.put("fileIndex", "index.html");
        final String fileScript = (String) sb.put("fileScript", "main.js");
        sb.put("fontAwesome", true);
        sb.put("fontAwesomeCss", "stylesheets/font-awesome/css/font-awesome.min.css");
        sb.put("generationStrategies", new GenerationStrategy[] {
                GenerationStrategy.STRATEGY_SANE,
                GenerationStrategy.STRATEGY_IMMEDIATE
        });
        sb.put("metaKeywords", null);
        sb.put("minifyHtml", true);
        sb.put("mod", buildModSettings(null, boardUri), this::convertMod);
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

    private Object convertApi(SettingsBuilder sb, Object value) {
        final VimboardApiProperties p = (VimboardApiProperties) value;
        return buildApiSettings(p, sb.boardUri);
    }

    @SuppressWarnings("rawtypes")
    private Object[] convertBoards(SettingsBuilder sb, Object value) {
        final String boardUri = sb.boardUri;
        final Map boards = (Map) value;
        if (boards == null) {
            return null;
        }
        final int len = boards.keySet().size();
        Object[] result = new Object[len];
        for (int i = 0; i < len; i++) {
            Object obj = boards.get(Integer.toString(i));
            if (obj == null) {
                throwInvalid(boardUri);
            } else if (obj instanceof Map) {
                Map map = (Map) obj;
                if (map.keySet().size() != 1) {
                    throwInvalid(boardUri);
                }
                for (Object key : map.keySet()) {
                    if (!(key instanceof String)) {
                        throwInvalid(boardUri);
                    }
                    Object val = map.get(key);
                    if (val instanceof Map) {
                        result[i] = new AbstractMap.SimpleEntry<>(key, convertBoards(sb, val));
                    } else if (val instanceof String) {
                        result[i] = new AbstractMap.SimpleEntry<>(key, val);
                    } else {
                        throwInvalid(boardUri);
                    }
                }
            } else if (obj instanceof String) {
                result[i] = obj;
            } else {
                throwInvalid(boardUri);
            }
        }
        return result;
    }

    private Object convertDefaultStylesheet(SettingsBuilder sb, Object value,
            Map<String, String> stylesheets) {
        final String defaultStylesheet = (String) value;
        return new String[] { defaultStylesheet, stylesheets.get(defaultStylesheet) };
    }

    private Object convertCookies(SettingsBuilder sb, Object value) {
        final VimboardCookiesProperties p = (VimboardCookiesProperties) value;
        return buildCookiesSettings(p, sb.boardUri);
    }

    private Object convertDir(SettingsBuilder sb, Object value) {
        final VimboardDirProperties p = (VimboardDirProperties) value;
        return buildDirSettings(p, sb.boardUri);
    }

    private Object convertMod(SettingsBuilder sb, Object value) {
        final VimboardModProperties p = (VimboardModProperties) value;
        return buildModSettings(p, sb.boardUri);
    }

    // Other -----------------------------------------------------------------

    private void throwInvalid(String boardUri) {
        throw new ValidationException(String.format(INVALID_PROPERTY,
                propertyPath(boardUri) + ".boards"));
    }

    private String propertyPath(String boardUri) {
        return boardUri == null
                ? "vimboard.all"
                : "vimboard.custom." + boardUri + "";
    }
}
