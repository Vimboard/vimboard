package com.github.vimboard.config;

import java.util.Map;

public class VimboardBoardSettings {

    // Post settings ---------------------------------------------------------

    private Boolean countryFlagsCondensed;
    private String countryFlagsCondensedCss;

    // Board settings --------------------------------------------------------

    private String boardAbbreviation;

    // Directory/file settings -----------------------------------------------

    private String boardPath;
    private String fileIndex;
    private String fileScript;
    private String root;
    private String urlFavicon;
    private String urlJavascript;
    private String urlStylesheet;

    // Display settings ------------------------------------------------------

    private Object[] boards;
    private Boolean boardlistWrapBracket;
    private String[] defaultStylesheet;
    private Boolean fontAwesome;
    private String fontAwesomeCss;
    private Map<String, String> stylesheets;
    private String uriStylesheets;

    // Javascript ------------------------------------------------------------

    private String[] additionalJavascript;
    private Boolean additionalJavascriptCompile;
    private String additionalJavascriptUrl;

    // Other/uncategorized ---------------------------------------------------

    private String metaKeywords;

    // Getters and setters (setters must return void) ------------------------

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

    public String getBoardAbbreviation() {
        return boardAbbreviation;
    }

    public void setBoardAbbreviation(String boardAbbreviation) {
        this.boardAbbreviation = boardAbbreviation;
    }

    public String getBoardPath() {
        return boardPath;
    }

    public void setBoardPath(String boardPath) {
        this.boardPath = boardPath;
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

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getUrlFavicon() {
        return urlFavicon;
    }

    public void setUrlFavicon(String urlFavicon) {
        this.urlFavicon = urlFavicon;
    }

    public String getUrlJavascript() {
        return urlJavascript;
    }

    public void setUrlJavascript(String urlJavascript) {
        this.urlJavascript = urlJavascript;
    }

    public String getUrlStylesheet() {
        return urlStylesheet;
    }

    public void setUrlStylesheet(String urlStylesheet) {
        this.urlStylesheet = urlStylesheet;
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

    public String[] getAdditionalJavascript() {
        return additionalJavascript;
    }

    public void setAdditionalJavascript(String[] additionalJavascript) {
        this.additionalJavascript = additionalJavascript;
    }

    public Boolean getAdditionalJavascriptCompile() {
        return additionalJavascriptCompile;
    }

    public void setAdditionalJavascriptCompile(Boolean additionalJavascriptCompile) {
        this.additionalJavascriptCompile = additionalJavascriptCompile;
    }

    public String getAdditionalJavascriptUrl() {
        return additionalJavascriptUrl;
    }

    public void setAdditionalJavascriptUrl(String additionalJavascriptUrl) {
        this.additionalJavascriptUrl = additionalJavascriptUrl;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }
}
