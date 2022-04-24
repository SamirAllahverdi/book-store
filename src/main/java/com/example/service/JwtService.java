package com.example.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtService {
    String issue(Authentication authentication);
    Claims parse(String token);
}
