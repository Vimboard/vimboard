package com.github.vimboard.service;

import com.github.vimboard.inc.IncEventHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: На пыхе ивенты - это аналог плагинов. Тут они не нужны. Тупо для облегчения портации.
 */
@Service
public class Events {

    private Map<String, List<IncEventHandler>> events; // TODO: сделать final, убрать resetEvents

    /**
     * TODO: На пыхе здесь добавляются хандлеры во время чтения конфига.
     */
    public Events() {
        resetEvents();
    }

    /**
     * TODO: вызов события.
     */
    public String event(String event, Object... args) throws ServiceException {
        List<IncEventHandler> handlers = events.get(event);

        if (event == null) {
            return null;
        }

        for (IncEventHandler h : handlers) {
            if (h == null) {
                throw new ServiceException("Event handler for " + event + " is not callable!");
            } else {
                String error = h.call(args);
                if (error != null) {
                    return error;
                }
            }
        }

        return null;
    }

    /**
     * TODO: метод для добавления хандлера.
     */
    public void eventHandler(String event, IncEventHandler callback) {
        List<IncEventHandler> handlers =
                events.computeIfAbsent(event, e -> new ArrayList<>());

        handlers.add(callback);
    }

    /**
     * TODO: убрать
     */
    private void resetEvents() {
        events = new HashMap<>();
    }
}
