package com.example.service.impl;

import com.example.config.SecurityProperties;
import com.example.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final SecurityProperties properties;

    @Override
    public Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(properties.getJwt().getKey())
                .build()
                .parseClaimsJws(token)//TODO; jwt exporation catch
                .getBody();
    }

    @Override
    public String issue(Authentication authentication) {
        Duration duration = Duration.ofMinutes(properties.getJwt().getExpirationWithMinutes());
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(duration)))
                .claim(SecurityProperties.CLAIM_AUTHORITIES, getAuthorities(authentication))//
                .signWith(properties.getJwt().getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Set<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
