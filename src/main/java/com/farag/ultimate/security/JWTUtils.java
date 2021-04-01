package com.farag.ultimate.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtils {
    @Value("${auth.expiration}")
    private long expiration;
    @Value("${auth.secret}")
    private String secret;
    @Value("${auth.prefix}")
    private String tokenPrefix;
    @Value("${auth.header}")
    private String headerString;

    public String addAuthentication(JWTUserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getUsername());
        claims.put("roles", user.getAuthorities());
        claims.put("id", user.getId());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsername(String token) throws ExpiredJwtException{
        String userName;
        userName = getClaims(token).getSubject();
        return userName;
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expirationDate = getClaims(token).getExpiration();
            return expirationDate.before(new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            return true;
        }
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) throws ExpiredJwtException {
        JWTUserDetails user = (JWTUserDetails) userDetails;
        final String username = getUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private Claims getClaims(String token) {

        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}
