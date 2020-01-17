package com.github.vimboard.config;

import com.github.vimboard.domain.Mod;
import com.github.vimboard.domain.User;
import com.github.vimboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("Username %s not found", username));
        }
        return new Mod(
                user.getUsername(),
                user.getPassword(),
                ,
                user.getType(),
                user.getBoards()
        );
    }
}
