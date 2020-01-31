package com.github.vimboard.controller;

import com.github.vimboard.config.SettingsBean;
import com.github.vimboard.config.VimboardVersion;
import com.github.vimboard.model.DashboardModel;
import com.github.vimboard.model.PageModel;
import com.github.vimboard.repository.BoardRepository;
import com.github.vimboard.service.BoardService;
import com.github.vimboard.service.SecurityService;
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

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final SecurityService securityService;
    private final SettingsBean settingsBean;

    @Autowired
    public ModController(
            MessageSource messageSource,
            BoardRepository boardRepository,
            BoardService boardService,
            SecurityService securityService,
            SettingsBean settingsBean) {
        super(messageSource);
        this.boardRepository = boardRepository;
        this.boardService = boardService;
        this.securityService = securityService;
        this.settingsBean = settingsBean;
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

        model.addAttribute("dashboard", new DashboardModel()
                .setBoards(boardRepository.list()));

        //model.addAttribute("noticeboard",  // todo

        //model.addAttribute("unread_pms", // todo

        //model.addAttribute("reports", // todo

        //model.addAttribute("logout_token", // todo

        return modPage("mod/dashboard.ftlh", model, i18n("page.Dashboard"), null);
    }

    // TODO move
    private String modPage(String bodyTemplate, Model model, String pageTitle, String pageSubTitle) {

        String dataStylesheet  = settingsBean.getAll().getStylesheets()
                .get(settingsBean.getAll().getDefaultStylesheet());
        if (dataStylesheet == null || dataStylesheet.isEmpty()) {
            dataStylesheet = "default"; // TODO: если нигде не используется, то можно перенести в загрузку конфига
        }

        model.addAttribute("config", settingsBean.getAll());

        model.addAttribute("mod", securityService.getMod());

        model.addAttribute("page", new PageModel()
                .setBoardlist(boardService.buildBoardList())
                .setDataStylesheet(dataStylesheet)
                .setHideDashboardLink(bodyTemplate.equals("mod/dashboard.ftlh"))
                .setTitle(pageTitle)
                .setSubtitle(pageSubTitle)
                .setVersion(VimboardVersion.get()));

        model.addAttribute("body", bodyTemplate);

        return "page";
    }

    private String redirectToDashboard(HttpServletRequest request) {
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.SEE_OTHER);
        return "redirect:/mod.php?/";
    }
}
