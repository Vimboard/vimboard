package com.github.vimboard.controller;

import com.github.vimboard.config.settings.VimboardSettings;
import com.github.vimboard.controller.context.GlobalContext;
import com.github.vimboard.controller.context.HandlerContext;
import com.github.vimboard.domain.Group;
import com.github.vimboard.domain.Mod;
import com.github.vimboard.domain.Pms;
import com.github.vimboard.model.*;
import com.github.vimboard.page.ErrorPage;
import com.github.vimboard.page.Page;
import com.github.vimboard.page.mod.*;
import com.github.vimboard.repository.*;
import com.github.vimboard.service.*;
import com.github.vimboard.service.types.BodyRef;
import com.github.vimboard.service.types.ServiceException;
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

    interface Handler {

        String handle(HandlerContext args);
    }

    //------------------------------------------------------------------------

    private static final Logger logger = LoggerFactory.getLogger(ModController.class);

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final DebugService debugService;
    private final Events events;
    private final FunctionsService functionsService;
    private final ModLogService modLogService;
    private final ModRepository modRepository;
    private final ModService modService;
    private final NoticeboardRepository noticeboardRepository;
    private final PmsRepository pmsRepository;
    private final PmsService pmsService;
    private final ReportRepository reportRepository;
    private final SecurityService securityService;
    private final VimboardSettings settings;

    private final Map<UriPattern, Handler> handlerMap = new LinkedHashMap<>();
    private final Pattern removeTokenPattern;

    @Autowired
    public ModController(
            MessageSource messageSource,
            BoardRepository boardRepository,
            BoardService boardService,
            DebugService debugService,
            Events events,
            FunctionsService functionsService,
            ModLogService modLogService,
            ModRepository modRepository,
            ModService modService,
            NoticeboardRepository noticeboardRepository,
            PmsRepository pmsRepository,
            PmsService pmsService,
            ReportRepository reportRepository,
            SecurityService securityService,
            VimboardSettings settings) {
        super(messageSource);
        this.boardRepository = boardRepository;
        this.boardService = boardService;
        this.debugService = debugService;
        this.events = events;
        this.functionsService = functionsService;
        this.modLogService = modLogService;
        this.modRepository = modRepository;
        this.modService = modService;
        this.noticeboardRepository = noticeboardRepository;
        this.pmsRepository = pmsRepository;
        this.pmsService = pmsService;
        this.reportRepository = reportRepository;
        this.securityService = securityService;
        this.settings = settings;

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

        // create a new pm
        handlerMap.put(new UriPattern("/new_PM/([^/]+)", SECURED_POST), this::newPm);
        // read a pm
        handlerMap.put(new UriPattern("/PM/(\\d+)(/reply)?"), this::pm);
        // pm inbox
        handlerMap.put(new UriPattern("/inbox"), this::inbox);

        // ...

        // edit board details
        //handlerMap.put(new UriPattern("/edit/(\%b)", SECURED_POST), this::editBoard);
        // create a new board
        handlerMap.put(new UriPattern("/new-board", SECURED_POST), this::newBoard); // TODO

        // ...

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
        final ModModel modModel = ctx.modModel;

        ctx.model.addAttribute("dashboard", new DashboardPage() // TODO dashboard -> dashboardPage
                .setBoards(boardRepository.list())
                .setLogoutToken(securityService.makeSecureLinkToken("/logout"))
                .setNewerRelease(new ReleaseModel()
                        .setMassive(9)
                        .setMajor(1)
                        .setMinor(4))
                .setNoticeboard(noticeboardRepository.preview())
                .setReports(reportRepository.count())
                .setUnreadPms(pmsRepository.countUnreaded(modModel.getId())));
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

    private String inbox(HandlerContext ctx) {
        final ModModel modModel = ctx.modModel;

        List<PmsModel> messages = pmsService.list(modModel.getId());

        long unread = pmsRepository.countUnreaded(modModel.getId());

        ctx.model.addAttribute("inbox", new InboxPage() // TODO inbox -> inboxPage
                .setMessages(messages)
                .setUnread(unread));

        final String unreadStr = (unread > 0
                ? unread + " " + i18n("mod.inbox.unread")
                : i18n("mod.inbox.empty"));

        return modPage("mod/inbox.ftlh", ctx.model,
                i18n("mod.inbox.PM_inbox_{unread}", unreadStr), null);
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

    private String newBoard(HandlerContext ctx) {
        final ModModel modModel = ctx.modModel;

        if (!modModel.getHasPermission().isNewboard()) {
            return error(ctx.put("message", i18n("error.noaccess")));
        }

        final String uri = ctx.request.getParameter("uri");
        final String title = ctx.request.getParameter("title");
        final String subtitle = ctx.request.getParameter("subtitle");
        if (ctx.request.getMethod().equals("POST")
                && uri != null && title != null && subtitle != null) {
            if (uri.isEmpty()) {
                return error(ctx.put("message",
                        i18n("error.required", "URI")));
            }

            if (title.isEmpty()) {
                return error(ctx.put("message",
                        i18n("error.required", "title")));
            }

            if (!uri.matches("^" + settings.getAll().getBoardRegex() + "$")) {
                return error(ctx.put("message",
                        i18n("error.invalidfield", "URI")));
            }

            // TODO: error 'Your filesystem cannot handle a board URI of that length'

            final FunctionsService.Functions f = functionsService.create(
                    new GlobalContext() // TODO: refactor (move)
                            .setEvents(events)
                            .setHandlerContext(ctx)
                            .setUri(uri));

            try {
                if (f.openBoard()) {
                    return error(ctx.put("message",
                            i18n("error.boardexists", uri)));
                }
            } catch (ServiceException ex) {
                return error(ctx.put("message", ex.getMessage()));
            }

            boardRepository.create(uri, title, subtitle);

            securityService.log(ctx, "Created a new board: " + settings
                    .getAll().getBoardAbbreviation().replace("{uri}", uri));

            try {
                if (!f.openBoard()) {
                    return error(ctx.put("message",
                            i18n("error.boardnotcreated", uri)));
                }
            } catch (ServiceException ex) {
                return error(ctx.put("message", ex.getMessage()));
            }

            boardRepository.createBoardTable(uri);

            // TODO ^^^^^^^^^^^^^^

            // Build the board
            f.buildIndex(); // TODO: CURRENT <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

            // rebuildThemes('boards'); TODO: NEXT =======

            return redirectToDashboard(ctx.request,
                    "/" + uri + "/" + settings.getAll().getFileIndex());
        }

        // ctx.model.addAttribute(); TODO: NEXT =======

        return modPage("mod/board.ftlh", ctx.model,
                i18n("mod.board.New_board"), null);

        /* TODO: ---------------------
        mod_page(_('New board'), 'mod/board.html',
                array('new' => true, 'token' => make_secure_link_token('new-board')));*/
    }

    private String newPm(HandlerContext ctx) {
        final ModModel modModel = ctx.modModel;
        final String username = ctx.matcher.group(1);

        if (!modModel.getHasPermission().isCreatePm()) {
            return error(ctx.put("message", i18n("error.noaccess")));
        }

        Mod m = modRepository.findByName(username);
        if (m == null) {
            // Old style ?/PM: by user ID
            // TODO ^^^
            try {
                final int id = Integer.parseInt(username);
                m = modRepository.find(id);
            } catch (NumberFormatException ex) {
                m = null;
            }
            if (m != null) {
                return redirectToDashboard(ctx.request,
                        "/new_PM/" + m.getUsername());
            } else {
                return error(ctx.put("message", i18n("error.404")));
            }
        }

        String message = ctx.request.getParameter("message");
        if (ctx.request.getMethod().equals("POST")
                && message != null) {
            final FunctionsService.Functions f = functionsService.create(
                    new GlobalContext() // TODO: refactor (move)
                            .setHandlerContext(ctx));

            message = f.escapeMarkupModifiers(message);
            BodyRef bodyRef = new BodyRef(message);
            try {
                f.markup(bodyRef);
            } catch (ServiceException ex) {
                return error(ctx.put("message", i18n(ex.getMessage())));
            }
            message = bodyRef.body;

            pmsRepository.create(new Pms()
                    .setSender(modModel.getId())
                    .setTo(m.getId())
                    .setMessage(message)
                    .setTime(new Date()));

            securityService.log(ctx,
                    "Sent a PM to " + f.utf8ToHtml(m.getUsername()));

            return redirectToDashboard(ctx.request, null);
        }

        ctx.model.addAttribute("newPm", new NewPmPage()
                .setId(m.getId())
                .setMessage("")
                .setToken(securityService.makeSecureLinkToken(
                        "/new_PM/" + m.getUsername()))
                .setUsername(m.getUsername()));

        return modPage("mod/new_pm.ftlh", ctx.model,
                i18n("mod.newPm.New_PM_for_{username}", m.getUsername()), null);
    }

    private String pm(HandlerContext ctx) {
        final ModModel modModel = ctx.modModel;
        final long id = Long.parseLong(ctx.matcher.group(1));
        final boolean reply = (ctx.matcher.group(2) != null);

        if (reply && !modModel.getHasPermission().isCreatePm()) {
            return error(ctx.put("message", i18n("error.noaccess")));
        }

        PmsToModel pm = pmsService.find(id);

        if (pm == null || pm.getTo() != modModel.getId()
                && !modModel.getHasPermission().isMasterPm()) {
            return error(ctx.put("message", i18n("error.404")));
        }

        if (ctx.request.getMethod().equals("POST")
                && ctx.request.getParameter("delete") != null) {
            pmsRepository.drop(id);

            return redirectToDashboard(ctx.request, null);
        }

        if (pm.isUnread() && pm.getTo() == modModel.getId()) {
            pmsRepository.setReaded(id);

            securityService.log(ctx, "Read a PM");
        }

        if (reply) {
            if (pm.getToUsername() == null) {
                return error(ctx.put("message", i18n("error.404"))); // deleted?
            }

            ctx.model.addAttribute("newPm", new NewPmPage()
                    .setId(pm.getSender())
                    .setMessage(PmsService.quote(pm.getMessage(),
                            settings.getAll().getMinifyHtml()))
                    .setToken(securityService.makeSecureLinkToken(
                            "/new_PM/" + pm.getUsername()))
                    .setUsername(pm.getUsername()));

            return modPage("mod/new_pm.ftlh", ctx.model,
                    i18n("mod.newPm.New_PM_for_{username}",
                            pm.getToUsername()), null);
        } else {
            ctx.model.addAttribute("pm", pm);

            return modPage("mod/pm.ftlh", ctx.model,
                    i18n("mod.pm.Private_message_{id}", id), null);
        }
    }

    private String logout(HandlerContext ctx) {
        securityService.logout(ctx.request, ctx.response);
        return redirectToDashboard(ctx.request, null);
    }

    private String user(HandlerContext ctx) {
        final FunctionsService.Functions f = functionsService.create(
                new GlobalContext()); // TODO: refactor (move)

        final ModModel modModel = ctx.modModel;
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
                        + settings.getAll().getBoardRegex() + ")$");
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
                securityService.log(ctx, "Deleted user "
                        + f.utf8ToHtml(user.getUsername())
                        + " <small>(#" + user.getId() + ")</small>");
                return redirectToDashboard(ctx.request, "/users");
            }

            if (username.isEmpty()) {
                return error(ctx.put("message",
                        i18n("error.required", "username")));
            }

            modRepository.alter(userId, username, boards);

            if (!user.getUsername().equals(username)) {
                securityService.log(ctx, "Renamed user \""
                        + f.utf8ToHtml(user.getUsername())
                        + "\" <small>(#" + user.getId() + ")</small>"
                        + " to \"" + f.utf8ToHtml(username) + "\"");
            }

            if (!password.isEmpty()) {
                modRepository.changePassword(userId, password);
                securityService.log(ctx, "Changed password for "
                        + f.utf8ToHtml(username)
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
        final FunctionsService.Functions f = functionsService.create(
                new GlobalContext()); // TODO: refactor (move)

        final ModModel modModel = ctx.modModel;
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
                        + settings.getAll().getBoardRegex() + ")$");
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

            securityService.log(ctx, "Created a new user: "
                    + f.utf8ToHtml(username)
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
        final FunctionsService.Functions f = functionsService.create(
                new GlobalContext()); // TODO: refactor (move)

        final ModModel modModel = ctx.modModel;
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
                + " user \"" + f.utf8ToHtml(mod.getUsername())
                + "\" to " + newType.toString());

        return redirectToDashboard(ctx.request, "/users");
    }

    private String users(HandlerContext ctx) {
        final ModModel modModel = ctx.modModel;

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

    // TODO move
    private String modPage(String bodyTemplate, Model model,
            String pageTitle, String pageSubTitle) {

        model.addAttribute("config", settings.getAll());

        String dataStylesheet = settings.getAll().getDefaultStylesheet()[1];
        if (dataStylesheet == null || dataStylesheet.isEmpty()) {
            dataStylesheet = "default"; // TODO: если нигде не используется, то можно перенести в загрузку конфига
        }

        model.addAttribute("page", new Page()
                .setBoardlist(boardService.buildBoardList())
                .setBody(bodyTemplate)
                .setDataStylesheet(dataStylesheet)
                .setHideDashboardLink(bodyTemplate.equals("mod/dashboard.ftlh"))
                .setTitle(pageTitle)
                .setSubtitle(pageSubTitle));

        return "page";
    }

    private String redirectToDashboard(HttpServletRequest request,
            String redirect) {
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE,
                HttpStatus.valueOf(settings.getAll().getRedirectHttp()));
        return "redirect:/mod.php?" + (redirect == null ? "/" : redirect);
    }
}
