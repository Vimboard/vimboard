package com.github.vimboard.service;

import com.github.vimboard.config.settings.VimboardSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiService {/* TODO: CURRENT

    private final VimboardSettings settings;

    public final Map<String, String> postFields;
    public final Map<String, String> threadsPageFields;

    @Autowired
    public ApiService(VimboardSettings settings) {
        this.settings = settings;

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

        threadsPageFields = new HashMap<>()
                'id' => 'no',
                'bump' => 'last_modified'
		);

        $this->fileFields = array(
                'thumbheight' => 'tn_h',
                'thumbwidth' => 'tn_w',
                'height' => 'h',
                'width' => 'w',
                'size' => 'fsize',
		);

        if (isset($config['api']['extra_fields']) && gettype($config['api']['extra_fields']) == 'array'){
            $this->postFields = array_merge($this->postFields, $config['api']['extra_fields']);
        }
    }

    private static $ints = array(
		'no' => 1,
                'resto' => 1,
                'time' => 1,
                'tn_w' => 1,
                'tn_h' => 1,
                'w' => 1,
                'h' => 1,
                'fsize' => 1,
                'omitted_posts' => 1,
                'omitted_images' => 1,
                'replies' => 1,
                'images' => 1,
                'sticky' => 1,
                'locked' => 1,
                'last_modified' => 1
    );

    private function translateFields($fields, $object, &$apiPost) {
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

    private function translateFile($file, $post, &$apiPost) {
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

    private function translatePost($post, $threadsPage = false) {
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

    function translateThread(Thread $thread, $threadsPage = false) {
        $apiPosts = array();
        $op = $this->translatePost($thread, $threadsPage);
        if (!$threadsPage) $op['resto'] = 0;
        $apiPosts['posts'][] = $op;

        foreach ($thread->posts as $p) {
            $apiPosts['posts'][] = $this->translatePost($p, $threadsPage);
        }

        return $apiPosts;
    }

    function translatePage(array $threads) {
        $apiPage = array();
        foreach ($threads as $thread) {
            $apiPage['threads'][] = $this->translateThread($thread);
        }
        return $apiPage;
    }

    function translateCatalogPage(array $threads, $threadsPage = false) {
        $apiPage = array();
        foreach ($threads as $thread) {
            $ts = $this->translateThread($thread, $threadsPage);
            $apiPage['threads'][] = current($ts['posts']);
        }
        return $apiPage;
    }

    function translateCatalog($catalog, $threadsPage = false) {
        $apiCatalog = array();
        foreach ($catalog as $page => $threads) {
            $apiPage = $this->translateCatalogPage($threads, $threadsPage);
            $apiPage['page'] = $page;
            $apiCatalog[] = $apiPage;
        }

        return $apiCatalog;
    }

*/
}