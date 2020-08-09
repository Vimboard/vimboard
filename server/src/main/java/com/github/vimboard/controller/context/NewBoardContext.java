package com.github.vimboard.controller.context;

import com.github.vimboard.config.settings.VimboardBoardSettings;
import com.github.vimboard.domain.Board;

/**
 * Context of ModController#newBoard.
 */
public class NewBoardContext {

    /** Current opened board. */
    public Board board;

    /** Custom board config. */
    public VimboardBoardSettings config;

    /** Handler context */
    public HandlerContext handlerContext;

    /** URI parameter of the request. */
    public String uri;

    /**
     * Setter for {@link #board}.
     *
     * @param board new field value.
     * @return {@code this}.
     */
    public NewBoardContext setBoard(Board board) {
        this.board = board;
        return this;
    }

    /**
     * Setter for {@link #config}.
     *
     * @param config new field value.
     * @return {@code this}.
     */
    public NewBoardContext setConfig(VimboardBoardSettings config) {
        this.config = config;
        return this;
    }

    /**
     * Setter for {@link #handlerContext}.
     *
     * @param handlerContext new field value.
     * @return {@code this}.
     */
    public NewBoardContext setHandlerContext(HandlerContext handlerContext) {
        this.handlerContext = handlerContext;
        return this;
    }

    /**
     * Setter for {@link #uri}.
     *
     * @param uri new field value.
     * @return {@code this}.
     */
    public NewBoardContext setUri(String uri) {
        this.uri = uri;
        return this;
    }
}
