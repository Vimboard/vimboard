package com.github.vimboard.service;

import com.github.vimboard.config.SettingsBean;
import com.github.vimboard.config.VimboardModSettings;
import com.github.vimboard.domain.Group;
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
        if (!isMod()) {
            return null;
        }
        ModModel modModel = new ModModel();
        final ModPermissionsModel permissionModel = modModel.getHasPermission();
        final Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        final VimboardModSettings modSettings = settingsBean.getAll().getMod();

        for (GrantedAuthority ga : auth.getAuthorities()) {
            if (ga instanceof Group) {
                final Group group = (Group) ga;
                if (group.hasRole(modSettings.getEditPages())) {
                    permissionModel.setEditPages(true);
                }
                if (group.hasRole(modSettings.getManageboards())) {
                    permissionModel.setManageboards(true);
                }
                if (group.hasRole(modSettings.getNewboard())) {
                    permissionModel.setNewboard(true);
                }
            }
        }
        return modModel;
    }
}
