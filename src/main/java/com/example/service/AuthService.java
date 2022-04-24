package com.example.service;

import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.dto.RegistrationRequest;
import com.example.dto.RegistrationResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    RegistrationResponse register(RegistrationRequest request);
}
