package com.github.vimboard.service;

import com.github.vimboard.config.settings.VimboardBoardSettings;
import com.github.vimboard.config.settings.VimboardSettings;
import com.github.vimboard.controller.context.GlobalContext;
import com.github.vimboard.domain.Board;
import com.github.vimboard.domain.CitedPost;
import com.github.vimboard.domain.GenerationStrategy;
import com.github.vimboard.domain.NumPosts;
import com.github.vimboard.model.BoardModel;
import com.github.vimboard.model.BoardModelFileboard;
import com.github.vimboard.model.ContentModel;
import com.github.vimboard.model.GenerationAction;
import com.github.vimboard.repository.BoardRepository;
import com.github.vimboard.repository.PostRepository;
import com.github.vimboard.service.types.BodyRef;
import com.github.vimboard.service.types.PostLF;
import com.github.vimboard.service.types.ServiceException;
import org.apache.logging.log4j.util.Strings;
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
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.web.util.HtmlUtils.htmlEscape;

@Service
public class FunctionsService {

    public class Functions {

        public final GlobalContext global;

        public Functions(GlobalContext global) {
            this.global = global;
        }

        /**
         * Помещает в контекст указанную доску и её конфиг.
         *
         * @param board
         * @throws ServiceException
         */
        private void setupBoard(Board board)
                throws ServiceException {
            final String uri = board.getUri();
            final VimboardBoardSettings config = settings.getCustom(uri);

            global.setBoard(board)
                    .setConfig(config);

            final BoardModel boardModel = new BoardModel(board);

            final String dir = config.getBoardPath().replace("{uri}", uri);
            boardModel.setDir(dir);
            boardModel.setUrl(config.getBoardAbbreviation().replace("{uri}", uri));
            global.setBoardModel(boardModel);

            // loadConfig(); TODO switch from AllBoards() to CustomBoard() ---> global.setConfig

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
         * @return {@code true} если в контексте находится доска, uri которой
         * совпадает с uri из контекста.
         * @throws ServiceException
         */
        public boolean openBoard()
                throws ServiceException {
            Board board = global.board;
            final String uri = global.uri;

            if (settings.getAll().getTrySmarter()) {
                global.setBuildPages(new ArrayList<>());
            }

            if (board != null && board.getUri().equals(uri)) {
                return true;
            }

            board = boardRepository.findByUri(uri);
            if (board != null) {
                setupBoard(board);

                // TODO: call after_open_board
                // TODO: Эта функция существует только в /smart_build.php и /tools/worker.php

                return true;
            }

            return false;
        }

        private Object index(int page) {
            return index(page, false, false);
        }

        private Object index(int page, boolean mod) {
            return index(page, mod, false);
        }

        // brief means that we won't need to generate anything yet
        private ContentModel index(int page, boolean mod, boolean brief) {
            final BoardModelFileboard board =
                    new BoardModelFileboard(global.boardModel);
            final VimboardBoardSettings config = global.config;

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
                IncThread thread = new IncThread(global, th, mod ? "?/" : config.getRoot(), mod);  // TODO: CURRENT ========================================================

                List<Post> posts = postRepository.listPosts(
                        board.getUri(), th.getId(), (th.isSticky() ? config.getThreadsPreviewSticky() : config.getThreadsPreview()));

                List<Post> replies = new ArrayList<>(posts);
                Collections.reverse(replies);

                final Map omitted;
                if (replies.size() == ((th.isSticky() ? config.getThreadsPreviewSticky() : config.getThreadsPreview()))) {
                    NumPosts count = numPosts(global, th.getId());
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

                    thread.add(new IncPost(global, po, mod ? "?/" : config.getRoot(), mod));
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
                    body.append(thread.build(global, true)); // TODO: WAIT FOR DEV ===================================================
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
            );
        }

        // Returns an associative array with 'replies' and 'images' keys
        public NumPosts numPosts(long id) {
            final BoardModelFileboard board =
                    new BoardModelFileboard(global.boardModel);

            return postRepository.numPosts(board.getUri(), id);
        }

        public void buildIndex() {
            buildIndex("yes");
        }

        /**
         * @param globalApi TODO: true = "yes", false = "skip"
         */
        public void buildIndex(String globalApi) {
        final BoardModel board = global.boardModel;
        final VimboardBoardSettings config =
                settings.getCustom(board.getUri());

        final GenerationAction catalogApiAction = generationStrategy(
                "sb_api",
                new Object[] { board.getUri() },
                global.handlerContext.request);

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
                    && global.buildPages != null
                    && !global.buildPages.isEmpty()
                    && !global.buildPages.contains(page);

            if ((!config.getApi().getEnabled() || globalApi.equals("skip"))
                    && wontBuildThisPage) {
                continue;
            }

            GenerationAction action = generationStrategy(
                    "sb_board",
                    new Object[] { board.getUri(), page },
                    global.handlerContext.request);
            if (action.equals(GenerationAction.REBUILD)
                    || catalogApiAction.equals(GenerationAction.REBUILD)) {
                ContentModel content = index(page, false, wontBuildThisPage); // TODO: CURRENT <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
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
        }


        public String markupUrl(MatchResult matches,
                List<String> markupUrls) {
            final VimboardBoardSettings config = global.config;

            String url = matches.group(1);
            String after = matches.group(2);

            markupUrls.add(url);

            Map<String, String> link = new HashMap<>();
            link.put("href", config.getLinkPrefix() + url);
            link.put("text", url);
            link.put("rel", "nofollow");
            link.put("target", "_blank");

            global.events.event("markup-url", link); // TODO:

            List<String> parts = new ArrayList<>();

            for (String attr : link.keySet()) {
                final String value = link.get(attr);
                if (attr.equals("text") || attr.equals("after")) {
                    continue;
                }
                parts.add(attr + "=\"" + value + "\"");
            }
            if (link.get("after") != null) {  // TODO: можно добавить только в событии
                after = link.get("after") + after;
            }
            return "<a " + String.join(" ", parts) + ">" + link.get("text") + "</a>" + after;
        }

        public String unicodify(String body) {
            body = body.replace("...", "&hellip;");
            body = body.replace("&lt;--", "&larr;");
            body = body.replace("--&gt;", "&rarr;");

            // En and em- dashes are rendered exactly the same in
            // most monospace fonts (they look the same in code
            // editors).
            body = body.replace("---", "&mdash;"); // em dash
            body = body.replace("--", "&ndash;"); // en dash

            return body;
        }

        public Map<String, String> extractModifiers(String body) {
            Map<String, String> modifiers = new HashMap<>();

            Pattern p = Pattern.compile("<tinyboard ([\\w\\s]+)>(.*?)</tinyboard>");
            Matcher m = p.matcher(body);
            while (m.find()) {
                final String group1 = m.group(1);
                if (group1.startsWith("escape ")) {
                    continue;
                }
                modifiers.put(group1, HtmlUtils.htmlUnescape(m.group(2)));
            }

            return modifiers;
        }

        public String removeModifiers(String body) {
            return body
                    .replaceAll("<tinyboard ([\\w\\s]+)>(.*?)</tinyboard>", "");
        }

        public ArrayList<String> markup(BodyRef bodyRef)
                throws ServiceException {
            return markup(bodyRef, false, false);
        }

        public ArrayList<String> markup(BodyRef bodyRef,
                boolean trackCites) throws ServiceException {
            return markup(bodyRef, trackCites, false);
        }

        public ArrayList<String> markup(BodyRef bodyRef,
                boolean trackCites, boolean op) throws ServiceException {
            //global $board, $config, $markup_urls;
            final BoardModel board = global.boardModel;
            final VimboardBoardSettings config =
                    settings.getCustom(board.getUri());

            Map<String, String> $modifiers = extractModifiers(bodyRef.body);

            bodyRef.body = removeMarkupModifiersString(bodyRef.body);
            bodyRef.body = unescapeMarkupModifiers(bodyRef.body);

            if ($modifiers.containsKey("raw html")
                    && $modifiers.get("raw html").equals("1")) {
                return new ArrayList<>();
            }

            bodyRef.body = bodyRef.body.replace("\r", "");
            bodyRef.body = utf8ToHtml(bodyRef.body);

            List<MatchResult> codeMarkup = new ArrayList<>();
            if (!Strings.isEmpty(config.getMarkupCode())) {
                Pattern p = Pattern.compile(config.getMarkupCode());
                Matcher m = p.matcher(bodyRef.body);
                bodyRef.body = m.replaceAll(matchResult -> {
                    int d = codeMarkup.size();
                    codeMarkup.add(matchResult);
                    return "<code $" + d + ">";
                });
            }

            for (String[] markup : config.getMarkup()) {
                // TODO: каллбеков в конфиге нет
                bodyRef.body = bodyRef.body.replaceAll(markup[0], markup[1]);
            }

            List<String> markupUrls = new ArrayList<>();
            if (config.getMarkupUrls()) {

                // TODO: шаблон вызывает вопросы, но пока трогать не будем
                Pattern p = Pattern.compile("((?:https?://|ftp://|irc://)[^\\s<>()\"]+?(?:\\([^\\s<>()\"]*?\\)[^\\s<>()\"]*?)*)((?:\\s|<|>|\"|\\.||\\]|!|\\?|,|&#44;|&quot;)*(?:[\\s<>()\"]|$))");
                Matcher m = p.matcher(bodyRef.body);
                bodyRef.body = m.replaceAll(matchResult ->
                        markupUrl(matchResult, markupUrls));
                int numLinks = markupUrls.size();

                if (numLinks > config.getMaxLinks()) {
                    throw new ServiceException("error.toomanylinks");
                }
            }

            if (config.getMarkupRepairTidy()) {
                bodyRef.body = bodyRef.body.replace("  ", " &nbsp;");
            }

            if (config.getAutoUnicode()) {
                bodyRef.body = unicodify(bodyRef.body);

                if (config.getMarkupUrls()) {
                    for (String url : markupUrls) {
                        bodyRef.body = bodyRef.body.replace(unicodify(url), url);
                    }
                }
            }

            $tracked_cites = array();

            // Cites
            final List<List<List<Object>>> cites = new ArrayList<>();
            if (board != null) {
                Pattern p = Pattern.compile("(?m)(^|\\s)&gt;&gt;(\\d+?)([\\s,.)?]|$)");
                Matcher m = p.matcher(bodyRef.body);
                while (m.find()) {
                    List<List<Object>> matches = new ArrayList<>();

                    List<Object> group = new ArrayList<>();
                    group.add(m.group());
                    group.add(m.start());
                    matches.add(group);

                    List<Object> group1 = new ArrayList<>();
                    group1.add(m.group(1));
                    group1.add(m.start(1));
                    matches.add(group1);

                    List<Object> group2 = new ArrayList<>();
                    group2.add(m.group(2));
                    group2.add(m.start(2));
                    matches.add(group2);

                    List<Object> group3 = new ArrayList<>();
                    group3.add(m.group(3));
                    group3.add(m.start(3));
                    matches.add(group3);

                    cites.add(matches);
                }

                if (cites.size() > config.getMaxCites()) {
                    throw new ServiceException("error.'toomanycites'");
                }

                int skipChars = 0;
                String bodyTmp = bodyRef.body;

                Set<Long> searchCites = new HashSet<>();
                for (List<List<Object>> matches : cites) {
                    String cite = (String) matches.get(2).get(0);
                    searchCites.add(Long.parseLong(cite));
                }

                Map<Long, Long> citedPosts = postRepository
                        .mapCitedPosts(board.getUri(), searchCites);

                for (List<List<Object>> matches : cites) {
                    long cite = Long.parseLong((String) matches.get(2).get(0));

                    // preg_match_all is not multibyte-safe
                    for (List<Object> match : matches) {
                        // TODO: не знаю, как в PHP, но в Java match[1] не изменится:
                        match.set(1, bodyTmp.substring(0, (Integer) match.get(1)).length());
                    }

                    if (citedPosts.get(cite) != null) {
                        final Map<String, Object> post = new HashMap<>();
                        post.put("id", cite);
                        post.put("thread", citedPosts.get(cite));

                        final String replacement = "<a onclick=\"highlightReply('" + cite + "', event);\" href=\""
                                + config.getRoot() + board.getDir() + config.getDir().getRes()
                                + linkFor(post) + "#" + cite + "\">" +
                                "&gt;&gt;" + cite +
                                "</a>";

                        $body = mb_substr_replace($body, $matches[1][0] + $replacement + $matches[3][0], $matches[0][1] + $skip_chars, mb_strlen($matches[0][0]));
                        $skip_chars += mb_strlen($matches[1][0].$replacement.$matches[3][0]) - mb_strlen($matches[0][0]);

                        if ($track_cites && $config['track_cites'])
                            $tracked_cites[] =array($board['uri'], $cite);
                    }
                }
            }

            // Cross-board linking
            if (preg_match_all('/(^|\s)&gt;&gt;&gt;\/('.$config['board_regex'].
            'f?)\/(\d+)?([\s,.)?]|$)/um', $body, $cites, PREG_SET_ORDER | PREG_OFFSET_CAPTURE))
            {
                if (count($cites[0]) > $config['max_cites']) {
                    error($config['error']['toomanycross']);
                }

                $skip_chars = 0;
                $body_tmp = $body;

                if (isset($cited_posts)) {
                    // Carry found posts from local board >>X links
                    foreach($cited_posts as $cite = > $thread){
                        $cited_posts[$cite] = $config['root'].$board['dir'].$config['dir']['res'].
                        ($thread ? $thread : $cite). '.html#'.$cite;
                    }

                    $cited_posts = array(
                            $board['uri'] = > $cited_posts
                );
                } else
                    $cited_posts = array();

                $crossboard_indexes = array();
                $search_cites_boards = array();

                foreach($cites as $matches) {
                $_board = $matches[2][0];
                $cite = @$matches[3][0];

                if (!isset($search_cites_boards[$_board]))
                    $search_cites_boards[$_board] = array();
                $search_cites_boards[$_board][] =$cite;
            }

                $tmp_board = $board['uri'];

                foreach($search_cites_boards as $_board = > $search_cites){
                $clauses = array();
                foreach($search_cites as $cite) {
                    if (!$cite || isset($cited_posts[$_board][$cite]))
                        continue;
                    $clauses[] ='`id` = '.$cite;
                }
                $clauses = array_unique($clauses);

                if ($board['uri'] != $_board) {
                    if (!openBoard($_board))
                        continue; // Unknown board
                }

                if (!empty($clauses)) {
                    $cited_posts[$_board] = array();

                    $query = query(sprintf('SELECT `thread`, `id`, `slug` FROM ``posts_%s`` WHERE '.
                            implode(' OR ', $clauses), $board['uri'])) or error
                    (db_error());

                    while ($cite = $query -> fetch(PDO::FETCH_ASSOC)) {
                        $cited_posts[$_board][$cite['id']] = $config['root'].$board['dir'].$config['dir']['res'].
                                link_for($cite). '#'.$cite['id'];
                    }
                }

                $crossboard_indexes[$_board] = $config['root'].$board['dir'].$config['file_index'];
            }

                // Restore old board
                if ($board['uri'] != $tmp_board)
                    openBoard($tmp_board);

                foreach($cites as $matches) {
                $_board = $matches[2][0];
                $cite = @$matches[3][0];

                // preg_match_all is not multibyte-safe
                foreach($matches as & $match) {
                    $match[1] = mb_strlen(substr($body_tmp, 0, $match[1]));
                }

                if ($cite) {
                    if (isset($cited_posts[$_board][$cite])) {
                        $link = $cited_posts[$_board][$cite];

                        $replacement = '<a '.
                        ($_board == $board['uri'] ?
                                'onclick="highlightReply(\''.$cite.
                        '\', event);" '
                            :'') .'href="'.$link. '">'.
                        '&gt;&gt;&gt;/'.$_board. '/'.$cite.
                        '</a>';

                        $body = mb_substr_replace($body, $matches[1][0].$replacement.$matches[4][0], $matches[0][1] + $skip_chars, mb_strlen($matches[0][0]));
                        $skip_chars += mb_strlen($matches[1][0].$replacement.$matches[4][0]) - mb_strlen($matches[0][0]);

                        if ($track_cites && $config['track_cites'])
                            $tracked_cites[] =array($_board, $cite);
                    }
                } elseif(isset($crossboard_indexes[$_board])) {
                    $replacement = '<a href="'.$crossboard_indexes[$_board].
                    '">'.
                    '&gt;&gt;&gt;/'.$_board. '/'.
                    '</a>';
                    $body = mb_substr_replace($body, $matches[1][0].$replacement.$matches[4][0], $matches[0][1] + $skip_chars, mb_strlen($matches[0][0]));
                    $skip_chars += mb_strlen($matches[1][0].$replacement.$matches[4][0]) - mb_strlen($matches[0][0]);
                }
            }
            }

            $tracked_cites = array_unique($tracked_cites, SORT_REGULAR);

            $body = preg_replace("/^\s*&gt;.*$/m", '<span class="quote">$0</span>', $body);

            if ($config['strip_superfluous_returns'])
                $body = preg_replace('/\s+$/', '', $body);

            $body = preg_replace("/\n/", '<br/>', $body);

            // Fix code markup
            if ($config['markup_code']) {
                foreach($code_markup as $id = > $val){
                    $code = isset($val[2]) ? $val[2] : $val[1];
                    $code_lang = isset($val[2]) ? $val[1] : "";

                    $code = "<pre class='code lang-$code_lang'>".str_replace(array("\n", "\t"), array("&#10;", "&#9;"), htmlspecialchars($code)).
                    "</pre>";

                    $body = str_replace("<code $id>", $code, $body);
                }
            }

            if ($config['markup_repair_tidy']) {
                $tidy = new tidy();
                $body = str_replace("\t", '&#09;', $body);
                $body = $tidy -> repairString($body, array(
                                'doctype' = > 'omit',
                        'bare' = > true,
                        'literal-attributes' =>true,
                        'indent' =>false,
                        'show-body-only' =>true,
                        'wrap' =>0,
                        'output-bom' =>false,
                        'output-html' =>true,
                        'newline' =>'LF',
                        'quiet' =>true,
            ),'utf8');
                $body = str_replace("\n", '', $body);
            }

            // replace tabs with 8 spaces
            $body = str_replace("\t", '        ', $body);

            return $tracked_cites;
        }

        public String escapeMarkupModifiers(String string) {
            return string.replaceAll(
                    "(?mi)<(tinyboard) ([\\w\\s]+)>", //mi - multiline, insensitive
                    "<$1 escape $2>");
        }

        public String removeMarkupModifiersString(String string) {
            return string.replaceAll(
                    "<tinyboard (?!escape )([\\w\\s]+)>(.+?)</tinyboard>", //us - unicode, single line
                    "");
        }

        public String unescapeMarkupModifiers(String string) {
            return string.replaceAll(
                    "(?i)<(tinyboard) escape ([\\w\\s]+)>", //i - insensitive
                    "<$1 $2>");
        }

        public String utf8ToHtml(String string) {
            return htmlEscape(string);
        }

        public String linkFor(Map<String, Object> post) {
            return linkFor(post, false, null, false);
        }

        public String linkFor(Map<String, Object> post, boolean page50) {
            return linkFor(post, page50, null, false);
        }

        public String linkFor(Map<String, Object> post, boolean page50,
                Board foreignlink) {
            return linkFor(post, page50, foreignlink, false);
        }

        public String linkFor(PostLF post, boolean page50,
                Board foreignlink, boolean thread) {
            final BoardModel board = global.boardModel;
            final VimboardBoardSettings config =
                    settings.getCustom(board.getUri());

            // Where do we need to look for OP?
            Board b = foreignlink != null
                    ? foreignlink
                    : (post.getBoard() != null)
                            ? new Board().setUri((String) post.getBoard())
                            : board;

            id = (post.get("thread") != null && post.get("thread")) ? post.get("thread") : post.get("id");

            $slug = false;

            if ($config['slugify'] && ( (isset($post['thread']) && $post['thread']) || !isset ($post['slug']) ) ) {
                $cvar = "slug_".$b['uri']."_".$id;
                if (!$thread) {
                    $slug = Cache::get($cvar);

                    if ($slug === false) {
                        $query = prepare(sprintf("SELECT `slug` FROM ``posts_%s`` WHERE `id` = :id", $b['uri']));
                        $query->bindValue(':id', $id, PDO::PARAM_INT);
                        $query->execute() or error(db_error($query));

                        $thread = $query->fetch(PDO::FETCH_ASSOC);

                        $slug = $thread['slug'];

                        Cache::set($cvar, $slug);
                    }
                }
                else {
                    $slug = $thread['slug'];
                }
            }
            elseif ($config['slugify']) {
                $slug = $post['slug'];
            }


            if ( $page50 &&  $slug)  $tpl = $config['file_page50_slug'];
            else if (!$page50 &&  $slug)  $tpl = $config['file_page_slug'];
            else if ( $page50 && !$slug)  $tpl = $config['file_page50'];
            else if (!$page50 && !$slug)  $tpl = $config['file_page'];

            return sprintf($tpl, $id, $slug);
        }

        /**
         * TODO
         *
         * @param fun     идентификатор стратегии и имя функции для построения через очередь.
         * @param array   ??? Какой-то контекст. 0 элемент - uri доски. 1 элемент - какая-то цифра
         * @param request
         * @return TODO null or GenerationAction
         */
        public GenerationAction generationStrategy(final String fun, Object[] array,
                final HttpServletRequest request) {
            final String ACT_BUILD_ON_LOAD = "build_on_load";
            final String ACT_DEFER = "defer";
            final String ACT_IMMEDIATE = "immediate";

            String action = null;

            loop:
            for (GenerationStrategy gs :
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

        //--------------------------------------------------------------------

    }

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final SecurityService securityService;
    private final VimboardSettings settings;

    @Autowired
    public FunctionsService(
            BoardRepository boardRepository,
            PostRepository postRepository,
            SecurityService securityService,
            VimboardSettings settings) {
        this.boardRepository = boardRepository;
        this.postRepository = postRepository;
        this.securityService = securityService;
        this.settings = settings;
    }

    public Functions create(GlobalContext globalContext) {
        return new Functions(globalContext);
    }
}
