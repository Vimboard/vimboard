package com.github.vimboard.service;

import com.github.vimboard.config.settings.VimboardBoardSettings;
import com.github.vimboard.config.settings.VimboardSettings;
import com.github.vimboard.controller.context.GlobalContext;
import com.github.vimboard.domain.Board;
import com.github.vimboard.domain.GenerationStrategy;
import com.github.vimboard.domain.Post;
import com.github.vimboard.model.domain.BoardModel;
import com.github.vimboard.model.domain.BoardModelFileboard;
import com.github.vimboard.model.domain.GenerationAction;
import com.github.vimboard.repository.BoardRepository;
import com.github.vimboard.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class Functions {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final SecurityService securityService;
    private final VimboardSettings settings;

    @Autowired
    public Functions(
            BoardRepository boardRepository,
            PostRepository postRepository,
            SecurityService securityService,
            VimboardSettings settings) {
        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
        this.securityService = securityService;
        this.settings = settings;
    }

    // TODO: line: 532
    private void setupBoard(GlobalContext context, Board board)
            throws ServiceException {
        final String uri = board.getUri();
        final VimboardBoardSettings config = settings.getCustom(uri);

        context.setBoard(board)
                .setConfig(config);

        final BoardModel boardModel = new BoardModel(board);

        // TODO older versions
        boardModel.setName(board.getTitle());

        final String dir = config.getBoardPath().replace("{uri}", uri);
        boardModel.setDir(dir);
        boardModel.setUrl(config.getBoardAbbreviation().replace("{uri}", uri));
        context.setBoardModel(boardModel);

        // loadConfig(); TODO switch from AllBoards() to CustomBoard() ---> context.setConfig

        final FileAttribute<Set<PosixFilePermission>> mod777 =
                PosixFilePermissions.asFileAttribute(
                        PosixFilePermissions.fromString("rwxrwxrwx"));

        setupBoard_createDir(dir, mod777);
        setupBoard_createDir(dir + config.getDir().getImg(), mod777);
        setupBoard_createDir(dir + config.getDir().getThumb(), mod777);
        setupBoard_createDir(dir + config.getDir().getRes(), mod777);
    }

    // TODO: inline part
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

    // TODO: line: 563
    public boolean openBoard(GlobalContext context)
            throws ServiceException {
        Board board = context.board;
        final String uri = context.uri;

        if (settings.getAll().getTrySmarter()) {
            context.setBuildPages(new ArrayList<>());
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

    // TODO: line: 1369
    private Object index(GlobalContext context, int page) {
        return index(context, page, false, false);
    }

    // TODO: line: 1369
    private Object index(GlobalContext context, int page, boolean mod) {
        return index(context, page, mod, false);
    }

    // $brief means that we won't need to generate anything yet
    // TODO: line: 1369
    private Object index(GlobalContext context, int page, boolean mod, boolean brief) {
        final BoardModelFileboard board =
                new BoardModelFileboard(context.boardModel);
        final VimboardBoardSettings config = context.config;

        String body = "";
        int offset = (page - 1)*config.getThreadsPerPage();

        List<Post> query = postRepository.listThreads(
                board.getUri(), config.getThreadsPerPage(), offset);

        if (page == 1 && query.size() < config.getThreadsPerPage()) {
            board.setThreadCount(query.size());
        }

        if (query.size() < 1 && page > 1) {
            return false;
        }

        final List<ThreadObj> threads = new ArrayList<>();

        for (Post th : query) {
            ThreadObj thread = new ThreadObj($th, $mod ? '?/' : $config['root'], $mod);

            if ($config['cache']['enabled']) {
                $cached = cache::get("thread_index_{$board['uri']}_{$th['id']}");
                if (isset($cached['replies'], $cached['omitted'])) {
                    $replies = $cached['replies'];
                    $omitted = $cached['omitted'];
                } else {
                    unset($cached);
                }
            }

            if (!isset($cached)) {
                $posts = prepare(sprintf("SELECT * FROM ``posts_%s`` WHERE `thread` = :id ORDER BY `id` DESC LIMIT :limit", $board['uri']));
                $posts->bindValue(':id', $th['id']);
                $posts->bindValue(':limit', ($th['sticky'] ? $config['threads_preview_sticky'] : $config['threads_preview']), PDO::PARAM_INT);
                $posts->execute() or error(db_error($posts));

                $replies = array_reverse($posts->fetchAll(PDO::FETCH_ASSOC));

                if (count($replies) == ($th['sticky'] ? $config['threads_preview_sticky'] : $config['threads_preview'])) {
                    $count = numPosts($th['id']);
                    $omitted = array('post_count' => $count['replies'], 'image_count' => $count['images']);
                } else {
                    $omitted = false;
                }

                if ($config['cache']['enabled'])
                    cache::set("thread_index_{$board['uri']}_{$th['id']}", array(
                        'replies' => $replies,
                        'omitted' => $omitted,
				));
            }

            $num_images = 0;
            foreach ($replies as $po) {
                if ($po['num_files'])
                    $num_images+=$po['num_files'];

                $thread->add(new Post($po, $mod ? '?/' : $config['root'], $mod));
            }

            $thread->images = $num_images;
            $thread->replies = isset($omitted['post_count']) ? $omitted['post_count'] : count($replies);

            if ($omitted) {
                $thread->omitted = $omitted['post_count'] - ($th['sticky'] ? $config['threads_preview_sticky'] : $config['threads_preview']);
                $thread->omitted_images = $omitted['image_count'] - $num_images;
            }

            $threads[] = $thread;

            if (!$brief) {
                $body .= $thread->build(true);
            }
        }

        if ($config['file_board']) {
            $body = Element('fileboard.html', array('body' => $body, 'mod' => $mod));
        }

        return array(
                'board' => $board,
        'body' => $body,
                'post_url' => $config['post_url'],
                'config' => $config,
                'boardlist' => createBoardlist($mod),
                'threads' => $threads,
	    );
    }

    // TODO: line: 1661
    public void buildIndex(GlobalContext context) {
        buildIndex(context, true);
    }

    /**
     * @param globalApi TODO: true = "yes", false = "skip"
     */
    // TODO: line: 1661
    public void buildIndex(GlobalContext context, String globalApi) {
        final BoardModel board = context.boardModel;
        final VimboardBoardSettings config =
                settings.getCustom(board.getUri());

        final GenerationAction catalogApiAction = generationStrategy(
                "sb_api",
                new Object[] { board.getUri() },
                context.handlerContext.request);

        $pages = null;
        $antibot = null;

        if (config.getApi().getEnabled()) {
            $api = new Api();
            $catalog = array();
        }

        for (int page = 1; page <= config.getMaxPages(); page++) {
            String filename = board.getDir() + (page == 1
                    ? config.getFileIndex()
                    : config.getFilePage().replace("{no}", Integer.toString(page)));
            String jsonFilename = board.getDir() + (page - 1) + ".json"; // pages should start from 0

            boolean wontBuildThisPage = config.getTrySmarter()
                    && context.buildPages != null
                    && !context.buildPages.isEmpty()
                    && !context.buildPages.contains(page);

            if ((!config.getApi().getEnabled() || globalApi.equals("skip"))
                    && wontBuildThisPage) {
                continue;
            }

            GenerationAction action = generationStrategy(
                    "sb_board",
                    new Object[] { board.getUri(), page },
                    context.handlerContext.request);
            if (action.equals(GenerationAction.REBUILD)
                    || catalogApiAction.equals(GenerationAction.REBUILD)) {
                content = index(context, page, false, wontBuildThisPage);
                if (content == null) {
                    break;
                }

                // json api
                if ($config['api']['enabled']) {
                    $threads = $content['threads'];
                    $json = json_encode($api->translatePage($threads));
                    file_write($jsonFilename, $json);

                    $catalog[$page-1] = $threads;

                    if ($wont_build_this_page) continue;
                }

                if ($config['try_smarter']) {
                    $antibot = create_antibot($board['uri'], 0 - $page);
                    $content['current_page'] = $page;
                }
                elseif (!$antibot) {
                    $antibot = create_antibot($board['uri']);
                }
                $antibot->reset();
                if (!$pages) {
                    $pages = getPages();
                }
                $content['pages'] = $pages;
                $content['pages'][$page-1]['selected'] = true;
                $content['btn'] = getPageButtons($content['pages']);
                $content['antibot'] = $antibot;

                file_write($filename, Element('index.html', $content));
            }
            elseif ($action == 'delete' || $catalog_api_action == 'delete') {
                file_unlink($filename);
                file_unlink($jsonFilename);
            }
        }

        // $action is an action for our last page
        if (($catalog_api_action == 'rebuild' || $action == 'rebuild' || $action == 'delete') && $page < $config['max_pages']) {
            for (;$page<=$config['max_pages'];$page++) {
                $filename = $board['dir'] . ($page==1 ? $config['file_index'] : sprintf($config['file_page'], $page));
                file_unlink($filename);

                if ($config['api']['enabled']) {
                    $jsonFilename = $board['dir'] . ($page - 1) . '.json';
                    file_unlink($jsonFilename);
                }
            }
        }

        // json api catalog
        if ($config['api']['enabled'] && $global_api != "skip") {
            if ($catalog_api_action == 'delete') {
                $jsonFilename = $board['dir'] . 'catalog.json';
                file_unlink($jsonFilename);
                $jsonFilename = $board['dir'] . 'threads.json';
                file_unlink($jsonFilename);
            }
            elseif ($catalog_api_action == 'rebuild') {
                $json = json_encode($api->translateCatalog($catalog));
                $jsonFilename = $board['dir'] . 'catalog.json';
                file_write($jsonFilename, $json);

                $json = json_encode($api->translateCatalog($catalog, true));
                $jsonFilename = $board['dir'] . 'threads.json';
                file_write($jsonFilename, $json);
            }
        }

        if ($config['try_smarter'])
            $build_pages = array();
    }

    // TODO: line: 1943
    public static String markup(String body) {
        return body; // TODO
    }

    // TODO: line: 2204
    public static String escapeMarkupModifiers(String string) {
        return string.replaceAll("(?i)<(tinyboard) ([\\w\\s]+)>",
                "<$1 escape $2>");
    }

    /**
     * TODO
     * @param fun
     * @param array
     * @param request
     * @return TODO null or GenerationAction
     */
    // TODO: line: 2806
    public GenerationAction generationStrategy(final String fun, Object[] array,
            final HttpServletRequest request) {
        String action = null;

        loop: for(GenerationStrategy gs :
                settings.getAll().getGenerationStrategies()) {
            switch (gs) {

                case STRATEGY_IMMEDIATE:
                    action = "immediate";
                    break loop;

                case STRATEGY_SMART_BUILD:
                    action = "build_on_load";
                    break loop;

                case STRATEGY_SANE:
                    if (settings.isRunAsCli()) {
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
}
