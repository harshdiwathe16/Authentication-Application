package com.harshDiwathe16.authentication_application.security;

import com.harshDiwathe16.authentication_application.entity.Role;
import com.harshDiwathe16.authentication_application.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
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

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-ttl-seconds}") long accessTtlSeconds  ,
            @Value("${security.jwt.refresh-ttl-seconds}") long refreshTtlSeconds ,
            @Value("${security.jwt.issuer}") String issuer)
    {
        if (secret == null || secret.length() < 64)
        {
            throw new IllegalArgumentException("Invalid Secret");
        }

        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        this.issuer = issuer;
        this.accessTtlSeconds = accessTtlSeconds;
        this.refreshTtlSeconds = refreshTtlSeconds;
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

    // generate refresh token.
    public String generateRefreshToken(User user, String jti) {
        Instant now = Instant.now();
        return Jwts.builder()
                .id(jti)
                .subject(user.getId().toString())
                .issuer(issuer)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(refreshTtlSeconds)))
                .claim("typ", "refresh")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    // Parse The Token
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