package com.github.vimboard.service;

import com.github.vimboard.domain.Board;
import com.github.vimboard.model.BoardListModel;
import com.github.vimboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public BoardListModel buildBoardList() {
        final Map<String, String> boards = new HashMap<>();
        for (Board board : boardRepository.list()) {
            boards.put(board.getUri(), board.getTitle());
        }


        throw new RuntimeException("TODO");
    }
}
