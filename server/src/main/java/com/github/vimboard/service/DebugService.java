package com.github.vimboard.service;

import com.github.vimboard.model.mod.DebugHttpModel;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class DebugService {

    public DebugHttpModel buildDebugHttpModel(HttpServletRequest request) {

        if (request == null) {
            return null;
        }

        final DebugHttpModel model = new DebugHttpModel();

        // W4 HttpServletRequest
        final Map<String, String> properties = new LinkedHashMap<>(24);
        properties.put("RequestURL", request.getRequestURL().toString());
        properties.put("RequestURI", request.getRequestURI());
        properties.put("Scheme", request.getScheme());
        properties.put("AuthType", request.getAuthType());
        properties.put("Encoding", request.getCharacterEncoding());
        properties.put("ContentLength", "" + request.getContentLength());
        properties.put("ContentType", request.getContentType());
        properties.put("ContextPath", request.getContextPath());
        properties.put("Method", request.getMethod());
        properties.put("PathInfo", request.getPathInfo());
        properties.put("Protocol", request.getProtocol());
        properties.put("Query", request.getQueryString());
        properties.put("RemoteAddr", request.getRemoteAddr());
        properties.put("RemoteHost", request.getRemoteHost());
        properties.put("RemotePort", "" + request.getRemotePort());
        properties.put("RemoteUser", request.getRemoteUser());
        properties.put("SessionID", request.getRequestedSessionId());
        properties.put("ServerName", request.getServerName());
        properties.put("ServerPort", "" + request.getServerPort());
        properties.put("ServletPath", request.getServletPath());
        properties.put("DispatcherType", "" + request.getDispatcherType());
        properties.put("LocalAddr", request.getLocalAddr());
        properties.put("Locale", "" + request.getLocale());
        properties.put("LocalPort", "" + request.getLocalPort());
        model.setProperties(properties);

        // Headers
        final Map<String, String> headers = new HashMap<>();
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            final String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }
        model.setHeaders(headers);

        // Parameters
        final Map<String, String> parameters = new HashMap<>();
        final Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            final String paramName = parameterNames.nextElement();
            final String paramValue = request.getParameter(paramName);
            parameters.put(paramName, paramValue);
        }
        model.setParameters(parameters);

        // Cookies
        final List<Map<String, String>> cookies = new ArrayList<>();
        for (final Cookie c : request.getCookies()) {
            final Map<String, String> cookie = new LinkedHashMap<>(8);
            cookie.put("Name", c.getName());
            cookie.put("Comment", c.getComment());
            cookie.put("Domain", c.getDomain());
            cookie.put("Max age", "" + c.getMaxAge());
            cookie.put("Path", c.getPath());
            cookie.put("Secured", "" + c.getSecure());
            cookie.put("Value", c.getValue());
            cookie.put("Version", "" + c.getVersion());
            cookies.add(cookie);
        }
        model.setCookies(cookies);

        return model;
    }
}
