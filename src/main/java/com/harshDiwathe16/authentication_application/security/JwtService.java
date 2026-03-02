package com.harshDiwathe16.authentication_application.security;

import com.harshDiwathe16.authentication_application.entity.Role;
import com.harshDiwathe16.authentication_application.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

@Service
@Getter
@Setter
public class JwtService {

    private final SecretKey key;
    private final long accessTtlSeconds;
    private final long refreshTtlSeconds;
    private final String issuer;

    public JwtService(Environment env) {

        String secret = env.getProperty("security.jwt.secret");
        String issuerProp = env.getProperty("security.jwt.issuer");

        Long access =
                Long.parseLong(env.getProperty(
                        "security.jwt.access-ttl-seconds", "3600"));

        Long refresh =
                Long.parseLong(env.getProperty(
                        "security.jwt.refresh-ttl-seconds", "86400"));

        // DEBUG (remove later)
        System.out.println("JWT SECRET = " + secret);
        System.out.println("JWT LENGTH = " + (secret == null ? 0 : secret.length()));

        if (secret == null || secret.trim().length() < 32) {
            throw new IllegalArgumentException("Invalid Secret");
        }

        this.key =
                Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        this.issuer = issuerProp;
        this.accessTtlSeconds = access;
        this.refreshTtlSeconds = refresh;
    }

    public String generateAccessToken(User user) {

        Instant now = Instant.now();

        List<String> roles =
                user.getRoles() == null ? List.of()
                        : user.getRoles().stream()
                        .map(Role::getRoleName)
                        .toList();

        return Jwts.builder()
                .subject(user.getId().toString())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(accessTtlSeconds)))
                .claims(Map.of(
                        "email", user.getEmail(),
                        "roles", roles,
                        "typ", "access"))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }

    public boolean isAccessToken(String token) {
        return "access".equals(
                parseToken(token).getPayload().get("typ"));
    }
}