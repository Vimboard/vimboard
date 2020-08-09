package com.github.vimboard.controller.context;

import com.github.vimboard.model.domain.ModModel;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class HandlerContext {

    public HttpServletRequest request;
    public HttpServletResponse response;
    public ModModel modModel;
    public Model model;
    public Matcher matcher;
    public Map<String, Object> args;

    public Object get(String argKey) {
        return args.get(argKey);
    }

    public HandlerContext put(String argKey, Object argValue) {
        if (args == null) {
            args = new HashMap<>();
        }
        args.put(argKey, argValue);
        return this;
    }

    public HandlerContext setRequest(HttpServletRequest request) {
        this.request = request;
        return this;
    }

    public HandlerContext setResponse(HttpServletResponse response) {
        this.response = response;
        return this;
    }

    public HandlerContext setModModel(ModModel modModel) {
        this.modModel = modModel;
        return this;
    }

    public HandlerContext setModel(Model model) {
        this.model = model;
        return this;
    }

    public HandlerContext setMatcher(Matcher matcher) {
        this.matcher = matcher;
        return this;
    }

    public HandlerContext setArgs(Map<String, Object> args) {
        this.args = args;
        return this;
    }
}
