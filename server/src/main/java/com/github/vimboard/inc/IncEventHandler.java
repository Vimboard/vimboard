package com.github.vimboard.inc;

public interface IncEventHandler {

    /**
     * TODO
     *
     * @param args
     * @return сообщение об ошибке или {@code null}, если ошибок не было.
     */
    String call(Object... args);
}
