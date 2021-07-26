package com.pedroso.diogo.assignment.authentication.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtil {
    @Value("${app.security.jwt.secret}")
    private String SECRET_KEY;

    @Value("${app.security.jwt.expiration-time-ms}")
    private int jwtExpirationMs;

    public String extractUsername(String token) {
        final String username = extractAllClaims(token).getSubject();
        return username;
    }

    public Date extractExpiration(String token) {
        final Date expiresOn = extractAllClaims(token).getExpiration();
        return expiresOn;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());
        claims.put("isAdmin", userDetails.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date expiresOn = new Date(System.currentTimeMillis() + jwtExpirationMs);
        String jwt = Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(expiresOn)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();

        return jwt;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}