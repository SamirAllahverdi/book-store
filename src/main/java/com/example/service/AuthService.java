package com.example.service;

import com.example.dto.*;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    RegistrationResponse register(RegistrationRequest request);
    ConfirmationResponse confirm(String confirmationKey);
}
