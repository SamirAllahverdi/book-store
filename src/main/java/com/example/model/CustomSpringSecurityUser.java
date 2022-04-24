package com.example.model;

import com.example.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomSpringSecurityUser extends User {

    public CustomSpringSecurityUser(String username, String password, Set<Role> roles) {
        super(username, password, getAuthorities(roles));
    }

    private static Set<SimpleGrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(String.format("ROLE_%s", r.getName())))
                .collect(Collectors.toUnmodifiableSet());
    }
}
