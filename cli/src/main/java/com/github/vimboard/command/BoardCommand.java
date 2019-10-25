package com.github.vimboard.command;

import com.github.vimboard.domain.Board;
import com.github.vimboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BoardCommand {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardCommand(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @ShellMethod(key = "board-create", value = "Create board.")
    public void create(String uri, String title, String subtitle) {
        boardRepository.create(uri, title, subtitle);
    }

    @ShellMethod(key = "board-list", value = "List all boards.")
    public String list() {
        StringBuilder sb = new StringBuilder();
        for (Board board : boardRepository.list()) {
            if (sb.length() > 0) {
                sb.append(System.lineSeparator());
            }
            sb.append(boardToString(board));
        }
        return sb.toString();
    }

    private String boardToString(Board board) {
        return board.getId() + "(" + board.getUri() + ")";
    }
}
