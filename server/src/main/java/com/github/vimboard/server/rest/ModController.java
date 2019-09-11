package com.github.vimboard.server.rest;

import com.github.vimboard.server.domain.templates.ConfigModel;
import com.github.vimboard.server.domain.templates.ModModel;
import com.github.vimboard.server.domain.templates.PageModel;
import com.github.vimboard.server.domain.templates.PersonalMessageModel;
import com.github.vimboard.version.ApplicationVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/mod.php")
public class ModController extends AbstractController {

    @Autowired
    public ModController(MessageSource messageSource) {
        super(messageSource);
    }

    @GetMapping("")
    public ModelAndView index() {
        final var params = new HashMap<String, Object>();

        final var configModel = new ConfigModel();
        configModel.setVersion(ApplicationVersion.get());
        params.put("config", configModel);

        params.put("page", new PageModel()
            //.setHideDashboardLink(true)
            .setMod(new ModModel())
            .setPm(new PersonalMessageModel()
                .setId(2234525534L)
                .setWaiting(15L))
            .setTitle(i18n("page.Dashboard")));

        // TODO mod_page(_('Dashboard'), 'mod/dashboard.html', $args);
        return new ModelAndView("page", params);
    }

    @GetMapping({"/", "/**"})
    public RedirectView redirectToIndex(HttpServletRequest request) {
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.SEE_OTHER);
        return new RedirectView("/mod.php?/");
    }
}
