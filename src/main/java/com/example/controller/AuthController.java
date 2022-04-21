package com.example.controller;

import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.dto.RegistrationRequest;
import com.example.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    //TODO: COmmonREquestFilter
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest request) {
        var response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public String register(@RequestBody @Validated RegistrationRequest request) {
        authService.register(request);
        return "";
    }

}
