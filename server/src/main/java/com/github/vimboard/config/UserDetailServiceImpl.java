package com.github.vimboard.config;

import com.github.vimboard.domain.Mod;
import com.github.vimboard.repository.ModRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private final ModRepository modRepository;

    @Autowired
    public UserDetailServiceImpl(ModRepository modRepository) {
        this.modRepository = modRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Mod mod = modRepository.findByName(username);
        if (mod == null) {
            throw new UsernameNotFoundException(
                    String.format("Username %s not found", username));
        }
        return mod;
    }
}
