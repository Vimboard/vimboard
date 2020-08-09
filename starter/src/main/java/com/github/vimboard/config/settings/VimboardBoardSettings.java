package com.github.vimboard.config.settings;

import com.github.vimboard.config.properties.VimboardBoardProperties;
import com.github.vimboard.domain.GenerationStrategy;

import java.util.Map;

/**
 * Board settings.
 */
public class VimboardBoardSettings {

    // General/misc settings

    /** {@link VimboardBoardProperties#getDebug()} */
    private Boolean debug;
    /** {@link VimboardBoardProperties#getRedirectHttp()} */
    private Short redirectHttp;

    // Database settings

    // Cache, lock and queue settings

    // Cookie settings

    /** {@link VimboardBoardProperties#getCookies()} */
    private VimboardCookiesSettings cookies;

    // Flood/spam settings

    /** {@link VimboardBoardProperties#getRecaptcha()} */
    private Boolean recaptcha;

    // Post settings

    /** {@link VimboardBoardProperties#getCountryFlagsCondensed()} */
    private Boolean countryFlagsCondensed;
    /** {@link VimboardBoardProperties#getCountryFlagsCondensedCss()} */
    private String countryFlagsCondensedCss;

    // Ban settings

    /** {@link VimboardBoardProperties#getBanAppeals()} */
    private Boolean banAppeals;

    // Markup settings

    // Image settings

    // Board settings

    /** {@link VimboardBoardProperties#getBoardAbbreviation()} */
    private String boardAbbreviation;
    /** {@link VimboardBoardProperties#getAllowSubtitleHtml()} */
    private Boolean allowSubtitleHtml;

    // Display settings

    /** {@link VimboardBoardProperties#getPostDate()} */
    private String postDate;
    /** {@link VimboardBoardProperties#getStylesheets()} */
    private Map<String, String> stylesheets;
    /** {@link VimboardBoardProperties#getUriStylesheets()} */
    private String uriStylesheets;
    /** {@link VimboardBoardProperties#getDefaultStylesheet()} */
    private String[] defaultStylesheet;
    /** {@link VimboardBoardProperties#getFontAwesome()} */
    private Boolean fontAwesome;
    /** {@link VimboardBoardProperties#getFontAwesomeCss()} */
    private String fontAwesomeCss;
    /** {@link VimboardBoardProperties#getBoards()} */
    private Object[] boards;
    /** {@link VimboardBoardProperties#getBoardlistWrapBracket()} */
    private Boolean boardlistWrapBracket;
    /** {@link VimboardBoardProperties#getMinifyHtml()} */
    private Boolean minifyHtml;

    // Javascript

    /** {@link VimboardBoardProperties#getAdditionalJavascript()} */
    private String[] additionalJavascript;
    /** {@link VimboardBoardProperties#getAdditionalJavascriptUrl()} */
    private String additionalJavascriptUrl;
    /** {@link VimboardBoardProperties#getAdditionalJavascriptCompile()} */
    private Boolean additionalJavascriptCompile;

    // Video embedding

    // Error messages

    // Directory/file settings

    /** {@link VimboardBoardProperties#getRoot()} */
    private String root;
    /** {@link VimboardBoardProperties#getFileIndex()} */
    private String fileIndex;
    /** {@link VimboardBoardProperties#getFileScript()} */
    private String fileScript;
    /** {@link VimboardBoardProperties#getBoardPath()} */
    private String boardPath;
    /** {@link VimboardBoardProperties#getDir()} */
    private VimboardDirSettings dir;
    /** {@link VimboardBoardProperties#getUrlStylesheet()} */
    private String urlStylesheet;
    /** {@link VimboardBoardProperties#getUrlJavascript()} */
    private String urlJavascript;
    /** {@link VimboardBoardProperties#getUrlFavicon()} */
    private String urlFavicon;
    /** {@link VimboardBoardProperties#getTrySmarter()} */
    private Boolean trySmarter;

    // Advanced build

    /** {@link VimboardBoardProperties#getGenerationStrategies()} */
    private GenerationStrategy[] generationStrategies;

    // Mod settings

    /** {@link VimboardBoardProperties#getMod()} */
    private VimboardModSettings mod;

    // Mod permissions

    // Create pages

    // Public pages

    // Events (PHP 5.3.0+)

    // API settings

    /** {@link VimboardBoardProperties#getApi()} */
    private VimboardApiSettings api;

    // NNTPChan settings

    // Other/uncategorized

    /** {@link VimboardBoardProperties#getMetaKeywords()} */
    private String metaKeywords;
    /** {@link VimboardBoardProperties#getBoardRegex()} */
    private String boardRegex;

    //------------------------------------------------------------------------
    // Getters and setters
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
     */
    public void setDebug(Boolean debug) {
        this.debug = debug;
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
     */
    public void setRedirectHttp(Short redirectHttp) {
        this.redirectHttp = redirectHttp;
    }

    /**
     * Getter for {@link #cookies}.
     *
     * @return field value.
     */
    public VimboardCookiesSettings getCookies() {
        return cookies;
    }

    /**
     * Setter for {@link #cookies}.
     *
     * @param cookies new field value.
     */
    public void setCookies(VimboardCookiesSettings cookies) {
        this.cookies = cookies;
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
     */
    public void setRecaptcha(Boolean recaptcha) {
        this.recaptcha = recaptcha;
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
     */
    public void setCountryFlagsCondensed(Boolean countryFlagsCondensed) {
        this.countryFlagsCondensed = countryFlagsCondensed;
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
     */
    public void setCountryFlagsCondensedCss(String countryFlagsCondensedCss) {
        this.countryFlagsCondensedCss = countryFlagsCondensedCss;
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
     */
    public void setBanAppeals(Boolean banAppeals) {
        this.banAppeals = banAppeals;
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
     */
    public void setBoardAbbreviation(String boardAbbreviation) {
        this.boardAbbreviation = boardAbbreviation;
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
     */
    public void setAllowSubtitleHtml(Boolean allowSubtitleHtml) {
        this.allowSubtitleHtml = allowSubtitleHtml;
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
     */
    public void setPostDate(String postDate) {
        this.postDate = postDate;
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
     */
    public void setStylesheets(Map<String, String> stylesheets) {
        this.stylesheets = stylesheets;
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
     */
    public void setUriStylesheets(String uriStylesheets) {
        this.uriStylesheets = uriStylesheets;
    }

    /**
     * Getter for {@link #defaultStylesheet}.
     *
     * @return field value.
     */
    public String[] getDefaultStylesheet() {
        return defaultStylesheet;
    }

    /**
     * Setter for {@link #defaultStylesheet}.
     *
     * @param defaultStylesheet new field value.
     */
    public void setDefaultStylesheet(String[] defaultStylesheet) {
        this.defaultStylesheet = defaultStylesheet;
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
     */
    public void setFontAwesome(Boolean fontAwesome) {
        this.fontAwesome = fontAwesome;
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
     */
    public void setFontAwesomeCss(String fontAwesomeCss) {
        this.fontAwesomeCss = fontAwesomeCss;
    }

    /**
     * Getter for {@link #boards}.
     *
     * @return field value.
     */
    public Object[] getBoards() {
        return boards;
    }

    /**
     * Setter for {@link #boards}.
     *
     * @param boards new field value.
     */
    public void setBoards(Object[] boards) {
        this.boards = boards;
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
     */
    public void setBoardlistWrapBracket(Boolean boardlistWrapBracket) {
        this.boardlistWrapBracket = boardlistWrapBracket;
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
     */
    public void setMinifyHtml(Boolean minifyHtml) {
        this.minifyHtml = minifyHtml;
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
     */
    public void setAdditionalJavascript(String[] additionalJavascript) {
        this.additionalJavascript = additionalJavascript;
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
     */
    public void setAdditionalJavascriptUrl(String additionalJavascriptUrl) {
        this.additionalJavascriptUrl = additionalJavascriptUrl;
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
     */
    public void setAdditionalJavascriptCompile(Boolean additionalJavascriptCompile) {
        this.additionalJavascriptCompile = additionalJavascriptCompile;
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
     */
    public void setRoot(String root) {
        this.root = root;
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
     */
    public void setFileIndex(String fileIndex) {
        this.fileIndex = fileIndex;
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
     */
    public void setFileScript(String fileScript) {
        this.fileScript = fileScript;
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
     */
    public void setBoardPath(String boardPath) {
        this.boardPath = boardPath;
    }

    /**
     * Getter for {@link #dir}.
     *
     * @return field value.
     */
    public VimboardDirSettings getDir() {
        return dir;
    }

    /**
     * Setter for {@link #dir}.
     *
     * @param dir new field value.
     */
    public void setDir(VimboardDirSettings dir) {
        this.dir = dir;
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
     */
    public void setUrlStylesheet(String urlStylesheet) {
        this.urlStylesheet = urlStylesheet;
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
     */
    public void setUrlJavascript(String urlJavascript) {
        this.urlJavascript = urlJavascript;
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
     */
    public void setUrlFavicon(String urlFavicon) {
        this.urlFavicon = urlFavicon;
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
     */
    public void setTrySmarter(Boolean trySmarter) {
        this.trySmarter = trySmarter;
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
     */
    public void setGenerationStrategies(GenerationStrategy[] generationStrategies) {
        this.generationStrategies = generationStrategies;
    }

    /**
     * Getter for {@link #mod}.
     *
     * @return field value.
     */
    public VimboardModSettings getMod() {
        return mod;
    }

    /**
     * Setter for {@link #mod}.
     *
     * @param mod new field value.
     */
    public void setMod(VimboardModSettings mod) {
        this.mod = mod;
    }

    /**
     * Getter for {@link #api}.
     *
     * @return field value.
     */
    public VimboardApiSettings getApi() {
        return api;
    }

    /**
     * Setter for {@link #api}.
     *
     * @param api new field value.
     */
    public void setApi(VimboardApiSettings api) {
        this.api = api;
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
     */
    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
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
     */
    public void setBoardRegex(String boardRegex) {
        this.boardRegex = boardRegex;
    }
}
