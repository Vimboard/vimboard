package com.github.vimboard.controller;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class AbstractController {

    protected final MessageSource messageSource;

    public AbstractController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String i18n(String message) {
        final var currentLocale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, null, currentLocale);
    }
}
