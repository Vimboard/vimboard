package com.github.vimboard.cli.command;

import com.github.vimboard.cli.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BoardCommand {

    private final BoardService boardService;

    @Autowired
    public BoardCommand(BoardService boardService) {
        this.boardService = boardService;
    }

    @ShellMethod(key = "board-create", value = "Create board.")
    public void create(String uri, String title, String subtitle) {
        boardService.create(uri, title, subtitle);
    }
}
