package com.example.service;

import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.dto.RegistrationRequest;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    void register(RegistrationRequest request);
}
