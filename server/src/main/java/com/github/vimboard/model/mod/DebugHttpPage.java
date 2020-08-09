package com.github.vimboard.model.mod;

import java.util.List;
import java.util.Map;

public class DebugHttpPage {

    private Map<String, String> properties;
    private List<Map<String, String>> cookies;
    private Map<String, String> headers;
    private Map<String, String> parameters;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public Map<String, String> getProperties() {
        return properties;
    }

    public DebugHttpPage setProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    public List<Map<String, String>> getCookies() {
        return cookies;
    }

    public DebugHttpPage setCookies(List<Map<String, String>> cookies) {
        this.cookies = cookies;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public DebugHttpPage setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public DebugHttpPage setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }
}
