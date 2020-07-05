package com.github.vimboard.model.domain;

/**
 * action to be taken.
 */
public enum GenerationAction {

    /**
     * Generate the page immediately.
     */
    REBUILD,

    /**
     * Defer page generation to a moment a worker daemon gets to build it
     * (serving a stale page in the meantime). The remaining arguments are
     * daemon-specific. Daemon isn't implemented yet :DDDD inb4
     * while(true) { generate(Queue::Get()) }; (which is probably it).
     */
    IGNORE,

    /**
     * Defer page generation to a moment, when the user actually accesses the
     * page. This is a smart_build behaviour. You shouldn't use this one too
     * much, if you use it for active boards, the server may choke due to
     * a possible race condition.
     * See my blog post: https://engine.vichan.net/blog/res/2.html
     */
    DELETE
}
