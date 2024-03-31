package com.github.vimboard.service;

import com.github.vimboard.config.settings.VimboardBoardSettings;
import com.github.vimboard.config.settings.VimboardSettings;
import com.github.vimboard.controller.context.GlobalContext;
import com.github.vimboard.domain.Board;
import com.github.vimboard.domain.GenerationStrategy;
import com.github.vimboard.domain.NumPosts;
import com.github.vimboard.domain.Post;
import com.github.vimboard.inc.display.IncPost;
import com.github.vimboard.inc.display.IncThread;
import com.github.vimboard.model.BoardModel;
import com.github.vimboard.model.BoardModelFileboard;
import com.github.vimboard.model.ContentModel;
import com.github.vimboard.model.GenerationAction;
import com.github.vimboard.page.FileboardPage;
import com.github.vimboard.page.IndexPage;
import com.github.vimboard.repository.BoardRepository;
import com.github.vimboard.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Помещает в контекст указанную доску и её конфиг.
     *
     * @param context
     * @param board
     * @throws ServiceException
     */
    private void setupBoard(GlobalContext context, Board board)
            throws ServiceException {
        final String uri = board.getUri();
        final VimboardBoardSettings config = settings.getCustom(uri);

        context.setBoard(board)
                .setConfig(config);

        final BoardModel boardModel = new BoardModel(board);

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

    /**
     * Открывает (помещает в контекст), если не открыта ещё, доску по uri
     * из контекста.
     *
     * @param context
     * @return {@code true} если в контексте находится доска, uri которой
     *     совпадает с uri из контекста.
     * @throws ServiceException
     */
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

            // TODO: call after_open_board
            // TODO: Эта функция существует только в /smart_build.php и /tools/worker.php

            return true;
        }

        return false;
    }

    private Object index(GlobalContext context, int page) {
        return index(context, page, false, false);
    }

    private Object index(GlobalContext context, int page, boolean mod) {
        return index(context, page, mod, false);
    }

    // $brief means that we won't need to generate anything yet
    private ContentModel index(GlobalContext context, int page, boolean mod, boolean brief) {/* TODO: CURRENT
        final BoardModelFileboard board =
                new BoardModelFileboard(context.boardModel);
        final VimboardBoardSettings config = context.config;

        StringBuilder body = new StringBuilder();
        long offset = (long) (page - 1) * config.getThreadsPerPage();

        List<Post> query = postRepository.listThreads(
                board.getUri(), config.getThreadsPerPage(), offset);

        if (page == 1 && query.size() < config.getThreadsPerPage()) {
            board.setThreadCount(query.size());
        }

        if (query.size() < 1 && page > 1) {
            return null;
        }

        final List<IncThread> threads = new ArrayList<>();

        for (Post th : query) {
            IncThread thread = new IncThread(config, th, mod ? "?/" : config.getRoot(), mod);  // TODO: WAIT FOR DEV ========================================================

            List<Post> posts = postRepository.listPosts(
                    board.getUri(), th.getId(), (th.isSticky() ? config.getThreadsPreviewSticky() : config.getThreadsPreview()));

            List<Post> replies = new ArrayList<>(posts);
            Collections.reverse(replies);

            final Map omitted;
            if (replies.size() == ((th.isSticky() ? config.getThreadsPreviewSticky() : config.getThreadsPreview()))) {
                NumPosts count = numPosts(context, th.getId());
                omitted = new HashMap(); // TODO: удалить через рефакторинг
                omitted.put("post_count", count.getReplies());
                omitted.put("image_count", count.getImages());
            } else {
                omitted = null;
            }

            int numImages = 0;
            for (Post po : replies) {
                if (po.getNumFiles() > 0) {
                    numImages += po.getNumFiles();
                }

                thread.add(new IncPost(po, mod ? "?/" : config.getRoot(), mod));
            }

            thread.setImages(numImages);
            thread.setReplies(omitted != null && omitted.get("post_count") != null
                    ? (int) omitted.get("post_count") : replies.size()); // TODO: (int) list object

            if (omitted != null) {
                thread.setOmitted((int) omitted.get("post_count") - (th.isSticky() ? config.getThreadsPreviewSticky() : config.getThreadsPreview()));
                thread.setOmittedImages((int) omitted.get("image_count") - numImages);
            }

            threads.add(thread);

            if (!brief) {
                body.append(thread.build(context, true)); // TODO:
            }
        }

        if (config.getFileBoard()) {
            final IndexPage indexPage = new IndexPage()
                    .setBody("fileboard.ftlh");
            put("fileboard", new FileboardPage()
                    .setBody()
                    .setMod());

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
	    );TODO: CURRENT */ return null;
    }

    // Returns an associative array with 'replies' and 'images' keys
    public NumPosts numPosts(GlobalContext context, long id) {
        final BoardModelFileboard board =
                new BoardModelFileboard(context.boardModel);

        return postRepository.numPosts(board.getUri(), id);
    }

    public void buildIndex(GlobalContext context) {
        buildIndex(context, "yes");
    }

    /**
     * @param globalApi TODO: true = "yes", false = "skip"
     */
    public void buildIndex(GlobalContext context, String globalApi) {
        /* TODO: CURRENT
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
                ContentModel content = index(context, page, false, wontBuildThisPage); // TODO: CURRENT <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                if (content == null) {
                    break;
                }

                // json api
                if ($config['api']['enabled']) {
                    $threads = $content['threads'];
                    $json = json_encode($api->translatePage($threads));
                    file_write($jsonFilename, $json);

                    $catalog[$page-1] = $threads;

                    if ($wont_build_this_page) {
                        continue;
                    }
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
         */
    }

    public static Map<String, String> extractModifiers(String body) {
        Map<String, String> modifiers = new HashMap<>();

        Pattern pattern = Pattern.compile("<tinyboard ([\\w\\s]+)>(.*?)</tinyboard>");
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            final String group1 = matcher.group(1);
            if (group1.startsWith("escape ")) {
                continue;
            }
            modifiers.put(group1, HtmlUtils.htmlUnescape(matcher.group(2)));
        }

    	return modifiers;
    }

    public static String removeModifiers(String body) {
        return body
                .replaceAll("<tinyboard ([\\w\\s]+)>(.*?)</tinyboard>", "");
    }

    public static String markup(String body) {
        return body; // TODO
    }

    public static String escapeMarkupModifiers(String string) {
        return string.replaceAll("(?i)<(tinyboard) ([\\w\\s]+)>",
                "<$1 escape $2>");
    }

    /**
     * TODO
     * @param fun идентификатор стратегии и имя функции для построения через очередь.
     * @param array ??? Какой-то контекст. 0 элемент - uri доски. 1 элемент - какая-то цифра
     * @param request
     * @return TODO null or GenerationAction
     */
    public GenerationAction generationStrategy(final String fun, Object[] array,
            final HttpServletRequest request) {
        final String ACT_BUILD_ON_LOAD = "build_on_load";
        final String ACT_DEFER = "defer";
        final String ACT_IMMEDIATE = "immediate";

        String action = null;

        loop: for (GenerationStrategy gs :
                settings.getAll().getGenerationStrategies()) {
            switch (gs) {

                case STRATEGY_IMMEDIATE:
                    action = ACT_IMMEDIATE;
                    break loop;

                case STRATEGY_SMART_BUILD:
                    action = ACT_BUILD_ON_LOAD;
                    break loop;

                case STRATEGY_SANE:
                    if (settings.isRunAsCli()) {
                        break;
                    }
                    if (request.getMethod().equals("POST")
                            && request.getParameter("mod") != null) { // TODO: признак модератора определять не через request
                        break;
                    }
                    // Thread needs to be done instantly. Same with a board page, but only if posting a new thread.
                    if (fun.equals("sb_thread")
                            || (fun.equals("sb_board")
                                    && (int) array[1] == 1 // TODO: refactor array. массив здесь контекст, и параметр стоит явно назвать
                                    && request.getMethod().equals("POST")
                                    && request.getParameter("page") != null)) { // TODO: refactor $_POST['page'] тоже не стоит брать напрямую из запроса
                        action = ACT_IMMEDIATE;
                        break loop;
                    }
                    break;

                case STRATEGY_FIRST:
                    // My first, test strategy.
                    switch (fun) {
                        case "sb_thread":  // TODO: inc/functions.buildThread('sb_thread', array($board['uri'], $id))
                        case "sb_api":     // TODO: inc/functions.buildIndex('sb_api', array($board['uri']))
                        case "sb_catalog": // TODO: themes/catalog.catalog_build("sb_catalog", array($board))
                        case "sb_ukko":    // TODO: themes/ukko.ukko_build('sb_ukko', array())
                            action = ACT_DEFER;
                            break loop;
                        case "sb_board":   // TODO: inc/functions.buildIndex('sb_board', array($board['uri'], $page))
                            action = ((int) array[1] > 8) // TODO: refactor array
                                    ? ACT_BUILD_ON_LOAD : ACT_DEFER;
                            break loop;
                        case "sb_recent":  // TODO: themes/recent.build('sb_recent', array())
                        case "sb_sitemap": // TODO: themes/site_map.sitemap_build('sb_sitemap', array())
                            action = ACT_BUILD_ON_LOAD;
                            break loop;
                    }
            }
        }

        if (action == null) {
            return null;
        }

        switch (action) {
            case ACT_IMMEDIATE:
                return GenerationAction.REBUILD;
            case ACT_DEFER:
                // Ok, it gets interesting here :)
                // TODO: get_queue('generate')->push(serialize(array('build', $fun, $array, $action)));
                // TODO: Очередь используется только одним демоном /tools/worker.php, который из очереди дёргает функции ($fun, $array)
                // TODO: Можно заменить очередью из которой ест поток
                // TODO: Сделаю ближе к концу.
                return GenerationAction.IGNORE;
            case ACT_BUILD_ON_LOAD:
                return GenerationAction.DELETE;
        }

        return null; // TODO: CURRENT
    }

    //------------------------------------------------------------------------


}
