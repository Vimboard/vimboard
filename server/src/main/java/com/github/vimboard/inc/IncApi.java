package com.github.vimboard.inc;

import com.github.vimboard.config.settings.VimboardBoardSettings;
import com.github.vimboard.inc.display.IncPost;
import com.github.vimboard.inc.display.IncThread;

import java.util.*;

public class IncApi {

    private static Map<String, Integer> ints;

    {
        ints = new HashMap<>();
        ints.put("no", 1);
        ints.put("resto", 1);
        ints.put("time", 1);
        ints.put("tn_w", 1);
        ints.put("tn_h", 1);
        ints.put("w", 1);
        ints.put("h", 1);
        ints.put("fsize", 1);
        ints.put("omitted_posts", 1);
        ints.put("omitted_images", 1);
        ints.put("replies", 1);
        ints.put("images", 1);
        ints.put("sticky", 1);
        ints.put("locked", 1);
        ints.put("last_modified", 1);
    }

    private final VimboardBoardSettings config;
    private final Map<String, String> postFields;
    private final Map<String, String> threadsPageFields;
    private final Map<String, String> fileFields;

    public IncApi(VimboardBoardSettings config) {
        this.config = config;

        postFields = new HashMap<>();
        postFields.put("id", "no");
        postFields.put("thread", "resto");
        postFields.put("subject", "sub");
        postFields.put("body", "com");
        postFields.put("email", "email");
        postFields.put("name", "name");
        postFields.put("trip", "trip");
        postFields.put("capcode", "capcode");
        postFields.put("time", "time");
        postFields.put("omitted", "omitted_posts");
        postFields.put("omitted_images", "omitted_images");
        postFields.put("replies", "replies");
        postFields.put("images", "images");
        postFields.put("sticky", "sticky");
        postFields.put("locked", "locked");
        postFields.put("cycle", "cyclical");
        postFields.put("bump", "last_modified");
        postFields.put("embed", "embed");

        threadsPageFields = new HashMap<>();
        threadsPageFields.put("id", "no");
        threadsPageFields.put("bump", "last_modified");

        fileFields = new HashMap<>();
        fileFields.put("thumbheight", "tn_h");
        fileFields.put("thumbwidth", "tn_w");
        fileFields.put("height", "h");
        fileFields.put("width", "w");
        fileFields.put("size", "fsize");

        if (config.getApi().getExtraFields().size() > 0) {
            postFields.putAll(config.getApi().getExtraFields());
        }
    }
/* TODO: CURRENT
    private void translateFields($fields, $object, &$apiPost) {
        foreach ($fields as $local => $translated) {
            if (!isset($object->$local))
                continue;

            $toInt = isset(self::$ints[$translated]);
            $val = $object->$local;
            if ($val !== null && $val !== '') {
                $apiPost[$translated] = $toInt ? (int) $val : $val;
            }

        }
    }

    private void translateFile($file, $post, &$apiPost) {
        $this->translateFields($this->fileFields, $file, $apiPost);
        $apiPost['filename'] = @substr($file->name, 0, strrpos($file->name, '.'));
        $dotPos = strrpos($file->file, '.');
        $apiPost['ext'] = substr($file->file, $dotPos);
        $apiPost['tim'] = substr($file->file, 0, $dotPos);
        if (isset ($file->hash) && $file->hash) {
            $apiPost['md5'] = base64_encode(hex2bin($file->hash));
        }
        else if (isset ($post->filehash) && $post->filehash) {
            $apiPost['md5'] = base64_encode(hex2bin($post->filehash));
        }
    }

    private Map<String, Object> translatePost(IncThread post) {
        return translatePost(post, false);
    }

    private Map<String, Object> translatePost(IncThread post, boolean threadsPage) {
        global $config, $board;
        $apiPost = array();
        $fields = $threadsPage ? $this->threadsPageFields : $this->postFields;
        $this->translateFields($fields, $post, $apiPost);

        if (isset($config['poster_ids']) && $config['poster_ids']) $apiPost['id'] = poster_id($post->ip, $post->thread, $board['uri']);
        if ($threadsPage) return $apiPost;

        // Handle country field
        if (isset($post->body_nomarkup) && $this->config['country_flags']) {
            $modifiers = extract_modifiers($post->body_nomarkup);
            if (isset($modifiers['flag']) && isset($modifiers['flag alt']) && preg_match('/^[a-z]{2}$/', $modifiers['flag'])) {
                $country = strtoupper($modifiers['flag']);
                if ($country) {
                    $apiPost['country'] = $country;
                    $apiPost['country_name'] = $modifiers['flag alt'];
                }
            }
        }

        if ($config['slugify'] && !$post->thread) {
            $apiPost['semantic_url'] = $post->slug;
        }

        // Handle files
        // Note: 4chan only supports one file, so only the first file is taken into account for 4chan-compatible API.
        if (isset($post->files) && $post->files && !$threadsPage) {
            $file = $post->files[0];
            $this->translateFile($file, $post, $apiPost);
            if (sizeof($post->files) > 1) {
                $extra_files = array();
                foreach ($post->files as $i => $f) {
                    if ($i == 0) continue;

                    $extra_file = array();
                    $this->translateFile($f, $post, $extra_file);

                    $extra_files[] = $extra_file;
                }
                $apiPost['extra_files'] = $extra_files;
            }
        }

        return $apiPost;
    }

    public Map<String, Object> translateThread(IncThread thread) {
        return translateThread(thread, false);
    }

    // TODO: apiPosts structure
    public Map<String, Object> translateThread(IncThread thread, boolean threadsPage) {
        Map<String, Object> apiPosts = new HashMap<>();

        Map<String, Object> op = translatePost(thread, threadsPage);
        if (!threadsPage) {
            op.put("resto", 0);
        }

        List<Map<String, Object>> posts = new ArrayList<>();
        posts.add(op);
        foreach ($thread->posts as $p) {
            $apiPosts['posts'][] = $this->translatePost($p, $threadsPage);
        } // TODO
        for (IncPost p : thread.getPosts()) {
            posts.add(translatePost(p, threadsPage));
        }
        apiPosts.put("posts", posts);

        return apiPosts;
    }

    // TODO: apiPage structure
    public Map<String, Object> translatePage(List<IncThread> threadList) {
        Map<String, Object> apiPage = new HashMap<>();
        List<Map<String, Object>> threads = new ArrayList<>();
        for (IncThread thread : threadList) {
            threads.add(translateThread(thread));
        }
        apiPage.put("threads", threads);
        return apiPage;
    }

    public void translateCatalogPage(array $threads, $threadsPage = false) {
        $apiPage = array();
        foreach ($threads as $thread) {
            $ts = $this->translateThread($thread, $threadsPage);
            $apiPage['threads'][] = current($ts['posts']);
        }
        return $apiPage;
    }

    public void translateCatalog($catalog, $threadsPage = false) {
        $apiCatalog = array();
        foreach ($catalog as $page => $threads) {
            $apiPage = $this->translateCatalogPage($threads, $threadsPage);
            $apiPage['page'] = $page;
            $apiCatalog[] = $apiPage;
        }

        return $apiCatalog;
    }*/
}
