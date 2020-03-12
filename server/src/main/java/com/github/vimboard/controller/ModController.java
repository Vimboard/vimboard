package com.github.vimboard.controller;

import com.github.vimboard.config.SettingsBean;
import com.github.vimboard.domain.Group;
import com.github.vimboard.domain.Mod;
import com.github.vimboard.model.ErrorModel;
import com.github.vimboard.model.PageModel;
import com.github.vimboard.model.domain.ModModel;
import com.github.vimboard.model.domain.ReleaseModel;
import com.github.vimboard.model.domain.UserModel;
import com.github.vimboard.model.mod.ConfirmModel;
import com.github.vimboard.model.mod.DashboardModel;
import com.github.vimboard.model.mod.LoginModel;
import com.github.vimboard.model.mod.UsersModel;
import com.github.vimboard.repository.*;
import com.github.vimboard.service.BoardService;
import com.github.vimboard.service.ModService;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.vimboard.controller.ModController.UriPatternType.SECURED;
import static com.github.vimboard.controller.ModController.UriPatternType.SECURED_POST;

@Controller
@RequestMapping("/mod.php")
public class ModController extends AbstractController {

    enum UriPatternType {
        ALL,
        SECURED,
        SECURED_POST
    }

    static class UriPattern {

        final Pattern pattern;
        final boolean post;
        final boolean secured;

        UriPattern(String regex) {
            this(regex, UriPatternType.ALL);
        }

        UriPattern(String regex, UriPatternType type) {
            post = type.equals(SECURED_POST);
            switch (type) {
                case SECURED:
                case SECURED_POST:
                    secured = true;
                    pattern = Pattern.compile("^" + regex
                            + "(/(?<token>[a-f0-9]{8}))?"
                            + "(?:&[^&=]+=[^&]*)*$");
                    break;
                default:
                    secured = false;
                    pattern = Pattern.compile("^" + regex
                            + "(?:&[^&=]+=[^&]*)*$");
            }
        }
    }

    public static class HandlerContext {

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

    interface Handler {

        String handle(HandlerContext args);
    }

    //------------------------------------------------------------------------

    private static final Logger logger = LoggerFactory.getLogger(ModController.class);

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final ModService modService;
    private final ModRepository modRepository;
    private final NoticeboardRepository noticeboardRepository;
    private final PmsRepository pmsRepository;
    private final ReportRepository reportRepository;
    private final SecurityService securityService;
    private final SettingsBean settingsBean;

    private final Map<UriPattern, Handler> handlerMap = new LinkedHashMap<>();
    private final Pattern removeTokenPattern;

    @Autowired
    public ModController(
            MessageSource messageSource,
            BoardRepository boardRepository,
            BoardService boardService,
            ModService modService,
            ModRepository modRepository,
            NoticeboardRepository noticeboardRepository,
            PmsRepository pmsRepository,
            ReportRepository reportRepository,
            SecurityService securityService,
            SettingsBean settingsBean) {
        super(messageSource);
        this.boardRepository = boardRepository;
        this.boardService = boardService;
        this.modService = modService;
        this.modRepository = modRepository;
        this.noticeboardRepository = noticeboardRepository;
        this.pmsRepository = pmsRepository;
        this.reportRepository = reportRepository;
        this.securityService = securityService;
        this.settingsBean = settingsBean;

        // dashboard
        handlerMap.put(new UriPattern("/"), this::dashboard);
        // confirm action (if javascript didn't work)
        handlerMap.put(new UriPattern("/confirm/(.+)"), this::confirm);
        // logout
        handlerMap.put(new UriPattern("/logout", SECURED), this::logout);

        // manage users
        handlerMap.put(new UriPattern("/users"), this::users);
        // prmote/demote user
        handlerMap.put(new UriPattern("/users/(\\d+)/(promote|demote)", SECURED), this::userPromote);
        // edit user
        handlerMap.put(new UriPattern("/users/(\\d+)", SECURED_POST), this::user);
        // create a new user
        handlerMap.put(new UriPattern("/users/new", SECURED_POST), this::userNew);

        removeTokenPattern = Pattern.compile("/([a-f0-9]{8})$");
    }

    @RequestMapping(value = {"/", "/**"})
    public String index(HttpServletRequest request) {
        return redirectToDashboard(request, null);
    }

    @RequestMapping(value = "")
    public String root(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        if (securityService.isAnonymous()) { // todo change to getModModel == null
            return login(request, response, model, null); // todo redirect
        }

        final ModModel modModel = securityService.getModModel();
        model.addAttribute("mod", modModel);

        String query = request.getQueryString();
        if (query == null || query.isEmpty()) {
            query = "/";
        }

        for (Map.Entry<UriPattern, Handler> h : handlerMap.entrySet()) {
            final UriPattern uriPattern = h.getKey();
            final Handler handler = h.getValue();

            final Matcher matcher = uriPattern.pattern.matcher(query);
            if (matcher.find()) {

                final HandlerContext handlerContext = new HandlerContext()
                        .setRequest(request)
                        .setResponse(response)
                        .setModModel(modModel)
                        .setModel(model)
                        .setMatcher(matcher);

                if (uriPattern.secured && (
                        !uriPattern.post
                        || request.getMethod().equals("POST"))) {

                    String token;
                    try {
                        token = matcher.group("token");
                    } catch (IllegalArgumentException ex) {
                        token = null;
                    }
                    if (token == null) {
                        token = request.getParameter("token");
                    }
                    if (token == null) {
                        if (uriPattern.post) {
                            return error(handlerContext.put(
                                    "message", i18n("error.csrf")));
                        } else {
                            return confirm(handlerContext.put(
                                    "request", query));
                        }
                    }

                    // CSRF-protected page; validate security token
                    final String actualQuery =
                            removeTokenPattern.matcher(query).replaceAll("");
                    if (!securityService.makeSecureLinkToken(actualQuery)
                            .equals(token)) {
                        return error(handlerContext.put(
                                "message", i18n("error.csrf")));
                    }
                }

                return handler.handle(handlerContext);
            }
        }

        throw new ResourceBadRequestException();
    }

    //------------------------------------------------------------------------

    private String confirm(HandlerContext ctx) {

        final String request = (String) ctx.get("request");

        ctx.model.addAttribute("confirm", new ConfirmModel()
                .setRequest(request)
                .setToken(securityService.makeSecureLinkToken(request)));

        return modPage("mod/confirm.ftlh", ctx.model,
                i18n("mod.confirm.Confirm_action"), null);
    }

    private String dashboard(HandlerContext args) {
        args.model.addAttribute("dashboard", new DashboardModel()
                .setBoards(boardRepository.list())
                .setLogoutToken(securityService.makeSecureLinkToken("/logout"))
                .setNewerRelease(new ReleaseModel()
                        .setMassive(9)
                        .setMajor(1)
                        .setMinor(4))
                .setNoticeboard(noticeboardRepository.preview())
                .setReports(reportRepository.count())
                .setUnreadPms(pmsRepository.count()));
        return modPage("mod/dashboard.ftlh", args.model,
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

    private String logout(HandlerContext ctx) {
        securityService.logout(ctx.request, ctx.response);
        return redirectToDashboard(ctx.request, null);
    }

    private String user(HandlerContext ctx) {
        return null; // todo
    }

    private String userNew(HandlerContext ctx) {
        return null; // todo
    }

    private String userPromote(HandlerContext ctx) {
        final ModModel modModel = getModModel(ctx);
        if (modModel == null
                || !modModel.getHasPermission().isPromoteusers()) {
            return error(ctx.put("message", i18n("error.noaccess")));
        }

        final int userId = Integer.parseInt(ctx.matcher.group(1));
        final boolean isPromote = ctx.matcher.group(2).equals("promote");

        Mod mod = modRepository.find(userId);
        if (mod == null) {
            return error(ctx.put("message", i18n("error.404")));
        }

        Group newType = modService.promote(mod.getType(), isPromote);
        if (newType == null) {
            return error(ctx.put("message",
                    i18n("mod.users.Impossible_to_promote_demote_user_")));
        }

        modRepository.setType(userId, newType);

        securityService.log(ctx,
                (isPromote ? "Promoted" : "Demoted")
                        + " user \"" + mod.getUsername()
                        + "\" to " + newType.toString());

        return redirectToDashboard(ctx.request, "/users");
    }

    private String users(HandlerContext ctx) {
        final ModModel modModel = getModModel(ctx);
        if (modModel == null
                || !modModel.getHasPermission().isManageusers()) {
            return error(ctx.put("message", i18n("error.noaccess")));
        }

        final List<UserModel> userList = modService.listUsers();

        ctx.model.addAttribute("users", new UsersModel()
                .setList(userList));

        return modPage("mod/users.ftlh", ctx.model,
                i18n("mod.users.Manage_users_{count}", userList.size()), null);
    }

    //------------------------------------------------------------------------

    private String error(HandlerContext ctx) {
        ctx.response.setStatus(400);

        ctx.model.addAttribute("error", new ErrorModel()
                .setMessage((String) ctx.get("message")));

        return modPage("error.ftlh", ctx.model,
                i18n("error.Error"), i18n("error.An_error_has_occured_"));
   }

   private ModModel getModModel(HandlerContext ctx) {
       Object model = ctx.model.getAttribute("mod");
       if (model instanceof ModModel) {
           return (ModModel) model;
       } else {
           return securityService.getModModel();
       }
   }

    // TODO move
    private String modPage(String bodyTemplate, Model model,
            String pageTitle, String pageSubTitle) {

        model.addAttribute("body", bodyTemplate);

        model.addAttribute("config", settingsBean.getAll());

        String dataStylesheet = settingsBean.getAll().getDefaultStylesheet()[1];
        if (dataStylesheet == null || dataStylesheet.isEmpty()) {
            dataStylesheet = "default"; // TODO: если нигде не используется, то можно перенести в загрузку конфига
        }

        model.addAttribute("page", new PageModel()
                .setBoardlist(boardService.buildBoardList())
                .setDataStylesheet(dataStylesheet)
                .setHideDashboardLink(bodyTemplate.equals("mod/dashboard.ftlh"))
                .setTitle(pageTitle)
                .setSubtitle(pageSubTitle));

        return "page";
    }

    private String redirectToDashboard(HttpServletRequest request,
            String redirect) {
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE,
                HttpStatus.valueOf(settingsBean.getAll().getRedirectHttp()));
        return "redirect:/mod.php?" + (redirect == null ? "/" : redirect);
    }
}
