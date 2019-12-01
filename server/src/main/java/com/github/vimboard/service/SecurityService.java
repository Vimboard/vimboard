package com.github.vimboard.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    // TODO: remove
    public void foo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails) {
            ((UserDetails) principal).getUsername();
        }
        StringBuilder roles = new StringBuilder();
        for (GrantedAuthority ga : auth.getAuthorities()) {
            roles.append(ga.getAuthority())
            .append("--");
        }
        logger.info("=========== SS::foo: " + principal.getClass().getName() + "  --------- AU: " + roles);
    }

    public boolean isMod() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority ga : auth.getAuthorities()) {
            if ("ROLE_MOD".equals(ga.getAuthority())
                    || "ROLE_ADMIN".equals(ga.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}
