package com.preventhub.identity.security;

import com.preventhub.identity.domain.UserAccount;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationSeconds;

    public JwtService(@Value("${security.jwt.secret}") String secret,
                      @Value("${security.jwt.expiration-seconds}") long expirationSeconds) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationSeconds = expirationSeconds;
    }

    public String generate(UserAccount userAccount) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(userAccount.getEmail())
                .claim("name", userAccount.getFullName())
                .claim("roles", userAccount.getRoles().stream().map(Enum::name).toList())
                .claim("organization", userAccount.getOrganization())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expirationSeconds)))
                .signWith(secretKey)
                .compact();
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }
}
