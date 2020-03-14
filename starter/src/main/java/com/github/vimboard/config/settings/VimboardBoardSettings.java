package com.github.vimboard.config.settings;

import java.util.Map;

public class VimboardBoardSettings {

    // General/misc settings

    private Boolean debug;
    private Short redirectHttp;

    // Database settings

    // Cache, lock and queue settings

    // Cookie settings

    private VimboardCookiesSettings cookies;

    // Flood/spam settings

    private Boolean recaptcha;

    // Post settings

    private Boolean countryFlagsCondensed;
    private String countryFlagsCondensedCss;

    // Ban settings

    private Boolean banAppeals;

    // Markup settings

    // Image settings

    // Board settings

    private String boardAbbreviation;
    private Boolean allowSubtitleHtml;

    // Display settings

    private String postDate;
    private Map<String, String> stylesheets;
    private String uriStylesheets;
    private String[] defaultStylesheet;
    private Boolean fontAwesome;
    private String fontAwesomeCss;
    private Object[] boards;
    private Boolean boardlistWrapBracket;

    // Javascript

    private String[] additionalJavascript;
    private String additionalJavascriptUrl;
    private Boolean additionalJavascriptCompile;

    // Video embedding

    // Error messages

    // Directory/file settings

    private String root;
    private String fileIndex;
    private String fileScript;
    private String boardPath;
    private String urlStylesheet;
    private String urlJavascript;
    private String urlFavicon;

    // Advanced build

    // Mod settings

    private VimboardModSettings mod;

    // Mod permissions

    // Create pages

    // Public pages

    // Events (PHP 5.3.0+)

    // API settings

    // NNTPChan settings

    // Other/uncategorized

    private String metaKeywords;
    private String boardRegex;

    // Read-only settings

    private String version;

    //------------------------------------------------------------------------
    // Getters and setters (setters must return void)
    //------------------------------------------------------------------------

    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    public Short getRedirectHttp() {
        return redirectHttp;
    }

    public void setRedirectHttp(Short redirectHttp) {
        this.redirectHttp = redirectHttp;
    }

    public VimboardCookiesSettings getCookies() {
        return cookies;
    }

    public void setCookies(VimboardCookiesSettings cookies) {
        this.cookies = cookies;
    }

    public Boolean getRecaptcha() {
        return recaptcha;
    }

    public void setRecaptcha(Boolean recaptcha) {
        this.recaptcha = recaptcha;
    }

    public Boolean getCountryFlagsCondensed() {
        return countryFlagsCondensed;
    }

    public void setCountryFlagsCondensed(Boolean countryFlagsCondensed) {
        this.countryFlagsCondensed = countryFlagsCondensed;
    }

    public String getCountryFlagsCondensedCss() {
        return countryFlagsCondensedCss;
    }

    public void setCountryFlagsCondensedCss(String countryFlagsCondensedCss) {
        this.countryFlagsCondensedCss = countryFlagsCondensedCss;
    }

    public Boolean getBanAppeals() {
        return banAppeals;
    }

    public void setBanAppeals(Boolean banAppeals) {
        this.banAppeals = banAppeals;
    }

    public String getBoardAbbreviation() {
        return boardAbbreviation;
    }

    public void setBoardAbbreviation(String boardAbbreviation) {
        this.boardAbbreviation = boardAbbreviation;
    }

    public Boolean getAllowSubtitleHtml() {
        return allowSubtitleHtml;
    }

    public void setAllowSubtitleHtml(Boolean allowSubtitleHtml) {
        this.allowSubtitleHtml = allowSubtitleHtml;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public Map<String, String> getStylesheets() {
        return stylesheets;
    }

    public void setStylesheets(Map<String, String> stylesheets) {
        this.stylesheets = stylesheets;
    }

    public String getUriStylesheets() {
        return uriStylesheets;
    }

    public void setUriStylesheets(String uriStylesheets) {
        this.uriStylesheets = uriStylesheets;
    }

    public String[] getDefaultStylesheet() {
        return defaultStylesheet;
    }

    public void setDefaultStylesheet(String[] defaultStylesheet) {
        this.defaultStylesheet = defaultStylesheet;
    }

    public Boolean getFontAwesome() {
        return fontAwesome;
    }

    public void setFontAwesome(Boolean fontAwesome) {
        this.fontAwesome = fontAwesome;
    }

    public String getFontAwesomeCss() {
        return fontAwesomeCss;
    }

    public void setFontAwesomeCss(String fontAwesomeCss) {
        this.fontAwesomeCss = fontAwesomeCss;
    }

    public Object[] getBoards() {
        return boards;
    }

    public void setBoards(Object[] boards) {
        this.boards = boards;
    }

    public Boolean getBoardlistWrapBracket() {
        return boardlistWrapBracket;
    }

    public void setBoardlistWrapBracket(Boolean boardlistWrapBracket) {
        this.boardlistWrapBracket = boardlistWrapBracket;
    }

    public String[] getAdditionalJavascript() {
        return additionalJavascript;
    }

    public void setAdditionalJavascript(String[] additionalJavascript) {
        this.additionalJavascript = additionalJavascript;
    }

    public String getAdditionalJavascriptUrl() {
        return additionalJavascriptUrl;
    }

    public void setAdditionalJavascriptUrl(String additionalJavascriptUrl) {
        this.additionalJavascriptUrl = additionalJavascriptUrl;
    }

    public Boolean getAdditionalJavascriptCompile() {
        return additionalJavascriptCompile;
    }

    public void setAdditionalJavascriptCompile(Boolean additionalJavascriptCompile) {
        this.additionalJavascriptCompile = additionalJavascriptCompile;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(String fileIndex) {
        this.fileIndex = fileIndex;
    }

    public String getFileScript() {
        return fileScript;
    }

    public void setFileScript(String fileScript) {
        this.fileScript = fileScript;
    }

    public String getBoardPath() {
        return boardPath;
    }

    public void setBoardPath(String boardPath) {
        this.boardPath = boardPath;
    }

    public String getUrlStylesheet() {
        return urlStylesheet;
    }

    public void setUrlStylesheet(String urlStylesheet) {
        this.urlStylesheet = urlStylesheet;
    }

    public String getUrlJavascript() {
        return urlJavascript;
    }

    public void setUrlJavascript(String urlJavascript) {
        this.urlJavascript = urlJavascript;
    }

    public String getUrlFavicon() {
        return urlFavicon;
    }

    public void setUrlFavicon(String urlFavicon) {
        this.urlFavicon = urlFavicon;
    }

    public VimboardModSettings getMod() {
        return mod;
    }

    public void setMod(VimboardModSettings mod) {
        this.mod = mod;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getBoardRegex() {
        return boardRegex;
    }

    public void setBoardRegex(String boardRegex) {
        this.boardRegex = boardRegex;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
