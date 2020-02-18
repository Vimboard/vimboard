package com.github.vimboard.config;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

public class VimboardBoardProperties {

    //------------------------------------------------------------------------
    // General/misc settings
    //------------------------------------------------------------------------

    /**
     * Shows some extra information at the bottom of pages. Good for
     * development/debugging.
     */
    private Boolean debug;

    //------------------------------------------------------------------------
    // Database settings
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Cache, lock and queue settings
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Cookie settings
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Flood/spam settings
    //------------------------------------------------------------------------

    /**
     * Enable reCaptcha to make spam even harder. Rarely necessary.
     */
    private Boolean recaptcha;

    //------------------------------------------------------------------------
    // Post settings
    //------------------------------------------------------------------------

    /**
     * Load all country flags from one file.
     */
    private Boolean countryFlagsCondensed;

    /**
     * Country flags stylesheet.
     */
    private String countryFlagsCondensedCss;

    //------------------------------------------------------------------------
    // Ban settings
    //------------------------------------------------------------------------

    /**
     * Allow users to appeal bans through Vimboard.
     */
    private Boolean banAppeals;

    //------------------------------------------------------------------------
    // Markup settings
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Image settings
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Board settings
    //------------------------------------------------------------------------

    /**
     * How to display the URI of boards. Usually "/{uri}/" (/b/, /mu/, etc).
     * This doesn't change the URL. Find {@link #boardPath} if you wish to
     * change the URL.
     */
    private String boardAbbreviation;

    /**
     * Allow unfiltered HTML in board subtitle.
     * This is useful for placing icons and links.
     */
    private Boolean allowSubtitleHtml;

    //------------------------------------------------------------------------
    // Display settings
    //------------------------------------------------------------------------

    /**
     * The format string passed to string built-in for displaying dates.
     * https://freemarker.apache.org/docs/ref_builtins_date.html
     */
    private String postDate;

    /**
     * Custom stylesheets available for the user to choose.
     * See the "stylesheets/" folder for a list of available
     * stylesheets (or create your own).
     */
    private Map<String, String> stylesheets;

    /**
     * The prefix for each stylesheet URI. Defaults to {@link #root}/stylesheets/
     */
    private String uriStylesheets;

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

    //------------------------------------------------------------------------
    // Javascript
    //------------------------------------------------------------------------

    /**
     * Additional Javascript files to include on board index and thread pages.
     * See "js/" for available scripts.
     */
    private String[] additionalJavascript;

    /**
     * Where these script files are located on the web. Defaults to {@link #root}.
     */
    private String additionalJavascriptUrl;

    /**
     * Compile all additional scripts into one file ({@link #fileScript})
     * instead of including them seperately.
     */
    private Boolean additionalJavascriptCompile;

    //------------------------------------------------------------------------
    // Video embedding
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Error messages
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Directory/file settings
    //------------------------------------------------------------------------

    /**
     * The root directory, including the trailing slash,
     * Examples: '/', 'http://boards.chan.org/', '/chan/'.
     */
    private String root;

    /**
     * Name of the index page file.
     */
    private String fileIndex;

    /**
     * Name of the main script file.
     */
    private String fileScript;

    /**
     * Board directory, followed by a forward-slash (/).
     */
    private String boardPath;

    /*
     * Set custom location for stylesheets file. This can be used for load
     * balancing across multiple servers or hostnames.
     */
    private String urlStylesheet;

    /**
     * Set custom location for the main script file. This can be used for load
     * balancing across multiple servers or hostnames.
     */
    private String urlJavascript;

    /*
     * Website favicon.
     */
    private String urlFavicon;

    //------------------------------------------------------------------------
    // Advanced build
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Mod settings
    //------------------------------------------------------------------------

    @NestedConfigurationProperty
    private VimboardModProperties mod;

    //------------------------------------------------------------------------
    // Mod permissions
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Public pages
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Events (PHP 5.3.0+)
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // API settings
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // NNTPChan settings
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Other/uncategorized
    //------------------------------------------------------------------------

    /**
     * Meta keywords. It's probably best to include these in
     * per-board configurations.
     */
    private String metaKeywords;

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public Boolean getDebug() {
        return debug;
    }

    public VimboardBoardProperties setDebug(Boolean debug) {
        this.debug = debug;
        return this;
    }

    public Boolean getRecaptcha() {
        return recaptcha;
    }

    public VimboardBoardProperties setRecaptcha(Boolean recaptcha) {
        this.recaptcha = recaptcha;
        return this;
    }

    public Boolean getCountryFlagsCondensed() {
        return countryFlagsCondensed;
    }

    public VimboardBoardProperties setCountryFlagsCondensed(Boolean countryFlagsCondensed) {
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

    public Boolean getBanAppeals() {
        return banAppeals;
    }

    public VimboardBoardProperties setBanAppeals(Boolean banAppeals) {
        this.banAppeals = banAppeals;
        return this;
    }

    public String getBoardAbbreviation() {
        return boardAbbreviation;
    }

    public VimboardBoardProperties setBoardAbbreviation(String boardAbbreviation) {
        this.boardAbbreviation = boardAbbreviation;
        return this;
    }

    public Boolean getAllowSubtitleHtml() {
        return allowSubtitleHtml;
    }

    public VimboardBoardProperties setAllowSubtitleHtml(Boolean allowSubtitleHtml) {
        this.allowSubtitleHtml = allowSubtitleHtml;
        return this;
    }

    public String getPostDate() {
        return postDate;
    }

    public VimboardBoardProperties setPostDate(String postDate) {
        this.postDate = postDate;
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

    public String[] getAdditionalJavascript() {
        return additionalJavascript;
    }

    public VimboardBoardProperties setAdditionalJavascript(String[] additionalJavascript) {
        this.additionalJavascript = additionalJavascript;
        return this;
    }

    public String getAdditionalJavascriptUrl() {
        return additionalJavascriptUrl;
    }

    public VimboardBoardProperties setAdditionalJavascriptUrl(String additionalJavascriptUrl) {
        this.additionalJavascriptUrl = additionalJavascriptUrl;
        return this;
    }

    public Boolean getAdditionalJavascriptCompile() {
        return additionalJavascriptCompile;
    }

    public VimboardBoardProperties setAdditionalJavascriptCompile(Boolean additionalJavascriptCompile) {
        this.additionalJavascriptCompile = additionalJavascriptCompile;
        return this;
    }

    public String getRoot() {
        return root;
    }

    public VimboardBoardProperties setRoot(String root) {
        this.root = root;
        return this;
    }

    public String getFileIndex() {
        return fileIndex;
    }

    public VimboardBoardProperties setFileIndex(String fileIndex) {
        this.fileIndex = fileIndex;
        return this;
    }

    public String getFileScript() {
        return fileScript;
    }

    public VimboardBoardProperties setFileScript(String fileScript) {
        this.fileScript = fileScript;
        return this;
    }

    public String getBoardPath() {
        return boardPath;
    }

    public VimboardBoardProperties setBoardPath(String boardPath) {
        this.boardPath = boardPath;
        return this;
    }

    public String getUrlStylesheet() {
        return urlStylesheet;
    }

    public VimboardBoardProperties setUrlStylesheet(String urlStylesheet) {
        this.urlStylesheet = urlStylesheet;
        return this;
    }

    public String getUrlJavascript() {
        return urlJavascript;
    }

    public VimboardBoardProperties setUrlJavascript(String urlJavascript) {
        this.urlJavascript = urlJavascript;
        return this;
    }

    public String getUrlFavicon() {
        return urlFavicon;
    }

    public VimboardBoardProperties setUrlFavicon(String urlFavicon) {
        this.urlFavicon = urlFavicon;
        return this;
    }

    public VimboardModProperties getMod() {
        return mod;
    }

    public VimboardBoardProperties setMod(VimboardModProperties mod) {
        this.mod = mod;
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
