package com.tix.nostra.nostra_tix.security.util;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import com.tix.nostra.nostra_tix.security.model.AccessJwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtTokenFactory {
    private final Key secret;

    public JwtTokenFactory(Key secret) {
        super();
        this.secret = secret;
    }

    public AccessJwtToken createAccessJwtToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = Jwts.claims().subject(username)
                .add("scopes", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .build();

        // Waktu Kapan Token Dibuat
        LocalDateTime currentTime = LocalDateTime.now();
        Date currentTimeDate = Date.from(currentTime.atZone(ZoneId.of("Asia/Jakarta")).toInstant());

        // Waktu Kapan Token Berakhir
        LocalDateTime expiryDate = currentTime.plusMinutes(15);
        Date expiryDateDate = Date.from(expiryDate.atZone(ZoneId.of("Asia/Jakarta")).toInstant());

        // Membuat Token
        String token = Jwts.builder().claims(claims)
                .issuer("https://ageng.com")
                .issuedAt(currentTimeDate)
                .expiration(expiryDateDate)
                .signWith(secret)
                .compact();

        return new AccessJwtToken(token, claims);

    }
}
