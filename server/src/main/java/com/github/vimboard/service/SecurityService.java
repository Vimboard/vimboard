package com.github.vimboard.service;

import com.github.vimboard.config.SettingsBean;
import com.github.vimboard.config.settings.VimboardModSettings;
import com.github.vimboard.domain.Group;
import com.github.vimboard.domain.Mod;
import com.github.vimboard.model.ModModel;
import com.github.vimboard.model.ModPermissionsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SecurityService {

    private final SettingsBean settingsBean;

    @Autowired
    public SecurityService(SettingsBean settingsBean) {
        this.settingsBean = settingsBean;
    }

    /**
     * todo
     *
     * @return {@code null} if the user does not have mod privileges.
     */
    public Mod getMod() {
        final Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (auth == null) {
            return null;
        }

        return getMod(auth);
    }

    public Mod getMod(Authentication auth) {
        final Object principal = auth.getPrincipal();

        if (!(principal instanceof Mod)) {
            return null;
        }

        if (!isMod(auth)) {
            return null;
        }

        return (Mod) principal;
    }

    /**
     * todo
     *
     * @return {@code null} if the user does not have mod privileges.
     */
    public ModModel getModModel() {
        final Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (auth == null) {
            return null;
        }

        final Mod mod = getMod();
        if (mod == null) {
            return null;
        }

        final ModModel modModel = new ModModel();
        modModel.setId(mod.getId());
        fillPermissionsModel(modModel.getHasPermission(), auth);

        return modModel;
    }

    public void fillPermissionsModel(ModPermissionsModel permissionModel,
            Authentication auth) {
        final VimboardModSettings modSettings = settingsBean.getAll().getMod();
        for (GrantedAuthority ga : auth.getAuthorities()) {
            if (ga instanceof Group) {
                final Group group = (Group) ga;
                if (group.hasRole(modSettings.getChangePassword())) {
                    permissionModel.setChangePassword(true);
                }
                if (group.hasRole(modSettings.getDebugSql())) {
                    permissionModel.setDebugSql(true);
                }
                if (group.hasRole(modSettings.getEditConfig())) {
                    permissionModel.setEditConfig(true);
                }
                if (group.hasRole(modSettings.getEditPages())) {
                    permissionModel.setEditPages(true);
                }
                if (group.hasRole(modSettings.getManageboards())) {
                    permissionModel.setManageboards(true);
                }
                if (group.hasRole(modSettings.getManageusers())) {
                    permissionModel.setManageusers(true);
                }
                if (group.hasRole(modSettings.getModlog())) {
                    permissionModel.setModlog(true);
                }
                if (group.hasRole(modSettings.getNewboard())) {
                    permissionModel.setNewboard(true);
                }
                if (group.hasRole(modSettings.getNoticeboard())) {
                    permissionModel.setNoticeboard(true);
                }
                if (group.hasRole(modSettings.getRecent())) {
                    permissionModel.setRecent(true);
                }
                if (group.hasRole(modSettings.getReports())) {
                    permissionModel.setReports(true);
                }
                if (group.hasRole(modSettings.getSearch())) {
                    permissionModel.setSearch(true);
                }
                if (group.hasRole(modSettings.getShowIp())) {
                    permissionModel.setShowIp(true);
                }
                if (group.hasRole(modSettings.getThemes())) {
                    permissionModel.setThemes(true);
                }
                if (group.hasRole(modSettings.getViewBanAppeals())) {
                    permissionModel.setViewBanAppeals(true);
                }
                if (group.hasRole(modSettings.getViewBanlist())) {
                    permissionModel.setViewBanlist(true);
                }
                if (group.hasRole(modSettings.getViewNotes())) {
                    permissionModel.setViewNotes(true);
                }
            }
        }
    }

    /**
     * TODO
     *
     * @return
     */
    public boolean isAnonymous() {
        final Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (auth == null) {
            return true;
        }

        return auth.getPrincipal() == null
                || auth.getPrincipal().equals("anonymousUser");
    }

    /**
     * TODO
     *
     * @return {@code true} if the user has mod privileies.
     */
    public boolean isMod() {
        final Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (auth == null) {
            return false;
        }
        return isMod(auth);
    }

    public boolean isMod(Authentication auth) {
        for (GrantedAuthority ga : auth.getAuthorities()) {
            if (Group.JANITOR.equals(ga)
                    || Group.MOD.equals(ga)
                    || Group.ADMIN.equals(ga)) {
                return true;
            }
        }
        return false;
    }

    public boolean login(HttpServletRequest request,
            String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            return false;
        }
        return true;
    }

    public void logout(HttpServletRequest request,
            HttpServletResponse response) {
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    public String makeSecureLinkToken(String uri) {
        final String token = settingsBean.getAll().getCookies().getSalt()
                + "-" + uri
                + "-" + getMod().getId();
        return SecurityUtils.sha1(token).substring(0, 8);
    }
}
