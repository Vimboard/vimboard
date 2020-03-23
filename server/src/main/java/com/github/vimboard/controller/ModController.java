package com.github.vimboard.controller;

import com.github.vimboard.config.SettingsBean;
import com.github.vimboard.domain.Group;
import com.github.vimboard.domain.Mod;
import com.github.vimboard.model.ErrorPage;
import com.github.vimboard.model.Page;
import com.github.vimboard.model.domain.ModLogModel;
import com.github.vimboard.model.domain.ModModel;
import com.github.vimboard.model.domain.ReleaseModel;
import com.github.vimboard.model.domain.UserModel;
import com.github.vimboard.model.mod.*;
import com.github.vimboard.repository.*;
import com.github.vimboard.service.*;
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
    private final DebugService debugService;
    private final ModLogService modLogService;
    private final ModRepository modRepository;
    private final ModService modService;
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
            DebugService debugService,
            ModLogService modLogService,
            ModRepository modRepository,
            ModService modService,
            NoticeboardRepository noticeboardRepository,
            PmsRepository pmsRepository,
            ReportRepository reportRepository,
            SecurityService securityService,
            SettingsBean settingsBean) {
        super(messageSource);
        this.boardRepository = boardRepository;
        this.boardService = boardService;
        this.debugService = debugService;
        this.modLogService = modLogService;
        this.modRepository = modRepository;
        this.modService = modService;
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

        // debug request
        handlerMap.put(new UriPattern("/debug/http"), this::debugHttp);

        removeTokenPattern = Pattern.compile("/([a-f0-9]{8})$");
    }

    @RequestMapping(value = {"/", "/**"})
    public String index(HttpServletRequest request) {
        return redirectToDashboard(request, null);
    }

    @RequestMapping(value = "")
    public String root(HttpServletRequest request, HttpServletResponse response,
            Model model) {
        final HandlerContext handlerContext = new HandlerContext()
                .setRequest(request)
                .setResponse(response)
                .setModel(model);

        if (securityService.isAnonymous()) { // todo change to getModModel == null
            return login(handlerContext, null); // todo redirect
        }

        final ModModel modModel = securityService.buildModModel();
        model.addAttribute("mod", modModel);
        handlerContext.setModModel(modModel);

        String query = request.getQueryString();
        if (query == null || query.isEmpty()) {
            query = "/";
        }

        for (Map.Entry<UriPattern, Handler> h : handlerMap.entrySet()) {
            final UriPattern uriPattern = h.getKey();
            final Handler handler = h.getValue();

            final Matcher matcher = uriPattern.pattern.matcher(query);
            if (matcher.find()) {

                handlerContext.setMatcher(matcher);

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

        ctx.model.addAttribute("confirm", new ConfirmPage()
                .setRequest(request)
                .setToken(securityService.makeSecureLinkToken(request)));

        return modPage("mod/confirm.ftlh", ctx.model,
                i18n("mod.confirm.Confirm_action"), null);
    }

    private String dashboard(HandlerContext ctx) {
        ctx.model.addAttribute("dashboard", new DashboardPage() // TODO dashboard -> dashboardPage
                .setBoards(boardRepository.list())
                .setLogoutToken(securityService.makeSecureLinkToken("/logout"))
                .setNewerRelease(new ReleaseModel()
                        .setMassive(9)
                        .setMajor(1)
                        .setMinor(4))
                .setNoticeboard(noticeboardRepository.preview())
                .setReports(reportRepository.count())
                .setUnreadPms(pmsRepository.count()));
        return modPage("mod/dashboard.ftlh", ctx.model,
                i18n("mod.dashboard.Dashboard"), null);
    }

    private String debugHttp(HandlerContext ctx) {
        // TODO: check permissions

        ctx.model.addAttribute("request", // TODO request -> debugHttpPage
                debugService.buildDebugHttpModel(ctx.request));

        return modPage("mod/debug_http.ftlh", ctx.model,
                i18n("mod.debugHttp.Debug__HTTP"), null);
    }

    private String login(HandlerContext ctx, String redirect) {

        final LoginPage loginPage = new LoginPage();

        if (ctx.request.getMethod().equals("POST")
                && ctx.request.getParameter("login") != null) {
            final String username = ctx.request.getParameter("username");
            final String password = ctx.request.getParameter("password");

            if (username == null || username.isEmpty()
                    || password == null || password.isEmpty()) {
                loginPage.setError(i18n("error.invalid"));
            } else if (!securityService.login(ctx.request, username, password)) {
                logger.warn("Unauthorized login attempt!");
                loginPage.setError(i18n("error.invalid"));
            } else {
                final ModModel modModel = securityService.buildModModel();
                ctx.model.addAttribute("mod", modModel);
                ctx.setModModel(modModel);

                securityService.log(ctx, "Logged in");
                return redirectToDashboard(ctx.request, redirect);
            }
        }

        final String username = ctx.request.getParameter("username");
        if (username != null) {
            loginPage.setUsername(username);
        }

        ctx.model.addAttribute("login", loginPage); // TODO login -> loginPage

        return modPage("mod/login.ftlh", ctx.model,
                i18n("mod.login.Login"), null);
    }

    private String logout(HandlerContext ctx) {
        securityService.logout(ctx.request, ctx.response);
        return redirectToDashboard(ctx.request, null);
    }

    private String user(HandlerContext ctx) {
        final ModModel modModel = getModModel(ctx);
        final int userId = Integer.parseInt(ctx.matcher.group(1));
        final String username = ctx.request.getParameter("username");
        final String password = ctx.request.getParameter("password");

        if (modModel == null || (!modModel.getHasPermission().isEditusers()
                && !modModel.getHasPermission().isChangePassword()
                && userId == modModel.getId())) {
            return error(ctx.put("message", i18n("error.noaccess")));
        }

        final Mod user = modRepository.find(userId);
        if (user == null) {
            return error(ctx.put("message", i18n("error.404")));
        }

        if (modModel.getHasPermission().isEditusers()
                && ctx.request.getMethod().equals("POST")
                && username != null
                && password != null) {

            final String[] boards;
            if (ctx.request.getParameter("allboards") != null) {
                boards = new String[] { "*" };
            } else {
                final List<String> boardList = new ArrayList<>();
                final Set<String> boardSet = boardService.buildUriSet();
                final Enumeration<String> paramNames =
                        ctx.request.getParameterNames();
                final Pattern pattern = Pattern.compile("^board_("
                        + settingsBean.getAll().getBoardRegex() + ")$");
                while (paramNames.hasMoreElements()) {
                    final Matcher mather = pattern.matcher(
                            paramNames.nextElement());
                    if (mather.find()) {
                        final String board = mather.group(1);
                        if (boardSet.contains(board)) {
                            boardList.add(board);
                        }
                    }
                }
                boards = boardList.toArray(new String[0]);
            }

            if (ctx.request.getParameter("delete") != null) {
                if (!modModel.getHasPermission().isDeleteusers()) {
                    return error(ctx.put("message", i18n("error.noaccess")));
                }
                modRepository.drop(userId);
                securityService.log(ctx, "Deleted user " + user.getUsername()
                        + " <small>(#" + user.getId() + ")</small>");
                return redirectToDashboard(ctx.request, "/users");
            }

            if (username.isEmpty()) {
                return error(ctx.put("message",
                        i18n("error.required", "username")));
            }

            modRepository.alter(userId, username, boards);

            if (!user.getUsername().equals(username)) {
                securityService.log(ctx, "Renamed user \"" + user.getUsername()
                        + "\" <small>(#" + user.getId() + ")</small>"
                        + " to \"" + username + "\"");
            }

            if (!password.isEmpty()) {
                modRepository.changePassword(userId, password);
                securityService.log(ctx, "Changed password for " + username
                        + " <small>(#" + user.getId() + ")</small>");
            }

            // TODO: relog with new password. (Deprecated in Vimboard)

            if (modModel.getHasPermission().isManageusers()) {
                return redirectToDashboard(ctx.request, "/users");
            } else {
                return redirectToDashboard(ctx.request, "/");
            }
        }

        if (modModel.getHasPermission().isChangePassword()
                && userId == modModel.getId()
                && ctx.request.getMethod().equals("POST")
                && password != null) {
            if (!password.isEmpty()) {
                modRepository.changePassword(userId, password);
                securityService.log(ctx, "Changed own password");

                // TODO: relog with new password. (Deprecated in Vimboard)
            }

            if (modModel.getHasPermission().isManageusers()) {
                return redirectToDashboard(ctx.request, "/users");
            } else {
                return redirectToDashboard(ctx.request, "/");
            }
        }

        final List<ModLogModel> log;
        if (modModel.getHasPermission().isModlog()) {
            log = modLogService.preview(userId, 5);
        } else {
            log = new ArrayList<>();
        }

        ctx.model.addAttribute("userPage", new UserPage()
                .setBoards(boardRepository.list())
                .setLogs(log)
                .setToken(securityService.makeSecureLinkToken(
                        "/users/" + user.getId()))
                .setUser(user));

        return modPage("mod/user.ftlh", ctx.model,
                i18n("mod.user.Edit_user"), null);
    }

    private String userNew(HandlerContext ctx) {
        final ModModel modModel = getModModel(ctx);
        final String username = ctx.request.getParameter("username");
        final String password = ctx.request.getParameter("password");

        if (!modModel.getHasPermission().isCreateusers()) {
            return error(ctx.put("message", i18n("error.noaccess")));
        }

        if (ctx.request.getMethod().equals("POST")) {
            if (username.isEmpty()) {
                return error(ctx.put("message",
                        i18n("error.required", "username")));
            }
            if (password.isEmpty()) {
                return error(ctx.put("message",
                        i18n("error.required", "password")));
            }

            final String[] boards;
            if (ctx.request.getParameter("allboards") != null) {
                boards = new String[] { "*" };
            } else {
                final List<String> boardList = new ArrayList<>();
                final Set<String> boardSet = boardService.buildUriSet();
                final Enumeration<String> paramNames =
                        ctx.request.getParameterNames();
                final Pattern pattern = Pattern.compile("^board_("
                        + settingsBean.getAll().getBoardRegex() + ")$");
                while (paramNames.hasMoreElements()) {
                    final Matcher mather = pattern.matcher(
                            paramNames.nextElement());
                    if (mather.find()) {
                        final String board = mather.group(1);
                        if (boardSet.contains(board)) {
                            boardList.add(board);
                        }
                    }
                }
                boards = boardList.toArray(new String[0]);
            }

            Group type;
            try {
                type = Group.valueOf(ctx.request.getParameter("type"));
            } catch (NullPointerException | IllegalArgumentException ex) {
                type = null;
            }
            if (type == null || type == Group.DISABLED) {
                return error(ctx.put("message",
                        i18n("error.invalidfield", "type")));
            }

            modRepository.create(username, password, type, boards);
            final Mod user = modRepository.findByName(username);

            securityService.log(ctx, "Created a new user: " + username
                    + " <small>(#" + user.getId() + ")</small>");

            return redirectToDashboard(ctx.request, "/users");
        }

        ctx.model.addAttribute("userPage", new UserPage()
                .setBoards(boardRepository.list())
                .setNew(true)
                .setToken(securityService.makeSecureLinkToken("/users/new")));

        return modPage("mod/user.ftlh", ctx.model,
                i18n("mod.user.New_user"), null);
    }

    private String userPromote(HandlerContext ctx) {
        final ModModel modModel = getModModel(ctx);
        final int userId = Integer.parseInt(ctx.matcher.group(1));
        final boolean isPromote = ctx.matcher.group(2).equals("promote");

        if (modModel == null
                || !modModel.getHasPermission().isPromoteusers()) {
            return error(ctx.put("message", i18n("error.noaccess")));
        }

        Mod mod = modRepository.find(userId);
        if (mod == null) {
            return error(ctx.put("message", i18n("error.404")));
        }

        Group newType = modService.promote(mod.getType(), isPromote);
        if (newType == null) {
            return error(ctx.put("message",
                    i18n("mod.users.Impossible_to_promote_demote_user_")));
        }

        modRepository.alterType(userId, newType);

        securityService.log(ctx, (isPromote ? "Promoted" : "Demoted")
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

        ctx.model.addAttribute("users", new UsersPage() // TODO users -> usersPage
                .setList(userList));

        return modPage("mod/users.ftlh", ctx.model,
                i18n("mod.users.Manage_users_{count}", userList.size()), null);
    }

    //------------------------------------------------------------------------

    private String error(HandlerContext ctx) {
        ctx.response.setStatus(400);

        ctx.model.addAttribute("error", new ErrorPage() // TODO error -> errorPage
                .setMessage((String) ctx.get("message")));

        return modPage("error.ftlh", ctx.model,
                i18n("error.Error"), i18n("error.An_error_has_occured_"));
   }

   private ModModel getModModel(HandlerContext ctx) {
       Object model = ctx.model.getAttribute("mod");
       if (model instanceof ModModel) {
           return (ModModel) model;
       } else {
           return securityService.buildModModel();
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

        model.addAttribute("page", new Page()
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
