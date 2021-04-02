package com.farag.ultimate.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service

public class JWTFilter extends OncePerRequestFilter {
    @Value("${auth.header}")

    private String headerContent;
    private final JWTUtils jwtUtils;
    private final JWTUserDetailsService jwtUserDetailsService;

    public JWTFilter(JWTUtils jwtUtils, JWTUserDetailsService jwtUserDetailsService) {
        this.jwtUtils = jwtUtils;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader(headerContent);
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        if (header != null && securityContext.getAuthentication() == null) {
            String token = header.substring("Bearer ".length());
            String userName = jwtUtils.getUsername(token);
            if (userName != null) {
                UserDetails user = jwtUserDetailsService.loadUserByUsername(userName);
                if (jwtUtils.isTokenValid(token, user)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,
                            null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(authToken);
                }
            }

        }
        chain.doFilter(request, response);
    }
}
