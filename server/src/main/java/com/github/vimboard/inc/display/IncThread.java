package com.github.vimboard.inc.display;

import com.github.vimboard.config.settings.VimboardBoardSettings;

import java.util.Collection;
import java.util.List;

public class IncThread {

    private final VimboardBoardSettings config;

    /**
     *
     * @param root TODO: null or value
     */
    public IncThread( $post, String root = null, $mod = false, $hr = true) {
        if (root == null) {
            root = config.getRoot();
        }

        for ()
        foreach ($post as $key => $value) {
            $this->{$key} = $value;
        }

        if (isset($this->files))
            $this->files = @json_decode($this->files);

        $this->subject = utf8tohtml($this->subject);
        $this->name = utf8tohtml($this->name);
        $this->mod = $mod;
        $this->root = $root;
        $this->hr = $hr;

        $this->posts = array();
        $this->omitted = 0;
        $this->omitted_images = 0;

        if ($this->embed)
            $this->embed = embed_html($this->embed);

        $this->modifiers = extract_modifiers($this->body_nomarkup);

        if ($config['always_regenerate_markup']) {
            $this->body = $this->body_nomarkup;
            markup($this->body);
        }

        if ($this->mod)
            // Fix internal links
            // Very complicated regex
            $this->body = preg_replace(
                    '/<a((([a-zA-Z]+="[^"]+")|[a-zA-Z]+=[a-zA-Z]+|\s)*)href="' . preg_quote($config['root'], '/') . '(' . sprintf(preg_quote($config['board_path'], '/'), $config['board_regex']) . ')/u',
                '<a $1href="?/$4',
                $this->body
			);
    }

    public List<Object> getPosts() {
        throw new RuntimeException("TODO getPosts"); // TODO: posts
    }
}
