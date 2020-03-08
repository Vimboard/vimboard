package com.github.vimboard.model;

public class PageModel {

    /**
     * Board list bar model.
     */
    private BoardListModel boardlist = null;

    /**
     * Page data stylesheet.
     */
//    {
//        // TODO replace array in config.defaultStylesheet[1]. `0` value is used only in `main.js`
//        new String[] {
//                vimboardProperties.getAll().getDefaultStylesheet(),
//                vimboardProperties.getAll().getStylesheets().get(vimboardProperties.getAll().getDefaultStylesheet())
//        }
//    }
    private String dataStylesheet;

    /**
     * If {@code true} then don't draw "Return to dashboard" link for mod user.
     */
    private Boolean hideDashboardLink = false;

    /**
     * If {@code true} then don't include main script file
     * {@code <board-config>.file-script}.
     */
    private boolean nojavascript = false;

    /**
     * TODO
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

    //------------------------------------------------------------------------
    // Getters and setters
    //------------------------------------------------------------------------

    public BoardListModel getBoardlist() {
        return boardlist;
    }

    public PageModel setBoardlist(BoardListModel boardlist) {
        this.boardlist = boardlist;
        return this;
    }

    public String getDataStylesheet() {
        return dataStylesheet;
    }

    public PageModel setDataStylesheet(String dataStylesheet) {
        this.dataStylesheet = dataStylesheet;
        return this;
    }

    public Boolean getHideDashboardLink() {
        return hideDashboardLink;
    }

    public PageModel setHideDashboardLink(Boolean hideDashboardLink) {
        this.hideDashboardLink = hideDashboardLink;
        return this;
    }

    public boolean isNojavascript() {
        return nojavascript;
    }

    public PageModel setNojavascript(boolean nojavascript) {
        this.nojavascript = nojavascript;
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
