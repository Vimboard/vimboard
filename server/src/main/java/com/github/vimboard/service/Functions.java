package com.github.vimboard.service;

public class Functions {

    public static String escapeMarkupModifiers(String string) {
        return string.replaceAll("(?i)<(tinyboard) ([\\w\\s]+)>",
                "<$1 escape $2>");
    }

    public static String markup(String body) {
        return body; // TODO
    }
}
