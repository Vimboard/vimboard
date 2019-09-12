package com.github.vimboard.server.controller;

import com.github.vimboard.server.model.ConfigModel;
import com.github.vimboard.server.model.ModModel;
import com.github.vimboard.server.model.PageModel;
import com.github.vimboard.server.model.PersonalMessageModel;
import com.github.vimboard.version.ApplicationVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/mod.php")
public class ModController extends AbstractController {

    @Autowired
    public ModController(MessageSource messageSource) {
        super(messageSource);
    }

    @RequestMapping({"/", "/**"})
    public String index(HttpServletRequest request) {
        return redirectToDashboard(request);
    }

    @RequestMapping("")
    public String root(HttpServletRequest request, Model model) {
        final String query = request.getQueryString();

        if (query == null || query.isEmpty()) {
            return redirectToDashboard(request);

        } else if (query.equals("/")) {
            return dashboard(model);
        }

        throw new ResourceBadRequestException();
    }

    // TODO mod_page(_('Dashboard'), 'mod/dashboard.html', $args);
    private String dashboard(Model model) {

        model.addAttribute("config", new ConfigModel()
                .setVersion(ApplicationVersion.get()));

        model.addAttribute("page", new PageModel()
                .setHideDashboardLink(true)
                .setMod(new ModModel())
                .setPm(new PersonalMessageModel()
                        .setId(2234525534L)
                        .setWaiting(15L))
                .setTitle(i18n("page.Dashboard")));

        return "page";
    }

    private String redirectToDashboard(HttpServletRequest request) {
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.SEE_OTHER);
        return "redirect:/mod.php?/";
    }
}
