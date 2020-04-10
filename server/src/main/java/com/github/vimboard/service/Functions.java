package com.github.vimboard.service;

public class Functions {

    public static String escapeMarkupModifiers(String string) {
        return preg_replace('@<(tinyboard) ([\w\s]+)>@mi', '<$1 escape $2>', $string);
    }

    public static String markup(String body) {
        return body; // TODO
    }
}
