package com.farag.ultimate.security;

import com.farag.ultimate.models.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
public class JWTUserDetails implements UserDetails {
    private static final long serialVersionUID = 5155720064139820502L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    private final String password;
    private final String username;

    private final Collection<? extends GrantedAuthority> authorities;

    public JWTUserDetails(Long id, String password, String username, Set<Role> roles) {
        this.id = id;
        this.password = password;
        this.username = username;
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        }
        this.authorities = authorities;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
