package com.github.vimboard.service;

import com.github.vimboard.config.BoardListBean;
import com.github.vimboard.config.VimboardProperties;
import com.github.vimboard.domain.Board;
import com.github.vimboard.model.BoardListModel;
import com.github.vimboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.github.vimboard.config.VimboardProperties.props;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardListBean boardListBean;
    private final SecurityService securityService;
    private final VimboardProperties vimboardProperties;

    @Autowired
    public BoardService(
            BoardRepository boardRepository,
            BoardListBean boardListBean,
            SecurityService securityService,
            VimboardProperties vimboardProperties) {
        this.boardRepository = boardRepository;
        this.boardListBean = boardListBean;
        this.securityService = securityService;
        this.vimboardProperties = vimboardProperties;
    }

    public BoardListModel buildBoardList() {
        return buildBoardList("");
    }

    public BoardListModel buildBoardList(String boardUri) {
        final Object[] boards = boardListBean.get(boardUri);

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

        final String body = buildBoardlistPart(sb, boards, "?/", enabledBoards);

//        if ($config['boardlist_wrap_bracket'] && !preg_match('/\] $/', $body))
//            $body = '[' . $body . ']';
//
//        $body = trim($body);

        // Message compact-boardlist.js faster, so that page looks less ugly during loading
        final String top = "<script type=\"text/javascript\">if (typeof do_boardlist != 'undefined') do_boardlist();</script>";
        return new BoardListModel()
                .setBottom("<div class=\"boardlist bottom\">" + body + "</div>")
                .setTop("<div class=\"boardlist\">" + body + "</div>" + top);
    }

    private String buildBoardlistPart(StringBuilder sb, Object[] boards,
            String root, Map<String, String> enabledBoards) {
        StringBuilder body = new StringBuilder();

        for (final Object obj : boards) {
            if (obj instanceof Map.Entry) {
                final Map.Entry entry = (Map.Entry) obj;
                if (entry.getValue() instanceof Object[]) {
                    body.append(" <span class=\"sub\" data-description=\"")
                            .append((String) entry.getKey())
                            .append("\">[")
                            .append(buildBoardlistPart(sb, (Object[]) entry.getValue(), root, enabledBoards))
                            .append("]</span> ");
                } else {
                    body.append(" <a href=\"")
                            .append((String) entry.getValue())
                            .append("\">")
                            .append((String) entry.getKey())
                            .append("</a> /");
                }
            } else {
                final String boardUri = (String) obj;
                final String boardTitle = enabledBoards.get(boardUri);
                final String titleAttr = (boardTitle == null)
                        ? ""
                        : " title=\"" + boardTitle + "\"";
                body.append(" <a href=\"")
                        .append(root)
                        .append(boardUri)
                        .append("/")
                        .append(props(vimboardProperties, boardUri).getFileIndex())
                        .append("\"")
                        .append(titleAttr)
                        .append(">")
                        .append(boardUri)
                        .append("</a> /");
            }
        }
        return body.toString();
    }
}
