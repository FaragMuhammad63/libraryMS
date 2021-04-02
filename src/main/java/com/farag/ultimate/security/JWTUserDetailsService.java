package com.farag.ultimate.security;

import com.farag.ultimate.repos.UserRepository;
import com.farag.ultimate.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;




@Service
public class JWTUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public JWTUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(s).orElseThrow(
                () -> new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", s)));

        return new JWTUserDetails(user.getId(), user.getPassword(), user.getUserName(), user.getRoles());
    }

    public boolean isUserVerified(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username)));

        return user != null;
    }
}
