package com.github.vimboard.model;

public class PageModel {

    /**
     * Html rendered board list bar.
     */
    private BoardlistModel boardlist = null;

    /**
     * If {@code true} then don't draw return to dashboard link.
     */
    private Boolean hideDashboardLink = false;

    /**
     * If not {@code null} then user has mod privileies.
     */
    private ModModel mod = null;

    /**
     *
     */
    private PersonalMessageModel pm = null;

    /**
     * Description under the title string.
     */
    private String subtitle = null;

    /**
     * Title of the page.
     */
    private String title;

    // Getters and setters ---------------------------------------------------

    public BoardlistModel getBoardlist() {
        return boardlist;
    }

    public PageModel setBoardlist(BoardlistModel boardlist) {
        this.boardlist = boardlist;
        return this;
    }

    public Boolean getHideDashboardLink() {
        return hideDashboardLink;
    }

    public PageModel setHideDashboardLink(Boolean hideDashboardLink) {
        this.hideDashboardLink = hideDashboardLink;
        return this;
    }

    public ModModel getMod() {
        return mod;
    }

    public PageModel setMod(ModModel mod) {
        this.mod = mod;
        return this;
    }

    public PersonalMessageModel getPm() {
        return pm;
    }

    public PageModel setPm(PersonalMessageModel pm) {
        this.pm = pm;
        return this;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public PageModel setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PageModel setTitle(String title) {
        this.title = title;
        return this;
    }
}