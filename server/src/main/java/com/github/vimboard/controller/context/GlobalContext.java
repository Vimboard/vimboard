package com.github.vimboard.controller.context;

import com.github.vimboard.config.settings.VimboardBoardSettings;
import com.github.vimboard.domain.Board;
import com.github.vimboard.model.BoardModel;
import com.github.vimboard.service.Events;

import java.util.List;

/**
 * TODO: временный аналог переменных `global`
 */
public class GlobalContext {

    /** Current opened board. */
    public Board board;

    /** Current opened board model */
    public BoardModel boardModel;

    /** TODO: ??? */
    public List<Integer> buildPages;

    /** Custom board config. */
    public VimboardBoardSettings config;

    /** Events service. */
    public Events events;

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
    public GlobalContext setBoard(Board board) {
        this.board = board;
        return this;
    }

    /**
     * Setter for {@link #boardModel}.
     *
     * @param boardModel new field value.
     * @return {@code this}.
     */
    public GlobalContext setBoardModel(BoardModel boardModel) {
        this.boardModel = boardModel;
        return this;
    }

    /**
     * Setter for {@link #buildPages}.
     *
     * @param buildPages new field value.
     * @return {@code this}.
     */
    public GlobalContext setBuildPages(List<Integer> buildPages) {
        this.buildPages = buildPages;
        return this;
    }

    /**
     * Setter for {@link #config}.
     *
     * @param config new field value.
     * @return {@code this}.
     */
    public GlobalContext setConfig(VimboardBoardSettings config) {
        this.config = config;
        return this;
    }

    /**
     * Setter for {@link #events}.
     *
     * @param events new field value.
     * @return {@code this}.
     */
    public GlobalContext setEvents(Events events) {
        this.events = events;
        return this;
    }

    /**
     * Setter for {@link #handlerContext}.
     *
     * @param handlerContext new field value.
     * @return {@code this}.
     */
    public GlobalContext setHandlerContext(HandlerContext handlerContext) {
        this.handlerContext = handlerContext;
        return this;
    }

    /**
     * Setter for {@link #uri}.
     *
     * @param uri new field value.
     * @return {@code this}.
     */
    public GlobalContext setUri(String uri) {
        this.uri = uri;
        return this;
    }
}
