package com.example.service;

import com.example.config.LocaleMessageConfig;
import com.example.domain.EmailConfirmation;
import com.example.domain.User;
import com.example.domain.enumeration.UserRole;
import com.example.dto.*;
import com.example.exception.AlreadyExistException;
import com.example.exception.InvalidConfirmationKeyException;
import com.example.repository.UserRepo;
import com.example.service.impl.AuthServiceImpl;
import com.example.util.AuthenticationUtil;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private MailSender mailSender;
    @Mock
    private AuthenticationUtil authenticationUtil;
    @Mock
    private LocaleMessageConfig localeMessageConfig;
    @Mock
    private EmailConfirmationService emailConfirmationService;

    private PasswordEncoder encoder;
    private AuthService authService;

    private LoginRequest DUMMY_LOGIN_REQUEST;
    private RegistrationRequest DUMMY_REGISTRATION_REQUEST;
    private User DUMMY_USER;
    private EmailConfirmation DUMMY_EMAIL_CONFIRMATION;


    @BeforeEach
    void init() {
        encoder = new BCryptPasswordEncoder(10);
        authService = new AuthServiceImpl(userRepo, mailSender, authenticationUtil, encoder, localeMessageConfig, emailConfirmationService);

        DUMMY_LOGIN_REQUEST = LoginRequest.builder()
                .email("smrallahverdieff@gmail.com")
                .password("Samir12345")
                .build();

        DUMMY_REGISTRATION_REQUEST = RegistrationRequest.builder()
                .email("smrallahverdieff@gmail.com")
                .firstName("Samir")
                .lastName("Allahverdiyev")
                .password("Samir12345")
                .role(UserRole.PUBLISHER)
                .build();

        DUMMY_USER = User.builder()
                .email("smrallahverdieff@gmail.com")
                .firstName("Samir")
                .lastName("Allahverdiyev")
                .build();

        DUMMY_EMAIL_CONFIRMATION = EmailConfirmation.builder()
                .confirmationKey("6fc448cd-29ec-4617-9209-df6d987b649c")
                .user(DUMMY_USER)
                .build();
    }

    @Test
    public void testLoginSuccess() {
        String token = encoder.encode(DUMMY_LOGIN_REQUEST.getPassword());
        when(authenticationUtil.create(DUMMY_LOGIN_REQUEST.getEmail(), DUMMY_LOGIN_REQUEST.getPassword())).thenReturn(token);

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


    @Test
    public void testConfirmSuccess() {
        String confirmationResponseTxt = "Your email accepted";
        when(emailConfirmationService.findByConfirmationKey(DUMMY_EMAIL_CONFIRMATION.getConfirmationKey())).thenReturn(Optional.ofNullable(DUMMY_EMAIL_CONFIRMATION));
        when(localeMessageConfig.translate(LocaleMessageConfig.CONFIRMATION_RESPONSE)).thenReturn(confirmationResponseTxt);

        ConfirmationResponse confirmationResponse = authService.confirm(DUMMY_EMAIL_CONFIRMATION.getConfirmationKey());

        assertTrue("Confirmation response is null", confirmationResponse != null);
        assertEquals(confirmationResponse.getMessage(), confirmationResponseTxt);
    }

    @Test
    public void testConfirmThrowsExceptionIfInvalidConfirmationKeyExceptionHappen() {
        when(emailConfirmationService.findByConfirmationKey(DUMMY_EMAIL_CONFIRMATION.getConfirmationKey())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.confirm(DUMMY_EMAIL_CONFIRMATION.getConfirmationKey()))
                .isInstanceOf(InvalidConfirmationKeyException.class);
    }
}
