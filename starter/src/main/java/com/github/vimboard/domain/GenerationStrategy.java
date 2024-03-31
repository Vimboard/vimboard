package com.github.vimboard.domain;

/**
 * Strategies for file generation. Also known as an "advanced build".
 */
public enum GenerationStrategy {

    /**
     * An immediate catch-all strategy. This is the default function of
     * imageboards: generate all pages on post time.
     */
    STRATEGY_IMMEDIATE,

    /**
     * NOT RECOMMENDED:
     * Instead of an all-"immediate" strategy, you can use
     * an all-"build_on_load" one (used to be initialized using
     * $config['smart_build']; ) for all pages instead of those to be build
     * immediately. A rebuild done in this mode should remove all your static
     * files.
     *
     * TODO $config['smart_build'] (disabled by default)
     */
    STRATEGY_SMART_BUILD,

    /**
     * A sane strategy. It forces to immediately generate a page user is about
     * to land on. Otherwise, it has no opinion, so it needs a fallback
     * strategy.
     */
    STRATEGY_SANE,

    /**
     * My (czaks) first, test strategy.
     */
    STRATEGY_FIRST
}
