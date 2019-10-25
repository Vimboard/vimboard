package com.github.vimboard.config;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

public class VimboardProperties {

    /**
     * The common configuration for all boards.
     */
    @NestedConfigurationProperty
    private BoardProperties allBoards;

    /**
      * Separate configurations that overrides settings
      * of the common configuration for each board.
      */
    private Map<String, BoardProperties> customBoard;

    /**
      * Static content location.
      */
    private String www;

    // Init ------------------------------------------------------------------

    @PostConstruct
    public void init() {

        if (allBoards == null) {
            allBoards = new BoardProperties();
        }
        BoardProperties.init(allBoards, null);

        if (customBoard == null) {
            customBoard = new HashMap<>();
        }
        for (String boardUrl : customBoard.keySet()) {
            BoardProperties b = customBoard.get(boardUrl);
            BoardProperties.init(b, this);
        }

        if (www == null || www.isEmpty()) {
            www = "/var/www/vimboard/public/";
        }
    }

    // Getters and setters ---------------------------------------------------

    public BoardProperties getAllBoards() {
        return allBoards;
    }

    public void setAllBoards(BoardProperties allBoards) {
        this.allBoards = allBoards;
    }

    public Map<String, BoardProperties> getCustomBoard() {
        return customBoard;
    }

    public void setCustomBoard(Map<String, BoardProperties> customBoard) {
        this.customBoard = customBoard;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }
}
