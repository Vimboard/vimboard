package com.github.vimboard.controller;

import com.github.vimboard.config.VimboardProperties;
import com.github.vimboard.config.VimboardVersion;
import com.github.vimboard.model.ConfigModel;
import com.github.vimboard.model.ModModel;
import com.github.vimboard.model.PageModel;
import com.github.vimboard.service.BoardService;
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

    private final BoardService boardService;
    private final VimboardProperties vimboardProperties;

    @Autowired
    public ModController(
            MessageSource messageSource,
            BoardService boardService,
            VimboardProperties vimboardProperties) {
        super(messageSource);
        this.boardService = boardService;
        this.vimboardProperties = vimboardProperties;
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

    private String dashboard(Model model) {

        //model.addAttribute("boards", boardService.list()); // todo

        //model.addAttribute("noticeboard",  // todo

        //model.addAttribute("unread_pms", // todo

        //model.addAttribute("reports", // todo

        //model.addAttribute("logout_token", // todo

        return modPage("mod/dashboard.ftl", model, i18n("page.Dashboard"), null);
    }

    // TODO move
    private String modPage(String bodyTemplate, Model model, String pageTitle, String pageSubTitle) {

        model.addAttribute("config", new ConfigModel()
                .setDefaultStylesheet(new String[] {
                        vimboardProperties.getAll().getDefaultStylesheet(),
                        vimboardProperties.getAll().getStylesheets().get(vimboardProperties.getAll().getDefaultStylesheet())
                })
                .setVersion(VimboardVersion.get()));

        model.addAttribute("page", new PageModel()
                .setBoardlist(boardService.buildBoardList())
                .setHideDashboardLink(bodyTemplate.equals("mod/dashboard.ftl"))
                .setMod(new ModModel()) // TODO
                .setTitle(pageTitle)
                .setSubtitle(pageSubTitle)
                );

        model.addAttribute("boardlist", "<hr><h4>"+ vimboardProperties.getAll().getBoards()/*.getClass().getName()*/ + "</h4><hr>");

        model.addAttribute("body", bodyTemplate);

        return "page";
    }

    private String redirectToDashboard(HttpServletRequest request) {
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.SEE_OTHER);
        return "redirect:/mod.php?/";
    }
}
