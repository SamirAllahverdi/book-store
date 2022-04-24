package com.example.config;

import com.example.service.JwtService;
import com.example.service.impl.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TokenVerifierFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(this::isTokenBearer)
                .flatMap(this::toAuthentication)
                .ifPresent(auth -> SecurityContextHolder.getContext().setAuthentication(auth));

        filterChain.doFilter(request, response);
    }

    private boolean isTokenBearer(String header) {
        return header.startsWith(SecurityProperties.BEARER);
    }

    private Optional<Authentication> toAuthentication(String header) {
        String token = header.substring(SecurityProperties.BEARER.length()).trim();
        Claims claims = jwtService.parse(token);
        if (claims.getExpiration().before(new Date())) {
            return Optional.empty();
        }
        return Optional.of(getAuthenticationBearer(claims));
    }

    private Authentication getAuthenticationBearer(Claims claims) {
        List<?> authorities = claims.get(SecurityProperties.CLAIM_AUTHORITIES, List.class);
        List<GrantedAuthority> grantedAuthorities = authorities
                .stream()
                .map(a -> new SimpleGrantedAuthority(a.toString()))
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", grantedAuthorities);
    }


}
