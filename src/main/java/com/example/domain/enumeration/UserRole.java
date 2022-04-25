package com.example.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum UserRole {
    USER(1), PUBLISHER(2);

    private final int id;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        return Arrays.stream(UserRole.values())
                .map(r -> new SimpleGrantedAuthority(String.format("ROLE_%s", r)))
                .collect(Collectors.toSet());
    }
}
