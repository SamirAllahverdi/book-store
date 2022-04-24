package com.example.domain.enumeration;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum UserRole {
    USER, PUBLISHER;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        return Arrays.stream(UserRole.values())
                .map(r -> new SimpleGrantedAuthority(String.format("ROLE_%s", r)))
                .collect(Collectors.toSet());
    }
}
