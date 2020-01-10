package com.github.vimboard.config;

import java.util.Map;

public class VimboardBoardProperties {

    // Post settings ---------------------------------------------------------

    /**
     * Load all country flags from one file.
     */
    private boolean countryFlagsCondensed;

    /**
     * Country flags stylesheet.
     */
    private String countryFlagsCondensedCss;

    // Board settings --------------------------------------------------------

    /**
     * How to display the URI of boards. Usually "/{uri}/" (/b/, /mu/, etc).
     * This doesn't change the URL. Find {@link #boardPath} if you wish to
     * change the URL.
     */
    private String boardAbbreviation;

    // Directory/file settings -----------------------------------------------

    /**
     * Board directory, followed by a forward-slash (/).
     */
    private String boardPath;

    /**
     * Name of index file.
     */
    private String fileIndex;

    /**
     * The root directory, including the trailing slash,
     * Examples: '/', 'http://boards.chan.org/', '/chan/'.
     */
    private String root;

    /*
     * Website favicon.
     */
    private String urlFavicon;

    /*
     * Set custom location for stylesheetsfile. This can be used for load
     * balancing across multiple servers or hostnames.
     */
    private String urlStylesheet;

    // Display settings ------------------------------------------------------

    /**
     * For lack of a better name, “boardlinks” are those sets of navigational
     * links that appear at the top and bottom of board pages. They can be a
     * list of links to boards and/or other pages such as status blogs and
     * social network profiles/pages.
     *
     * "Groups" in the boardlinks are marked with square brackets. Vimboard
     * allows for infinite recursion with groups. Each array in {@link #boards}
     * represents a new square bracket group.
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
     * Use Font-Awesome for displaying lock and pin icons, instead
     * of the images in static/.
     *
     * http://fortawesome.github.io/Font-Awesome/icon/pushpin/
     * http://fortawesome.github.io/Font-Awesome/icon/lock/
     */
    private Boolean fontAwesome;

    /**
     * Font-Awesome stylesheet.
     */
    private String fontAwesomeCss;

    /**
     * Custom stylesheets available for the user to choose.
     * See the "stylesheets/" folder for a list of available
     * stylesheets (or create your own).
     */
    private Map<String, String> stylesheets;

    /*
     * The prefix for each stylesheet URI. Defaults to {@link #root}/stylesheets/
    // $config['uri_stylesheets'] = 'http://static.example.org/stylesheets/';
     */
    private String uriStylesheets;

    // Other/uncategorized ---------------------------------------------------

    /**
     * Meta keywords. It's probably best to include these in
     * per-board configurations.
     */
    private String metaKeywords;

    // Getters and setters ---------------------------------------------------

    public boolean isCountryFlagsCondensed() {
        return countryFlagsCondensed;
    }

    public VimboardBoardProperties setCountryFlagsCondensed(boolean countryFlagsCondensed) {
        this.countryFlagsCondensed = countryFlagsCondensed;
        return this;
    }

    public String getCountryFlagsCondensedCss() {
        return countryFlagsCondensedCss;
    }

    public VimboardBoardProperties setCountryFlagsCondensedCss(String countryFlagsCondensedCss) {
        this.countryFlagsCondensedCss = countryFlagsCondensedCss;
        return this;
    }

    public String getBoardAbbreviation() {
        return boardAbbreviation;
    }

    public VimboardBoardProperties setBoardAbbreviation(String boardAbbreviation) {
        this.boardAbbreviation = boardAbbreviation;
        return this;
    }

    public String getBoardPath() {
        return boardPath;
    }

    public VimboardBoardProperties setBoardPath(String boardPath) {
        this.boardPath = boardPath;
        return this;
    }

    public String getFileIndex() {
        return fileIndex;
    }

    public VimboardBoardProperties setFileIndex(String fileIndex) {
        this.fileIndex = fileIndex;
        return this;
    }

    public String getRoot() {
        return root;
    }

    public VimboardBoardProperties setRoot(String root) {
        this.root = root;
        return this;
    }

    public String getUrlFavicon() {
        return urlFavicon;
    }

    public VimboardBoardProperties setUrlFavicon(String urlFavicon) {
        this.urlFavicon = urlFavicon;
        return this;
    }

    public String getUrlStylesheet() {
        return urlStylesheet;
    }

    public VimboardBoardProperties setUrlStylesheet(String urlStylesheet) {
        this.urlStylesheet = urlStylesheet;
        return this;
    }

    public Map getBoards() {
        return boards;
    }

    public VimboardBoardProperties setBoards(Map boards) {
        this.boards = boards;
        return this;
    }

    public Boolean getBoardlistWrapBracket() {
        return boardlistWrapBracket;
    }

    public VimboardBoardProperties setBoardlistWrapBracket(Boolean boardlistWrapBracket) {
        this.boardlistWrapBracket = boardlistWrapBracket;
        return this;
    }

    public String getDefaultStylesheet() {
        return defaultStylesheet;
    }

    public VimboardBoardProperties setDefaultStylesheet(String defaultStylesheet) {
        this.defaultStylesheet = defaultStylesheet;
        return this;
    }

    public Boolean getFontAwesome() {
        return fontAwesome;
    }

    public VimboardBoardProperties setFontAwesome(Boolean fontAwesome) {
        this.fontAwesome = fontAwesome;
        return this;
    }

    public String getFontAwesomeCss() {
        return fontAwesomeCss;
    }

    public VimboardBoardProperties setFontAwesomeCss(String fontAwesomeCss) {
        this.fontAwesomeCss = fontAwesomeCss;
        return this;
    }

    public Map<String, String> getStylesheets() {
        return stylesheets;
    }

    public VimboardBoardProperties setStylesheets(Map<String, String> stylesheets) {
        this.stylesheets = stylesheets;
        return this;
    }

    public String getUriStylesheets() {
        return uriStylesheets;
    }

    public VimboardBoardProperties setUriStylesheets(String uriStylesheets) {
        this.uriStylesheets = uriStylesheets;
        return this;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public VimboardBoardProperties setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
        return this;
    }
}
