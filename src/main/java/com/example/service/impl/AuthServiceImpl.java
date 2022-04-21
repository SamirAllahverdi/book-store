package com.example.service.impl;

import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.dto.RegistrationRequest;
import com.example.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public LoginResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public void register(RegistrationRequest request) {

    }
}
