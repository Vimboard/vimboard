package com.github.vimboard.config;

import java.util.HashMap;
import java.util.Map;

public class BoardProperties {

    // Directory/file settings -----------------------------------------------

    /**
     * Name of index file.
     */
    private String fileIndex;

    /**
     * The root directory, including the trailing slash,
     * Examples: '/', 'http://boards.chan.org/', '/chan/'.
     */
    private String root;

    // Display settings ------------------------------------------------------

    /**
     * For lack of a better name, “boardlinks” are those sets of navigational
     * links that appear at the top and bottom of board pages. They can be a
     * list of links to boards and/or other pages such as status blogs and
     * social network profiles/pages.
     *
     * "Groups" in the boardlinks are marked with square brackets. Vimboard
     * allows for infinite recursion with groups. Each array in boards
     * porperty represents a new square bracket group.
     */
    private Map boards;

    /**
     * Whether or not to put brackets around the whole board list.
     */
    private Boolean boardlistWrapBracket;

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

        // Directory/file settings -------------------------------------------

        if (b.fileIndex == null) {
            b.fileIndex = (v == null
                    ? "index.html"
                    : v.getAllBoards().fileIndex);
        }

        if (b.root == null) {
            b.root = (v == null
                    ? "/"
                    : v.getAllBoards().root);
        }

        // Display settings --------------------------------------------------

        if (b.boards == null) {
            b.boards = (v == null
                    ? null
                    : v.getAllBoards().boards);
        }

        if (b.boardlistWrapBracket == null) {
            b.boardlistWrapBracket = (v == null
                    ? false
                    : v.getAllBoards().boardlistWrapBracket);
        }

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

    public String getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(String fileIndex) {
        this.fileIndex = fileIndex;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public Map getBoards() {
        return boards;
    }

    public void setBoards(Map boards) {
        this.boards = boards;
    }

    public Boolean getBoardlistWrapBracket() {
        return boardlistWrapBracket;
    }

    public void setBoardlistWrapBracket(Boolean boardlistWrapBracket) {
        this.boardlistWrapBracket = boardlistWrapBracket;
    }

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
