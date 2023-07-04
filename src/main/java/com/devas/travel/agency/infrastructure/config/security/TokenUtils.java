package com.devas.travel.agency.infrastructure.config.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private final static String ACCESS_TOKEN_SECRET = "BbYp3s6v9y$B&E)H@McQfTjWnZr4u7x!";

    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 86400000L;

    private TokenUtils() {
    }

    public static String createToken(String userName, String role, int roleId) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role);
        extraClaims.put("roleId", roleId);
        extraClaims.put("userName", userName);

        return Jwts.builder()
                .setSubject(userName)
                .addClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS))
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();

    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            var authorities = claims.get("role");
            var roleId = claims.get("roleId");
            var userName = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(userName, null, Collections.emptyList());

        } catch (JwtException e) {
            return null;

        }
    }

}
