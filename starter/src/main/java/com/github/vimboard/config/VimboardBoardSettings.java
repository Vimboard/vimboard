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

    // Other/uncategorized ---------------------------------------------------

    private String metaKeywords;

    // Getters and setters ---------------------------------------------------

    public Boolean getCountryFlagsCondensed() {
        return countryFlagsCondensed;
    }

    public VimboardBoardSettings setCountryFlagsCondensed(Boolean countryFlagsCondensed) {
        this.countryFlagsCondensed = countryFlagsCondensed;
        return this;
    }

    public String getCountryFlagsCondensedCss() {
        return countryFlagsCondensedCss;
    }

    public VimboardBoardSettings setCountryFlagsCondensedCss(String countryFlagsCondensedCss) {
        this.countryFlagsCondensedCss = countryFlagsCondensedCss;
        return this;
    }

    public String getBoardAbbreviation() {
        return boardAbbreviation;
    }

    public VimboardBoardSettings setBoardAbbreviation(String boardAbbreviation) {
        this.boardAbbreviation = boardAbbreviation;
        return this;
    }

    public String getBoardPath() {
        return boardPath;
    }

    public VimboardBoardSettings setBoardPath(String boardPath) {
        this.boardPath = boardPath;
        return this;
    }

    public String getFileIndex() {
        return fileIndex;
    }

    public VimboardBoardSettings setFileIndex(String fileIndex) {
        this.fileIndex = fileIndex;
        return this;
    }

    public String getFileScript() {
        return fileScript;
    }

    public VimboardBoardSettings setFileScript(String fileScript) {
        this.fileScript = fileScript;
        return this;
    }

    public String getRoot() {
        return root;
    }

    public VimboardBoardSettings setRoot(String root) {
        this.root = root;
        return this;
    }

    public String getUrlFavicon() {
        return urlFavicon;
    }

    public VimboardBoardSettings setUrlFavicon(String urlFavicon) {
        this.urlFavicon = urlFavicon;
        return this;
    }

    public String getUrlJavascript() {
        return urlJavascript;
    }

    public VimboardBoardSettings setUrlJavascript(String urlJavascript) {
        this.urlJavascript = urlJavascript;
        return this;
    }

    public String getUrlStylesheet() {
        return urlStylesheet;
    }

    public VimboardBoardSettings setUrlStylesheet(String urlStylesheet) {
        this.urlStylesheet = urlStylesheet;
        return this;
    }

    public Object[] getBoards() {
        return boards;
    }

    public VimboardBoardSettings setBoards(Object[] boards) {
        this.boards = boards;
        return this;
    }

    public Boolean getBoardlistWrapBracket() {
        return boardlistWrapBracket;
    }

    public VimboardBoardSettings setBoardlistWrapBracket(Boolean boardlistWrapBracket) {
        this.boardlistWrapBracket = boardlistWrapBracket;
        return this;
    }

    public String[] getDefaultStylesheet() {
        return defaultStylesheet;
    }

    public VimboardBoardSettings setDefaultStylesheet(String[] defaultStylesheet) {
        this.defaultStylesheet = defaultStylesheet;
        return this;
    }

    public Boolean getFontAwesome() {
        return fontAwesome;
    }

    public VimboardBoardSettings setFontAwesome(Boolean fontAwesome) {
        this.fontAwesome = fontAwesome;
        return this;
    }

    public String getFontAwesomeCss() {
        return fontAwesomeCss;
    }

    public VimboardBoardSettings setFontAwesomeCss(String fontAwesomeCss) {
        this.fontAwesomeCss = fontAwesomeCss;
        return this;
    }

    public Map<String, String> getStylesheets() {
        return stylesheets;
    }

    public VimboardBoardSettings setStylesheets(Map<String, String> stylesheets) {
        this.stylesheets = stylesheets;
        return this;
    }

    public String getUriStylesheets() {
        return uriStylesheets;
    }

    public VimboardBoardSettings setUriStylesheets(String uriStylesheets) {
        this.uriStylesheets = uriStylesheets;
        return this;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public VimboardBoardSettings setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
        return this;
    }
}
