package com.github.vimboard.service;

import com.github.vimboard.config.SettingsBean;
import com.github.vimboard.domain.Pms;
import com.github.vimboard.model.domain.PmsModel;
import com.github.vimboard.repository.PmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PmsService {

    private final PmsRepository pmsRepository;
    private final SettingsBean settingsBean;

    @Autowired
    public PmsService(PmsRepository pmsRepository, SettingsBean settingsBean) {
        this.pmsRepository = pmsRepository;
        this.settingsBean = settingsBean;
    }

    public List<PmsModel> list(int mod) {
        final List<PmsModel> result = new ArrayList<>();
        for (Pms pms : pmsRepository.list(mod)) {
            result.add(new PmsModel()
                    .setId(pms.getId())
                    .setSender(pms.getSender())
                    .setTo(pms.getTo())
                    .setMessage(pms.getMessage())
                    .setTime(pms.getTime())
                    .setUnread(pms.isUnread())
                    .setUsername(pms.getUsername())
                    .setSnippet(snippet(pms.getMessage())));
        }
        return result;
    }

    public String snippet(String body) {
        return snippet(body, settingsBean.getAll().getMod().getSnippetLength());
    }

    public static String snippet(String body, int len) { // TODO test it

        // Replace line breaks with some whitespace
        body = body.replaceAll("(?i)\\<br/?\\>", "  ");

        // Strip tags
        body = body.replaceAll("\\<.*?\\>", "");

        // Unescape HTML characters, to avoid splitting them in half
        body = HtmlUtils.htmlUnescape(body);

        // calculate strlen() so we can add "..." after if needed
        int strlen = body.length();

        if (strlen > len) {
            body = body.substring(0, len);
        }

        // Re-escape the characters.
        return "<em>" + HtmlUtils.htmlEscape(body)
                + (strlen > len ? "&hellip;" : "") + "</em>";
    }
}
