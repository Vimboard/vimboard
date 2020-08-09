package com.github.vimboard.service;

import com.github.vimboard.config.SettingsBean;
import com.github.vimboard.config.settings.VimboardBoardSettings;
import com.github.vimboard.controller.context.NewBoardContext;
import com.github.vimboard.domain.Board;
import com.github.vimboard.domain.GenerationStrategy;
import com.github.vimboard.model.domain.BoardListModel;
import com.github.vimboard.model.domain.BoardModel;
import com.github.vimboard.model.domain.GenerationAction;
import com.github.vimboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.*;

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

    public void buildIndex(NewBoardContext context) {
        buildIndex(context, true);
    }

    /**
     * @param globalApi TODO: true = "yes", false = "skip"
     */
    public void buildIndex(NewBoardContext context, boolean globalApi) {
        final VimboardBoardSettings customCfg =
                settingsBean.getCustom(context.board.getUri());

        final GenerationAction catalogApiAction;
        {
            final Object[] array = new Object[1];
            array[0] = context.board.getUri();
            catalogApiAction = generationStrategy(
                    "sb_api", array, context.handlerContext.request);
        }

        $pages = null;
        $antibot = null;

        if (customCfg.getApi().getEnabled()) {
            $api = new Api();
            $catalog = array();
        }
    }

    /**
     * Build a set of board URI's.
     *
     * @return boar URI set.
     */
    public Set<String> buildUriSet() {
        final List<Board> boardList = boardRepository.list();
        final Set<String> boardSet = new HashSet<>(boardList.size());
        for (Board board : boardList) {
            boardSet.add(board.getUri());
        }
        return boardSet;
    }

    /**
     * TODO
     * @param fun
     * @param array
     * @param request
     * @return TODO null or GenerationAction
     */
    public GenerationAction generationStrategy(final String fun, Object[] array,
            final HttpServletRequest request) {
        String action = null;

        loop: for(GenerationStrategy gs :
                settingsBean.getAll().getGenerationStrategies()) {
            switch (gs) {

                case STRATEGY_IMMEDIATE:
                    action = "immediate";
                    break loop;

                case STRATEGY_SMART_BUILD:
                    action = "build_on_load";
                    break loop;

                case STRATEGY_SANE:
                    if (settingsBean.get().isRunAsCli()) {
                        break;
                    }
                    if (request.getMethod().equals("POST")
                            && request.getParameter("mod") != null) { // TODO: refactor $_POST['mod']
                        break;
                    }
                    // Thread needs to be done instantly. Same with a board page, but only if posting a new thread.
                    if (fun.equals("sb_thread")
                            || (fun.equals("sb_board") && (int) array[1] == 1 // TODO: refactor array
                                    && request.getMethod().equals("POST")
                                    && request.getParameter("mod") != null)) { // TODO: refactor $_POST['page']
                        action = "immediate";
                        break loop;
                    }
                    break;

                case STRATEGY_FIRST:
                    switch (fun) {
                        case "sb_thread":  // TODO: inc/functions.buildThread('sb_thread', array($board['uri'], $id))
                        case "sb_api":     // TODO: inc/functions.buildIndex('sb_api', array($board['uri']))
                        case "sb_catalog": // TODO: themes/catalog.catalog_build("sb_catalog", array($board))
                        case "sb_ukko":    // TODO: themes/ukko.ukko_build('sb_ukko', array())
                            action = "defer";
                            break loop;
                        case "sb_board":   // TODO: inc/functions.buildIndex('sb_board', array($board['uri'], $page))
                            action = ((int) array[1] > 8) // TODO: refactor array
                                    ? "build_on_load" : "defer";
                            break loop;
                        case "sb_recent":  // TODO: themes/recent.build('sb_recent', array())
                        case "sb_sitemap": // TODO: themes/site_map.sitemap_build('sb_sitemap', array())
                            action = "build_on_load";
                            break loop;
                    }

                // TODO need default:
            }
        }

        if (action == null) {
            return null;
        }

        switch (action) {
            case "immediate":
                return GenerationAction.REBUILD;
            case "defer":
                // Ok, it gets interesting here :)
                get_queue('generate')->push(serialize(array('build', $fun, $array, $action)));
                return GenerationAction.IGNORE;
            case "build_on_load":
                return GenerationAction.DELETE;
            // TODO need default:
        }
    }

    // TODO
    public boolean openBoard(NewBoardContext context)
            throws ServiceException {
        Board board = context.board;
        final String uri = context.uri;

        if (settingsBean.getAll().getTrySmarter()) {
            // TODO $build_pages = array();
            // globals.put("buildPages", );
        }

        if (board != null && board.getUri().equals(uri)) {
            return true;
        }

        board = boardRepository.findByUri(uri);
        if (board != null) {
            setupBoard(context, board);

            // TODO call after_open_board, from vichan

            return true;
        }

        return false;
    }

    private void setupBoard(NewBoardContext context, Board board)
            throws ServiceException {
        final String uri = board.getUri();
        final VimboardBoardSettings config = settingsBean.getCustom(uri);

        context.setBoard(board)
                .setConfig(config);

        final BoardModel boardModel = new BoardModel(board);

        // TODO older versions
        boardModel.setName(board.getTitle());

        final String dir = config.getBoardPath().replace("{uri}", uri);
        boardModel.setDir(dir);
        boardModel.setUrl(config.getBoardAbbreviation().replace("{uri}", uri));

        // loadConfig(); TODO switch from AllBoards() to CustomBoard() ---> context.setConfig

        final FileAttribute<Set<PosixFilePermission>> mod777 =
                PosixFilePermissions.asFileAttribute(
                        PosixFilePermissions.fromString("rwxrwxrwx"));

        setupBoard_createDir(dir, mod777);
        setupBoard_createDir(dir + config.getDir().getImg(), mod777);
        setupBoard_createDir(dir + config.getDir().getThumb(), mod777);
        setupBoard_createDir(dir + config.getDir().getRes(), mod777);
    }

    // TODO refactor it (inline)
    private void setupBoard_createDir(String dir,
            FileAttribute<Set<PosixFilePermission>> attrs)
            throws ServiceException {
        Path dirPath = Paths.get(dir);
        if (Files.notExists(dirPath)) {
            try {
                Files.createDirectory(dirPath, attrs);
            } catch (Exception ex) {
                throw new ServiceException("Couldn't create "
                        + dir + ". Check permissions.", ex);
            }
        }
    }
}
