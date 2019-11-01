package com.github.vimboard.service;

import com.github.vimboard.domain.Board;
import com.github.vimboard.model.BoardlistModel;
import com.github.vimboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final SecurityService securityService;

    @Autowired
    public BoardService(
            BoardRepository boardRepository,
            SecurityService securityService) {
        this.boardRepository = boardRepository;
        this.securityService = securityService;
    }

    public BoardlistModel buildBoardlist() {
        securityService.foo();


        final Map<String, String> boards = new HashMap<>();
        for (Board board : boardRepository.list()) {
            boards.put(board.getUri(), board.getTitle());
        }



        return new BoardlistModel()
                .setBottom("none-bottom")
                .setTop("none-top");
    }
}
