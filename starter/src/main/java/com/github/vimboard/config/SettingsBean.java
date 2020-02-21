package com.github.vimboard.config;

import com.github.vimboard.config.properties.VimboardBoardProperties;
import com.github.vimboard.config.properties.VimboardModProperties;
import com.github.vimboard.config.properties.VimboardProperties;
import com.github.vimboard.config.settings.VimboardBoardSettings;
import com.github.vimboard.config.settings.VimboardModSettings;
import com.github.vimboard.config.settings.VimboardSettings;
import com.github.vimboard.domain.Group;

import javax.validation.ValidationException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class SettingsBean {

    private static final String INVALID_PROPERTY = "Invalid '%s' property value";

    private final VimboardSettings vimboardSettings;

    public SettingsBean(VimboardProperties vimboardProperties) {
        vimboardSettings = new VimboardSettings();

        final String www = vimboardProperties.getWww();
        vimboardSettings.setWww(www == null || www.isEmpty()
                ? "/var/www/vimboard/public/" : www);

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
    }

    /**
     * Returns the vimboard settings.
     *
     * @return vimboard settings.
     */
    public VimboardSettings get() {
        return vimboardSettings;
    }

    /**
     * Returns board settings for all boards.
     *
     * @return board settings.
     */
    public VimboardBoardSettings getAll() {
        return vimboardSettings.getAll();
    }

    /**
     * Returns board settings by the board uri.
     *
     * @param boardUri the board uri.
     * @return board settings.
     */
    public VimboardBoardSettings getCustom(String boardUri) {
        final VimboardBoardSettings result =
                vimboardSettings.getCustom().get(boardUri);
        return result == null ? vimboardSettings.getAll() : result;
    }

    private VimboardBoardSettings buildBoardSettings(
            VimboardBoardProperties p, String boardUri) {
        final VimboardBoardSettings a = (boardUri == null
                ? null
                : vimboardSettings.getAll());

        final SettingsBuilder sb = new SettingsBuilder(boardUri, "", p, a) {

            @Override
            protected VimboardBoardSettings createSettings() {
                return new VimboardBoardSettings();
            }
        };

        // Settings without dependencies

        sb.put("additionalJavascript", new String[] {
                "js/jquery.min.js",
                "js/inline-expanding.js"
        });
        sb.put("additionalJavascriptCompile", false);
        sb.put("allowSubtitleHtml", false);
        sb.put("banAppeals", false);
        sb.put("boardAbbreviation", "/{uri}/");
        sb.put("boardPath", "{uri}/");
        sb.put("boardlistWrapBracket", false);
        sb.put("boards", null, this::convertBoards);
        sb.put("countryFlagsCondensed", true);
        sb.put("countryFlagsCondensedCss", "static/flags/flags.css");
        sb.put("debug", false);
        sb.put("fileIndex", "index.html");
        final String fileScript = (String) sb.put("fileScript", "main.js");
        sb.put("fontAwesome", true);
        sb.put("fontAwesomeCss", "stylesheets/font-awesome/css/font-awesome.min.css");
        sb.put("metaKeywords", null);
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
        sb.put("version", VimboardVersion.get());

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

        return (VimboardBoardSettings) sb.build();
    }

    private VimboardModSettings buildModSettings(
            VimboardModProperties p, String boardUri) {
        final VimboardModSettings a = (boardUri == null
                ? null
                : vimboardSettings.getAll().getMod());

        final SettingsBuilder sb = new SettingsBuilder(boardUri, "mod", p, a) {

                    @Override
                    protected VimboardModSettings createSettings() {
                        return new VimboardModSettings();
                    }
                };

        // Settings without dependencies

        sb.put("dashboardLinks", new HashMap<String, String>());
        sb.put("noticeboardDashboard", 5L);
        // Mod permissions
        sb.put("changePassword", Group.JANITOR);
        sb.put("debugSql", Group.DISABLED);
        sb.put("editConfig", Group.ADMIN);
        sb.put("editPages", Group.MOD);
        sb.put("manageboards", Group.ADMIN);
        sb.put("manageusers", Group.MOD);
        sb.put("modlog", Group.ADMIN);
        sb.put("newboard", Group.ADMIN);
        sb.put("noticeboard", Group.JANITOR);
        sb.put("recent", Group.MOD);
        sb.put("reports", Group.JANITOR);
        sb.put("search", Group.JANITOR);
        sb.put("showIp", Group.MOD);
        sb.put("themes", Group.ADMIN);
        sb.put("viewBanAppeals", Group.MOD);
        sb.put("viewBanlist", Group.MOD);
        sb.put("viewNotes", Group.JANITOR);

        // Settings with dependencies

        return (VimboardModSettings) sb.build();
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

    private Object convertMod(SettingsBuilder sb, Object value) {
        final VimboardModProperties p = (VimboardModProperties) value;
        return buildModSettings(p, sb.boardUri);
    }

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
