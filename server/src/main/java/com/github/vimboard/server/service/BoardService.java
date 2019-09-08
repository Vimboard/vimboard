package com.github.vimboard.server.service;

import com.github.vimboard.server.dao.BoardDao;
import com.github.vimboard.server.domain.Board;
import com.github.vimboard.starter.VimboardConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardDao boardDao;
    private final VimboardConfig vimboardConfig;

    @Autowired
    public BoardService(
            BoardDao boardDao,
            VimboardConfig vimboardConfig) {
        this.boardDao = boardDao;
        this.vimboardConfig = vimboardConfig;
    }

    public void createBoard(Board board) {

    }

    public void dropBoard(Board board) {

    }
}


