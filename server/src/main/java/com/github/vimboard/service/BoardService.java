package com.github.vimboard.service;

import com.github.vimboard.config.SettingsBean;
import com.github.vimboard.domain.Board;
import com.github.vimboard.model.domain.BoardListModel;
import com.github.vimboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 * Board related functions.
 */
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final SecurityService securityService;
    private final SettingsBean settingsBean;

    @Autowired
    public BoardService(
            BoardRepository boardRepository,
            SecurityService securityService,
            SettingsBean settingsBean) {
        this.boardRepository = boardRepository;
        this.securityService = securityService;
        this.settingsBean = settingsBean;
    }

    /**
     * Build a board list bar model.
     *
     * @return the board list bar model.
     */
    public BoardListModel buildBoardList() {
        return buildBoardList("");
    }

    /**
     * Build a board list bar model for a specific board.
     *
     * @param boardUri the board uri.
     * @return the board list bar model.
     */
    public BoardListModel buildBoardList(String boardUri) {
        final Object[] boards = settingsBean.getCustom(boardUri).getBoards();

        if (boards == null) {
            return new BoardListModel()
                    .setBottom("")
                    .setTop("");
        }

        final Map<String, String> enabledBoards = new HashMap<>();
        for (Board board : boardRepository.list()) {
            enabledBoards.put(board.getUri(), board.getTitle());
        }

        final StringBuilder sb = new StringBuilder();
        buildBoardlistPart(
                sb,
                boards,
                securityService.isMod()
                        ? "?/"
                        : settingsBean.getAll().getRoot(),
                enabledBoards);
        final String body = sb.toString();

        // Message compact-boardlist.js faster, so that page looks less ugly during loading
        final String top = "<script type=\"text/javascript\">if (typeof do_boardlist != 'undefined') do_boardlist();</script>";
        return new BoardListModel()
                .setBottom("<div class=\"boardlist bottom\">" + body + "</div>")
                .setTop("<div class=\"boardlist\">" + body + "</div>" + top);
    }

    private void buildBoardlistPart(StringBuilder sb, Object[] boards,
            String root, Map<String, String> enabledBoards) {
        for (int i = 0; i < boards.length; i++) {
            final Object obj = boards[i];
            if (obj instanceof Map.Entry) {
                final Map.Entry entry = (Map.Entry) obj;
                if (entry.getValue() instanceof Object[]) {
                    sb.append("<span class=\"sub\" data-description=\"")
                            .append((String) entry.getKey())
                            .append("\">[ ");
                    buildBoardlistPart(
                            sb,
                            (Object[]) entry.getValue(),
                            root,
                            enabledBoards);
                    sb.append(" ]</span> ");
                } else {
                    sb.append("<a href=\"")
                            .append((String) entry.getValue())
                            .append("\">")
                            .append((String) entry.getKey())
                            .append("</a>");
                }
            } else {
                final String boardUri = (String) obj;
                final String boardTitle = enabledBoards.get(boardUri);
                final String titleAttr = (boardTitle == null)
                        ? ""
                        : " title=\"" + boardTitle + "\"";
                sb.append("<a href=\"")
                        .append(root)
                        .append(boardUri)
                        .append("/")
                        .append(settingsBean.getCustom(boardUri).getFileIndex())
                        .append("\"")
                        .append(titleAttr)
                        .append(">")
                        .append(boardUri)
                        .append("</a>");
            }
            if (i < boards.length - 1) {
                sb.append(" / ");
            }
        }
    }
}
