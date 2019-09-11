package com.github.vimboard.server.service;

import com.github.vimboard.server.dao.BoardDao;
import com.github.vimboard.server.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardDao boardDao;

    @Autowired
    public BoardService(
            BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public void createBoard(Board board) {

    }

    public void dropBoard(Board board) {

    }

    // TODO public List<Board> getBoardList
}


