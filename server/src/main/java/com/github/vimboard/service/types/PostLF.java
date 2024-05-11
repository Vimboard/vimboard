package com.github.vimboard.service.types;

import com.github.vimboard.domain.Board;
import com.github.vimboard.domain.Post;

public class PostLF extends Post {

    private String board;

    public PostLF(Post post) {
        setId(post.getId());
        setThread(post.getThread());
        setSubject(post.getSubject());
        setEmail(post.getEmail());
        setName(post.getName());
        setTrip(post.getTrip());
        setCapcode(post.getCapcode());
        setBody(post.getBody());
        setBodyNomarkup(post.getBodyNomarkup());
        setTime(post.getTime());
        setBump(post.getBump());
        setFiles(post.getFiles());
        setNumFiles(post.getNumFiles());
        setFilehash(post.getFilehash());
        setPassword(post.getPassword());
        setIp(post.getIp());
        setSticky(post.isSticky());
        setLocked(post.isLocked());
        setCycle(post.isCycle());
        setSage(post.isSage());
        setEmbed(post.getEmbed());
        setSlug(post.getSlug());
        this.board = null;
    }

    public PostLF(Post post, String board) {
        this(post);
        this.board = board;
    }

    public PostLF setBoard(String board) {
        this.board = board;
        return this;
    }
}
