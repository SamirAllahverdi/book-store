package com.example.service;

import com.example.config.LocaleMessageConfig;
import com.example.domain.User;
import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.dto.RegistrationRequest;
import com.example.dto.RegistrationResponse;
import com.example.exception.AlreadyExistException;
import com.example.repository.UserRepo;
import com.example.service.impl.AuthServiceImpl;
import com.example.util.AuthenticationCreator;
import com.example.util.MailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private MailSender mailSender;
    @Mock
    private AuthenticationCreator authenticationCreator;
    @Mock
    private LocaleMessageConfig localeMessageConfig;

    private PasswordEncoder encoder;
    private AuthService authService;

    private LoginRequest DUMMY_LOGIN_REQUEST;
    private RegistrationRequest DUMMY_REGISTRATION_REQUEST;
    private User DUMMY_USER;

    @BeforeEach
    void init() {
        encoder = new BCryptPasswordEncoder(10);
        authService = new AuthServiceImpl(userRepo, mailSender, authenticationCreator, encoder, localeMessageConfig);

        DUMMY_LOGIN_REQUEST = LoginRequest.builder()
                .email("smrallahverdieff@gmail.com")
                .password("Samir12345")
                .build();

        DUMMY_REGISTRATION_REQUEST = RegistrationRequest.builder()
                .email("smrallahverdieff@gmail.com")
                .firstName("Samir")
                .lastName("Allahverdiyev")
                .password("Samir12345")
                .role("PUBLISHER")
                .build();

        DUMMY_USER = User.builder()
                .email("smrallahverdieff@gmail.com")
                .firstName("Samir")
                .lastName("Allahverdiyev")
                .build();
    }

    @Test
    public void testLoginSuccess() {
        String token = encoder.encode(DUMMY_LOGIN_REQUEST.getPassword());
        when(authenticationCreator.create(DUMMY_LOGIN_REQUEST.getEmail(), DUMMY_LOGIN_REQUEST.getPassword())).thenReturn(token);

        LoginResponse loginResponse = authService.login(DUMMY_LOGIN_REQUEST);
        assertTrue("Login Response is null", loginResponse != null);
        assertEquals(loginResponse.getEmail(), DUMMY_LOGIN_REQUEST.getEmail());
        assertEquals(loginResponse.getToken(), token);
    }

    @Test
    public void testRegistrationSuccess() {
        String confirmationResponse = "Confirmation link was sent to your email successfully";
        when(localeMessageConfig.translate(AuthServiceImpl.REGISTRATION_RESPONSE)).thenReturn(confirmationResponse);
        RegistrationResponse registrationResponse = authService.register(DUMMY_REGISTRATION_REQUEST);

        assertTrue("Registration response is null", registrationResponse != null);
        assertEquals(registrationResponse.getMessage(), confirmationResponse);
    }

    @Test
    public void testThrowsExceptionIfAlreadyExistExceptionHappen() {
        when(userRepo.findByEmail(DUMMY_REGISTRATION_REQUEST.getEmail())).thenReturn(Optional.of(DUMMY_USER));
        assertThatThrownBy(() -> authService.register(DUMMY_REGISTRATION_REQUEST))
                .isInstanceOf(AlreadyExistException.class);
    }

}
