package com.github.vimboard.controller;

import com.github.vimboard.config.SettingsBean;
import com.github.vimboard.model.DashboardModel;
import com.github.vimboard.model.LoginModel;
import com.github.vimboard.model.PageModel;
import com.github.vimboard.model.Release;
import com.github.vimboard.repository.BoardRepository;
import com.github.vimboard.repository.NoticeboardRepository;
import com.github.vimboard.repository.PmsRepository;
import com.github.vimboard.repository.ReportRepository;
import com.github.vimboard.service.BoardService;
import com.github.vimboard.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/mod.php")
public class ModController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(ModController.class);

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final NoticeboardRepository noticeboardRepository;
    private final PmsRepository pmsRepository;
    private final ReportRepository reportRepository;
    private final SecurityService securityService;
    private final SettingsBean settingsBean;

    @Autowired
    public ModController(
            MessageSource messageSource,
            BoardRepository boardRepository,
            BoardService boardService,
            NoticeboardRepository noticeboardRepository,
            PmsRepository pmsRepository,
            ReportRepository reportRepository,
            SecurityService securityService,
            SettingsBean settingsBean) {
        super(messageSource);
        this.boardRepository = boardRepository;
        this.boardService = boardService;
        this.noticeboardRepository = noticeboardRepository;
        this.pmsRepository = pmsRepository;
        this.reportRepository = reportRepository;
        this.securityService = securityService;
        this.settingsBean = settingsBean;
    }

    @RequestMapping(value = {"/", "/**"})
    public String index(HttpServletRequest request) {
        return redirectToDashboard(request, null);
    }

    @RequestMapping(value = "")
    public String root(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        if (securityService.isAnonymous()) {
            return login(request, response, model, null);
        }

        model.addAttribute("mod", securityService.getMod());

        String query = request.getQueryString();
        if (query == null || query.isEmpty()) {
            query = "/";
        }
        switch (query) {
            case "/":
                return dashboard(model);
            case "/logout":
            case "/logout/":
                return logout(request, response, model);
        }

        throw new ResourceBadRequestException();
    }

    //------------------------------------------------------------------------

    private String dashboard(Model model) {
        model.addAttribute("dashboard", new DashboardModel()
                .setBoards(boardRepository.list())
                .setNewerRelease(new Release()
                        .setMassive(9)
                        .setMajor(1)
                        .setMinor(4))
                .setNoticeboard(noticeboardRepository.preview())
                .setReports(reportRepository.count())
                .setUnreadPms(pmsRepository.count()));
        //model.addAttribute("logout_token", // todo
        return modPage("mod/dashboard.ftlh", model,
                i18n("mod.dashboard.Dashboard"), null);
    }

    private String login(HttpServletRequest request,
            HttpServletResponse response, Model model, String redirect) {

        final LoginModel loginModel = new LoginModel();

        if (request.getMethod().equals("POST")
                && request.getParameter("login") != null) {
            final String username = request.getParameter("username");
            final String password = request.getParameter("password");

            if (username == null || username.isEmpty()
                    || password == null || password.isEmpty()) {
                loginModel.setError(i18n("error.invalid"));
            } else if (!securityService.login(request, username, password)) {
                logger.warn("Unauthorized login attempt!");
                loginModel.setError(i18n("error.invalid"));
            } else {
                //modService.log("Logged in") // todo
                securityService.setCookies();
                return redirectToDashboard(request, redirect);
            }
        }

        final String username = request.getParameter("username");
        if (username != null) {
            loginModel.setUsername(username);
        }

        model.addAttribute("login", loginModel);

        return modPage("mod/login.ftlh", model, i18n("mod.login.Login"), null);
    }

    private String logout(HttpServletRequest request,
            HttpServletResponse response, Model model) {
        securityService.logout(request, response);
        return redirectToDashboard(request, null);
    }

    // TODO move
    private String modPage(String bodyTemplate, Model model,
            String pageTitle, String pageSubTitle) {

        String dataStylesheet = settingsBean.getAll().getDefaultStylesheet()[1];
        if (dataStylesheet == null || dataStylesheet.isEmpty()) {
            dataStylesheet = "default"; // TODO: если нигде не используется, то можно перенести в загрузку конфига
        }

        model.addAttribute("config", settingsBean.getAll());

        model.addAttribute("page", new PageModel()
                .setBoardlist(boardService.buildBoardList())
                .setDataStylesheet(dataStylesheet)
                .setHideDashboardLink(bodyTemplate.equals("mod/dashboard.ftlh"))
                .setTitle(pageTitle)
                .setSubtitle(pageSubTitle));

        model.addAttribute("body", bodyTemplate);

        return "page";
    }

    private String redirectToDashboard(HttpServletRequest request,
            String redirect) {
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE,
                HttpStatus.valueOf(settingsBean.getAll().getRedirectHttp()));
        return "redirect:/mod.php?" + (redirect == null ? "/" : redirect);
    }
}
