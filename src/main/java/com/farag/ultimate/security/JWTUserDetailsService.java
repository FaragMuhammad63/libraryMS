package com.farag.ultimate.security;

import com.farag.ultimate.repos.UserRepo;
import com.farag.ultimate.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JWTUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    @Autowired
    public JWTUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepo.findFirstByUserName(s).orElseThrow(
                () -> new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", s)));
        return new JWTUserDetails(user.getId(), user.getPassword(), user.getUserName(), user.getRoles());
    }

    public boolean isUserVerified(String username) {
        User user = userRepo.findFirstByUserName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username)));

        return user != null;
    }
}
