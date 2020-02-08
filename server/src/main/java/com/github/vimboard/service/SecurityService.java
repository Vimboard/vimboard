package com.github.vimboard.service;

import com.github.vimboard.config.SettingsBean;
import com.github.vimboard.config.VimboardModSettings;
import com.github.vimboard.domain.Group;
import com.github.vimboard.domain.Mod;
import com.github.vimboard.model.ModModel;
import com.github.vimboard.model.ModPermissionsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final SettingsBean settingsBean;

    @Autowired
    public SecurityService(SettingsBean settingsBean) {
        this.settingsBean = settingsBean;
    }

    /**
     * TODO
     *
     * @return {@code true} if the user has mod privileies.
     */
    public boolean isMod() {
        final Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        return isMod(auth);
    }

    private boolean isMod(Authentication auth) {
        for (GrantedAuthority ga : auth.getAuthorities()) {
            if (Group.JANITOR.equals(ga)
                    || Group.MOD.equals(ga)
                    || Group.ADMIN.equals(ga)) {
                return true;
            }
        }
        return false;
    }

    /**
     * TODO
     *
     * @return not {@code null} if the user has mod privileies.
     */
    public ModModel getMod() {
        final Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        final Object userDetails = auth.getDetails();
        if (!(userDetails instanceof Mod)) {
            return null;
        }
        final Mod mod = (Mod) userDetails;

        if (!isMod(auth)) {
            return null;
        }

        ModModel modModel = new ModModel();
        modModel.setId(mod.getId());
        fillPermissionsModel(modModel.getHasPermission(), auth);

        return modModel;
    }

    private void fillPermissionsModel(ModPermissionsModel permissionModel,
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
                if (group.hasRole(modSettings.getNewboard())) {
                    permissionModel.setNewboard(true);
                }
                if (group.hasRole(modSettings.getNoticeboard())) {
                    permissionModel.setNoticeboard(true);
                }
                if (group.hasRole(modSettings.getReports())) {
                    permissionModel.setReports(true);
                }
                if (group.hasRole(modSettings.getViewBanAppeals())) {
                    permissionModel.setViewBanAppeals(true);
                }
                if (group.hasRole(modSettings.getViewBanlist())) {
                    permissionModel.setViewBanlist(true);
                }
            }
        }
    }
}
