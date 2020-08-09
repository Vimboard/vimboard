package com.github.vimboard.config.properties;

import com.github.vimboard.domain.GenerationStrategy;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

/**
 * Board settings mapping to Spring Boot application properties.
 */
public class VimboardBoardProperties {

    //------------------------------------------------------------------------
    // General/misc settings
    //------------------------------------------------------------------------

    /**
     * Shows some extra information at the bottom of pages. Good for
     * development/debugging.
     */
    private Boolean debug;

    /**
     * The HTTP status code to use when redirecting. Can be either 303
     * "See Other" or 302 "Found". (303 is more correct but both should work.)
     * There is really no reason for you to ever need to change this.
     * http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
     */
    private Short redirectHttp;

    //------------------------------------------------------------------------
    // Database settings
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Cache, lock and queue settings
    //------------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Cookie settings
    //------------------------------------------------------------------------

    @NestedConfigurationProperty
    private VimboardCookiesProperties cookies;

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

    /**
     * Automatically remove unnecessary whitespace when compiling HTML files
     * from templates.
     */
    private Boolean minifyHtml;

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

    @NestedConfigurationProperty
    private VimboardDirProperties dir;

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

    /**
     * Try not to build pages when we shouldn't have to.
     */
    private Boolean trySmarter;

    //------------------------------------------------------------------------
    // Advanced build
    //------------------------------------------------------------------------

    /**
     * TODO update doc
     *
     * Strategies for file generation. Also known as an "advanced build". If you
     * don't have performance issues, you can safely ignore that part, because
     * it's hard to configure and won't even work on your free webhosting.
     *
     * A strategy is a function, that given the PHP environment and
     * ($fun, $array) variable pair, returns an $action array or false.
     *
     * $fun - a controller function name, see inc/controller.php. This is named
     * after functions, so that we can generate the files in daemon.
     *
     * $array - arguments to be passed
     *
     * $action - action to be taken. It's an array, and the first element of it
     * is one of the following:
     *  * "immediate" - generate the page immediately
     *  * "defer" - defer page generation to a moment a worker daemon gets to build it (serving a stale
     *              page in the meantime). The remaining arguments are daemon-specific. Daemon isn't
     *              implemented yet :DDDD inb4 while(true) { generate(Queue::Get()) }; (which is probably it).
     *  * "build_on_load" - defer page generation to a moment, when the user actually accesses the page.
     *                      This is a smart_build behaviour. You shouldn't use this one too much, if you
     *                      use it for active boards, the server may choke due to a possible race condition.
     *                      See my blog post: https://engine.vichan.net/blog/res/2.html
     * TODO update docs in GenerationAction
     *
     * So, let's assume we want to build a thread 1324 on board /b/, because a
     * new post appeared there. We try the first strategy, giving it arguments:
     * 'sb_thread', array('b', 1324). The strategy will now return a value
     * $action, denoting an action to do. If $action is false, we try another
     * strategy.
     *
     * As I said, configuration isn't easy.
     *
     * 1. chmod 0777 directories: tmp/locks/ and tmp/queue/.
     * 2. serve 403 and 404 requests to go thru smart_build.php
     *    for nginx, this blog post contains config snippets:
     *    https://engine.vichan.net/blog/res/2.html
     * 3. disable indexes in your webserver
     * 4. launch any number of daemons (eg. twice your number of threads?) using
     *    the command:
     *    $ tools/worker.php
     *    You don't need to do that step if you are not going to use the "defer"
     *    option.
     * 5. enable smart_build_helper (see below)
     * 6. edit the strategies (see inc/functions.php for the builtin ones). You
     *    can use lambdas. I will test various ones and include one that works
     *    best for me.
     */
    private GenerationStrategy[] generationStrategies;

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

    @NestedConfigurationProperty
    private VimboardApiProperties api;

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

    /*
     * Regex for board URIs. Don't add "`" character or any Unicode that MySQL
     * can't handle. 58 characters is the absolute maximum, because MySQL cannot
     * handle table names greater than 64 characters.
     */
    private String boardRegex;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    /**
     * Getter for {@link #debug}.
     *
     * @return field value.
     */
    public Boolean getDebug() {
        return debug;
    }

    /**
     * Setter for {@link #debug}.
     *
     * @param debug new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setDebug(Boolean debug) {
        this.debug = debug;
        return this;
    }

    /**
     * Getter for {@link #redirectHttp}.
     *
     * @return field value.
     */
    public Short getRedirectHttp() {
        return redirectHttp;
    }

    /**
     * Setter for {@link #redirectHttp}.
     *
     * @param redirectHttp new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setRedirectHttp(Short redirectHttp) {
        this.redirectHttp = redirectHttp;
        return this;
    }

    /**
     * Getter for {@link #cookies}.
     *
     * @return field value.
     */
    public VimboardCookiesProperties getCookies() {
        return cookies;
    }

    /**
     * Setter for {@link #cookies}.
     *
     * @param cookies new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setCookies(VimboardCookiesProperties cookies) {
        this.cookies = cookies;
        return this;
    }

    /**
     * Getter for {@link #recaptcha}.
     *
     * @return field value.
     */
    public Boolean getRecaptcha() {
        return recaptcha;
    }

    /**
     * Setter for {@link #recaptcha}.
     *
     * @param recaptcha new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setRecaptcha(Boolean recaptcha) {
        this.recaptcha = recaptcha;
        return this;
    }

    /**
     * Getter for {@link #countryFlagsCondensed}.
     *
     * @return field value.
     */
    public Boolean getCountryFlagsCondensed() {
        return countryFlagsCondensed;
    }

    /**
     * Setter for {@link #countryFlagsCondensed}.
     *
     * @param countryFlagsCondensed new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setCountryFlagsCondensed(Boolean countryFlagsCondensed) {
        this.countryFlagsCondensed = countryFlagsCondensed;
        return this;
    }

    /**
     * Getter for {@link #countryFlagsCondensedCss}.
     *
     * @return field value.
     */
    public String getCountryFlagsCondensedCss() {
        return countryFlagsCondensedCss;
    }

    /**
     * Setter for {@link #countryFlagsCondensedCss}.
     *
     * @param countryFlagsCondensedCss new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setCountryFlagsCondensedCss(String countryFlagsCondensedCss) {
        this.countryFlagsCondensedCss = countryFlagsCondensedCss;
        return this;
    }

    /**
     * Getter for {@link #banAppeals}.
     *
     * @return field value.
     */
    public Boolean getBanAppeals() {
        return banAppeals;
    }

    /**
     * Setter for {@link #banAppeals}.
     *
     * @param banAppeals new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setBanAppeals(Boolean banAppeals) {
        this.banAppeals = banAppeals;
        return this;
    }

    /**
     * Getter for {@link #boardAbbreviation}.
     *
     * @return field value.
     */
    public String getBoardAbbreviation() {
        return boardAbbreviation;
    }

    /**
     * Setter for {@link #boardAbbreviation}.
     *
     * @param boardAbbreviation new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setBoardAbbreviation(String boardAbbreviation) {
        this.boardAbbreviation = boardAbbreviation;
        return this;
    }

    /**
     * Getter for {@link #allowSubtitleHtml}.
     *
     * @return field value.
     */
    public Boolean getAllowSubtitleHtml() {
        return allowSubtitleHtml;
    }

    /**
     * Setter for {@link #allowSubtitleHtml}.
     *
     * @param allowSubtitleHtml new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setAllowSubtitleHtml(Boolean allowSubtitleHtml) {
        this.allowSubtitleHtml = allowSubtitleHtml;
        return this;
    }

    /**
     * Getter for {@link #postDate}.
     *
     * @return field value.
     */
    public String getPostDate() {
        return postDate;
    }

    /**
     * Setter for {@link #postDate}.
     *
     * @param postDate new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setPostDate(String postDate) {
        this.postDate = postDate;
        return this;
    }

    /**
     * Getter for {@link #stylesheets}.
     *
     * @return field value.
     */
    public Map<String, String> getStylesheets() {
        return stylesheets;
    }

    /**
     * Setter for {@link #stylesheets}.
     *
     * @param stylesheets new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setStylesheets(Map<String, String> stylesheets) {
        this.stylesheets = stylesheets;
        return this;
    }

    /**
     * Getter for {@link #uriStylesheets}.
     *
     * @return field value.
     */
    public String getUriStylesheets() {
        return uriStylesheets;
    }

    /**
     * Setter for {@link #uriStylesheets}.
     *
     * @param uriStylesheets new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setUriStylesheets(String uriStylesheets) {
        this.uriStylesheets = uriStylesheets;
        return this;
    }

    /**
     * Getter for {@link #defaultStylesheet}.
     *
     * @return field value.
     */
    public String getDefaultStylesheet() {
        return defaultStylesheet;
    }

    /**
     * Setter for {@link #defaultStylesheet}.
     *
     * @param defaultStylesheet new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setDefaultStylesheet(String defaultStylesheet) {
        this.defaultStylesheet = defaultStylesheet;
        return this;
    }

    /**
     * Getter for {@link #fontAwesome}.
     *
     * @return field value.
     */
    public Boolean getFontAwesome() {
        return fontAwesome;
    }

    /**
     * Setter for {@link #fontAwesome}.
     *
     * @param fontAwesome new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setFontAwesome(Boolean fontAwesome) {
        this.fontAwesome = fontAwesome;
        return this;
    }

    /**
     * Getter for {@link #fontAwesomeCss}.
     *
     * @return field value.
     */
    public String getFontAwesomeCss() {
        return fontAwesomeCss;
    }

    /**
     * Setter for {@link #fontAwesomeCss}.
     *
     * @param fontAwesomeCss new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setFontAwesomeCss(String fontAwesomeCss) {
        this.fontAwesomeCss = fontAwesomeCss;
        return this;
    }

    /**
     * Getter for {@link #boards}.
     *
     * @return field value.
     */
    public Map getBoards() {
        return boards;
    }

    /**
     * Setter for {@link #boards}.
     *
     * @param boards new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setBoards(Map boards) {
        this.boards = boards;
        return this;
    }

    /**
     * Getter for {@link #boardlistWrapBracket}.
     *
     * @return field value.
     */
    public Boolean getBoardlistWrapBracket() {
        return boardlistWrapBracket;
    }

    /**
     * Setter for {@link #boardlistWrapBracket}.
     *
     * @param boardlistWrapBracket new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setBoardlistWrapBracket(Boolean boardlistWrapBracket) {
        this.boardlistWrapBracket = boardlistWrapBracket;
        return this;
    }

    /**
     * Getter for {@link #minifyHtml}.
     *
     * @return field value.
     */
    public Boolean getMinifyHtml() {
        return minifyHtml;
    }

    /**
     * Setter for {@link #minifyHtml}.
     *
     * @param minifyHtml new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setMinifyHtml(Boolean minifyHtml) {
        this.minifyHtml = minifyHtml;
        return this;
    }

    /**
     * Getter for {@link #additionalJavascript}.
     *
     * @return field value.
     */
    public String[] getAdditionalJavascript() {
        return additionalJavascript;
    }

    /**
     * Setter for {@link #additionalJavascript}.
     *
     * @param additionalJavascript new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setAdditionalJavascript(String[] additionalJavascript) {
        this.additionalJavascript = additionalJavascript;
        return this;
    }

    /**
     * Getter for {@link #additionalJavascriptUrl}.
     *
     * @return field value.
     */
    public String getAdditionalJavascriptUrl() {
        return additionalJavascriptUrl;
    }

    /**
     * Setter for {@link #additionalJavascriptUrl}.
     *
     * @param additionalJavascriptUrl new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setAdditionalJavascriptUrl(String additionalJavascriptUrl) {
        this.additionalJavascriptUrl = additionalJavascriptUrl;
        return this;
    }

    /**
     * Getter for {@link #additionalJavascriptCompile}.
     *
     * @return field value.
     */
    public Boolean getAdditionalJavascriptCompile() {
        return additionalJavascriptCompile;
    }

    /**
     * Setter for {@link #additionalJavascriptCompile}.
     *
     * @param additionalJavascriptCompile new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setAdditionalJavascriptCompile(Boolean additionalJavascriptCompile) {
        this.additionalJavascriptCompile = additionalJavascriptCompile;
        return this;
    }

    /**
     * Getter for {@link #root}.
     *
     * @return field value.
     */
    public String getRoot() {
        return root;
    }

    /**
     * Setter for {@link #root}.
     *
     * @param root new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setRoot(String root) {
        this.root = root;
        return this;
    }

    /**
     * Getter for {@link #fileIndex}.
     *
     * @return field value.
     */
    public String getFileIndex() {
        return fileIndex;
    }

    /**
     * Setter for {@link #fileIndex}.
     *
     * @param fileIndex new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setFileIndex(String fileIndex) {
        this.fileIndex = fileIndex;
        return this;
    }

    /**
     * Getter for {@link #fileScript}.
     *
     * @return field value.
     */
    public String getFileScript() {
        return fileScript;
    }

    /**
     * Setter for {@link #fileScript}.
     *
     * @param fileScript new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setFileScript(String fileScript) {
        this.fileScript = fileScript;
        return this;
    }

    /**
     * Getter for {@link #boardPath}.
     *
     * @return field value.
     */
    public String getBoardPath() {
        return boardPath;
    }

    /**
     * Setter for {@link #boardPath}.
     *
     * @param boardPath new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setBoardPath(String boardPath) {
        this.boardPath = boardPath;
        return this;
    }

    /**
     * Getter for {@link #dir}.
     *
     * @return field value.
     */
    public VimboardDirProperties getDir() {
        return dir;
    }

    /**
     * Setter for {@link #dir}.
     *
     * @param dir new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setDir(VimboardDirProperties dir) {
        this.dir = dir;
        return this;
    }

    /**
     * Getter for {@link #urlStylesheet}.
     *
     * @return field value.
     */
    public String getUrlStylesheet() {
        return urlStylesheet;
    }

    /**
     * Setter for {@link #urlStylesheet}.
     *
     * @param urlStylesheet new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setUrlStylesheet(String urlStylesheet) {
        this.urlStylesheet = urlStylesheet;
        return this;
    }

    /**
     * Getter for {@link #urlJavascript}.
     *
     * @return field value.
     */
    public String getUrlJavascript() {
        return urlJavascript;
    }

    /**
     * Setter for {@link #urlJavascript}.
     *
     * @param urlJavascript new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setUrlJavascript(String urlJavascript) {
        this.urlJavascript = urlJavascript;
        return this;
    }

    /**
     * Getter for {@link #urlFavicon}.
     *
     * @return field value.
     */
    public String getUrlFavicon() {
        return urlFavicon;
    }

    /**
     * Setter for {@link #urlFavicon}.
     *
     * @param urlFavicon new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setUrlFavicon(String urlFavicon) {
        this.urlFavicon = urlFavicon;
        return this;
    }

    /**
     * Getter for {@link #trySmarter}.
     *
     * @return field value.
     */
    public Boolean getTrySmarter() {
        return trySmarter;
    }

    /**
     * Setter for {@link #trySmarter}.
     *
     * @param trySmarter new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setTrySmarter(Boolean trySmarter) {
        this.trySmarter = trySmarter;
        return this;
    }

    /**
     * Getter for {@link #generationStrategies}.
     *
     * @return field value.
     */
    public GenerationStrategy[] getGenerationStrategies() {
        return generationStrategies;
    }

    /**
     * Setter for {@link #generationStrategies}.
     *
     * @param generationStrategies new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setGenerationStrategies(GenerationStrategy[] generationStrategies) {
        this.generationStrategies = generationStrategies;
        return this;
    }

    /**
     * Getter for {@link #mod}.
     *
     * @return field value.
     */
    public VimboardModProperties getMod() {
        return mod;
    }

    /**
     * Setter for {@link #mod}.
     *
     * @param mod new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setMod(VimboardModProperties mod) {
        this.mod = mod;
        return this;
    }

    /**
     * Getter for {@link #api}.
     *
     * @return field value.
     */
    public VimboardApiProperties getApi() {
        return api;
    }

    /**
     * Setter for {@link #api}.
     *
     * @param api new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setApi(VimboardApiProperties api) {
        this.api = api;
        return this;
    }

    /**
     * Getter for {@link #metaKeywords}.
     *
     * @return field value.
     */
    public String getMetaKeywords() {
        return metaKeywords;
    }

    /**
     * Setter for {@link #metaKeywords}.
     *
     * @param metaKeywords new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
        return this;
    }

    /**
     * Getter for {@link #boardRegex}.
     *
     * @return field value.
     */
    public String getBoardRegex() {
        return boardRegex;
    }

    /**
     * Setter for {@link #boardRegex}.
     *
     * @param boardRegex new field value.
     * @return {@code this}.
     */
    public VimboardBoardProperties setBoardRegex(String boardRegex) {
        this.boardRegex = boardRegex;
        return this;
    }
}
