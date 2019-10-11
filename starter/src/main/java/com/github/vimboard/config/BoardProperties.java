package com.github.vimboard.config;

import java.util.HashMap;
import java.util.Map;

public class BoardProperties {

    /**
     * The default stylesheet to use.
     */
    private String defaultStylesheet;

    /**
     * Custom stylesheets available for the user to choose.
     * See the "stylesheets/" folder for a list of available
     * stylesheets (or create your own).
     */
    private Map<String, String> stylesheets;

    // Init ------------------------------------------------------------------

    static void init(final BoardProperties b, VimboardProperties v) {

        if (b.defaultStylesheet == null) {
            b.defaultStylesheet = (v == null
                    ? "Yotsuba B"
                    : v.getAllBoards().defaultStylesheet);
        }

        if (b.stylesheets == null) {
            if (v == null) {
                b.stylesheets = new HashMap<>(2);
                b.stylesheets.put("Yotsuba B", ""); // Default; there is no additional/custom stylesheet for this.
                b.stylesheets.put("Yotsuba", "yotsuba.css");
                //b.stylesheets.put("Futaba", "futaba.css");
                //b.stylesheets.put("Dark", "dark.css");
            } else {
                b.stylesheets = v.getAllBoards().stylesheets;
            }
        }
    }

    // Getters and setters ---------------------------------------------------

    public String getDefaultStylesheet() {
        return defaultStylesheet;
    }

    public void setDefaultStylesheet(String defaultStylesheet) {
        this.defaultStylesheet = defaultStylesheet;
    }

    public Map<String, String> getStylesheets() {
        return stylesheets;
    }

    public void setStylesheets(Map<String, String> stylesheets) {
        this.stylesheets = stylesheets;
    }
}
