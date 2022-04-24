package com.example.service.impl;

import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.dto.RegistrationRequest;
import com.example.repository.UserRepo;
import com.example.service.AuthService;
import com.example.util.AuthenticationCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final AuthenticationCreator authenticationCreator;

    @Override
    public LoginResponse login(LoginRequest request) {
        String token = authenticationCreator.create(request.getEmail(), request.getPassword());
        return LoginResponse.builder()
                .token(token)
                .email(request.getEmail())
                .build();
    }

    @Override
    public void register(RegistrationRequest request) {

    }
}

