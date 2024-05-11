package com.github.vimboard.inc.display;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vimboard.config.settings.VimboardBoardSettings;
import com.github.vimboard.controller.context.GlobalContext;
import com.github.vimboard.domain.Post;
import com.github.vimboard.service.FunctionsService;
import com.github.vimboard.service.types.BodyRef;
import com.github.vimboard.service.types.ServiceException;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class IncThread extends IncPost {

    private static final Logger logger = LoggerFactory.getLogger(IncThread.class);

    private final boolean mod;
    private final String root;
    private final boolean hr;

    private final List<IncPost> posts;
    private int omitted;
    private int omittedImages;
    private int images;
    private int replies;

    public IncThread(FunctionsService.Functions f, Post post) throws ServiceException {
        this(f, post, null, false, true);
    }

    public IncThread(FunctionsService.Functions f, Post post, String root) throws ServiceException {
        this(f, post, root, false, true);
    }

    public IncThread(FunctionsService.Functions f, Post post, String root, boolean mod) throws ServiceException {
        this(f, post, root, mod, true);
    }

    public IncThread(FunctionsService.Functions f, Post post, String root, boolean mod, boolean hr) throws ServiceException {
        super(f);
        final VimboardBoardSettings config = global.config;

        if (root == null) {
            root = config.getRoot();
        }

        setId(post.getId());
        setThread(post.getThread());
        setEmail(post.getEmail());
        setTrip(post.getTrip());
        setCapcode(post.getCapcode());
        setBodyNomarkup(post.getBodyNomarkup());
        setTime(post.getTime());
        setBump(post.getBump());
        setNumFiles(post.getNumFiles());
        setFilehash(post.getFilehash());
        setPassword(post.getPassword());
        setIp(post.getIp());
        setSticky(post.isSticky());
        setLocked(post.isLocked());
        setCycle(post.isCycle());
        setSage(post.isSage());
        setSlug(post.getSlug());

        if (!Strings.isEmpty(post.getFiles())) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                setFiles(mapper.readValue(post.getFiles(), new TypeReference<>(){}));
            } catch (JsonProcessingException ex) {
                logger.error("Invalid post files value", ex);
            }
        }

        setSubject(f.utf8ToHtml(post.getSubject()));
        setName(f.utf8ToHtml(post.getName()));
        this.mod = mod;
        this.root = root;
        this.hr = hr;

        posts = new ArrayList<>();
        omitted = 0;
        omittedImages = 0;

        if (!Strings.isEmpty(post.getEmbed())) {
            setEmbed(embedHtml(post.getEmbed()));
        }

        setModifiers(f.extractModifiers(getBodyNomarkup()));

        if (config.getAlwaysRegenerateMarkup()) {
            setBody(getBodyNomarkup());
            BodyRef bodyRef = new BodyRef(getBody());
            f.markup(bodyRef); // TODO: CURRENT ================================================
            setBody(bodyRef.body);
        }

        if (this.mod) {
            // Fix internal links
            // Very complicated regex
            setBody(fixInternalLinks(getBody(),
                    config.getRoot(),
                    config.getBoardPath(),
                    config.getBoardRegex()));
        }
    }

    public void add(IncPost post) {
        posts.add(post);
    }

    public String build(GlobalContext global) {
        return build(global, false, false);
    }

    public String build(GlobalContext global, boolean index) {
        return build(global, index, false);
    }

    public String build(GlobalContext global, boolean index, boolean isnoko50) {
//        final BoardModelFileboard board =
//                new BoardModelFileboard(global.boardModel);
//        final VimboardBoardSettings config = global.config;
//
//        final boolean hasnoko50 = (postCount() >= config.getNoko50Min());
//
//        global.events.event("show-thread", this);
//
//        String file = (index && config.getFileBoard())
//                ? "post_thread_fileboard.html" : "post_thread.html";
//        $built = Element($file, array('config' => $config, 'board' => $board, 'post' => &$this, 'index' => $index, 'hasnoko50' => $hasnoko50, 'isnoko50' => $isnoko50, 'mod' => $this->mod));
//
//        return $built;
        return null; // TODO: remove ==============================================
    }

    public List<IncPost> getPosts() {
        throw new RuntimeException("TODO getPosts"); // TODO: CURRENT ================================================
    }

    public int postCount() {
        return posts.size() + omitted;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public void setOmitted(int omitted) {
        this.omitted = omitted;
    }

    public void setOmittedImages(int omittedImages) {
        this.omittedImages = omittedImages;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public static String fixInternalLinks(String body,
            String root, String boardPath, String boardRegex) {
        final String[] pathParts = boardPath.split("\\{uri\\}");
        for (int i = 0; i < pathParts.length; i++) {
            if (Strings.isEmpty(pathParts[i])) {
                continue;
            }
            pathParts[i] = Pattern.quote(pathParts[i]);
        }
        final String boardPathRegex = String.join(boardRegex, pathParts);

        final String regex = "<a((([a-zA-Z]+=\"[^\"]+\")|[a-zA-Z]+=[a-zA-Z]+|\\s)*)href=\""
                + Pattern.quote(root) + "(" + boardPathRegex + ")";
        final String replacement = "<a$1href=\"?/$4";

        return body.replaceAll(regex, replacement);
    }
}
