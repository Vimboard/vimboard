package com.github.vimboard.config;

import javax.validation.ValidationException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class SettingsBean {

    private static final String INVALID_BOARDS = "Invalid '%s' property value";

    private final VimboardSettings vimboardSettings;

    public SettingsBean(VimboardProperties vimboardProperties) {
        vimboardSettings = new VimboardSettings();

        final String www = vimboardProperties.getWww();
        vimboardSettings.setWww(www == null || www.isEmpty()
                ? "/var/www/vimboard/public/" : www);

        vimboardSettings.setAll(buildBoardSettings(
                vimboardProperties, null));

        Map<String, VimboardBoardSettings> customSettings = new HashMap<>();
        if (vimboardProperties.getCustom() != null) {
            for (String boardUri : vimboardProperties.getCustom().keySet()) {
                customSettings.put(boardUri, buildBoardSettings(
                        vimboardProperties, boardUri));
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
            VimboardProperties vimboardProperties, String boardUri) {
        VimboardBoardProperties p = (boardUri == null
                ? vimboardProperties.getAll()
                : (vimboardProperties.getCustom() == null
                        ? null
                        : vimboardProperties.getCustom().get(boardUri)));

        VimboardBoardSettings all = vimboardSettings.getAll();
        VimboardBoardSettings s = new VimboardBoardSettings();

        if (p == null || p.getBoardAbbreviation() == null) {
            s.setBoardAbbreviation(boardUri == null
                    ? "/{uri}/"
                    : all.getBoardAbbreviation());
        } else {
            s.setBoardAbbreviation(p.getBoardAbbreviation());
        }

        if (p == null || p.getBoardPath() == null) {
            s.setBoardPath(boardUri == null
                    ? "{uri}/"
                    : all.getBoardPath());
        } else {
            s.setBoardPath(p.getBoardPath());
        }

        if (p == null || p.getBoardlistWrapBracket() == null) {
            s.setBoardlistWrapBracket(boardUri == null
                    ? false
                    : all.getBoardlistWrapBracket());
        } else {
            s.setBoardlistWrapBracket(p.getBoardlistWrapBracket());
        }

        if (p == null || p.getBoards() == null) {
            s.setBoards(boardUri == null
                    ? null
                    : all.getBoards());
        } else {
            s.setBoards(convertBoards(boardUri, p.getBoards()));
        }

        if (p == null || p.getDefaultStylesheet() == null) {
            s.setDefaultStylesheet(boardUri == null
                    ? "Yotsuba B"
                    : all.getDefaultStylesheet());
        } else {
            s.setDefaultStylesheet(p.getDefaultStylesheet());
        }

        if (p == null || p.getFileIndex() == null) {
            s.setFileIndex(boardUri == null
                    ? "index.html"
                    : all.getFileIndex());
        } else {
            s.setFileIndex(p.getFileIndex());
        }

        if (p == null || p.getRoot() == null) {
            s.setRoot(boardUri == null
                    ? "/"
                    : all.getRoot());
        } else {
            s.setRoot(p.getRoot());
        }

        if (p == null || p.getStylesheets() == null) {
            if (boardUri == null) {
                Map<String, String> stylesheets = new HashMap<>(2);
                stylesheets.put("Yotsuba B", ""); // Default; there is no additional/custom stylesheet for this.
                stylesheets.put("Yotsuba", "yotsuba.css");
//                stylesheets.put("Futaba", "futaba.css");
//                stylesheets.put("Dark", "dark.css");
                s.setStylesheets(stylesheets);
            } else {
                s.setStylesheets(all.getStylesheets());
            }
        } else {
            s.setStylesheets(p.getStylesheets());
        }

        if (p == null || p.getUrlFavicon() == null) {
            s.setUrlFavicon(boardUri == null
                    ? null
                    : all.getUrlFavicon());
        } else {
            s.setUrlFavicon(p.getUrlFavicon());
        }

        // Settings with dependencies

        if (p == null || p.getUriStylesheets() == null) {
            s.setUriStylesheets(boardUri == null
                    ? s.getRoot() + "stylesheets/"
                    : all.getUriStylesheets());
        } else {
            s.setUriStylesheets(p.getUriStylesheets());
        }

        if (p == null || p.getUrlStylesheet() == null) {
            s.setUrlStylesheet(boardUri == null
                    ? s.getUriStylesheets() + "style.css"
                    : all.getUrlStylesheet());
        } else {
            s.setUrlStylesheet(p.getUrlStylesheet());
        }

        return s;
    }

    private Object[] convertBoards(String boardUri, Map boards) {
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
                    Object value = map.get(key);
                    if (value instanceof Map) {
                        result[i] = new AbstractMap.SimpleEntry<>(key, convertBoards(boardUri, (Map) value));
                    } else if (value instanceof String) {
                        result[i] = new AbstractMap.SimpleEntry<>(key, value);
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

    private void throwInvalid(String boardUri) {
        throw new ValidationException(String.format(INVALID_BOARDS,
                boardUri == null
                        ? "vimboard.all.boards"
                        : "vimboard.custom." + boardUri + ".boards"));
    }
}
