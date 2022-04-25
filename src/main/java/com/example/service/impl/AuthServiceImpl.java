package com.example.service.impl;

import com.example.config.LocaleMessageConfig;
import com.example.domain.User;
import com.example.dto.*;
import com.example.exception.AlreadyExistException;
import com.example.exception.InvalidConfirmationKeyException;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepo;
import com.example.service.AuthService;
import com.example.service.EmailConfirmationService;
import com.example.util.AuthenticationUtil;
import com.example.util.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public static final String REGISTRATION_RESPONSE = "registration-response";
    private final UserRepo userRepo;
    private final MailSender mailSender;
    private final AuthenticationUtil authenticationUtil;
    private final PasswordEncoder encoder;
    private final LocaleMessageConfig localeMessageConfig;
    private final EmailConfirmationService emailConfirmationService;

    @Override
    public LoginResponse login(LoginRequest request) {
        String token = authenticationUtil.create(request.getEmail(), request.getPassword());
        return LoginResponse.builder()
                .token(token)
                .email(request.getEmail())
                .build();
    }

    @Override
    @Transactional
    public RegistrationResponse register(RegistrationRequest request) {
        if (existsByEmail(request.getEmail()))
            throw new AlreadyExistException(request.getEmail());

        User user = userRepo.save(UserMapper.INSTANCE.toEntity(request, encoder.encode(request.getPassword())));

        String confirmationKey = emailConfirmationService.generateConfirmationKey(user);
        mailSender.sendEmail(request.getEmail(), confirmationKey);
        return new RegistrationResponse(localeMessageConfig.translate(REGISTRATION_RESPONSE));
    }

    @Override
    public ConfirmationResponse confirm(String confirmationKey) {
        return emailConfirmationService.findByConfirmationKey(confirmationKey)
                .map(emailConfirmation -> {
                    approveEmail(emailConfirmation.getUser());
                    return new ConfirmationResponse(localeMessageConfig.translate(LocaleMessageConfig.CONFIRMATION_RESPONSE));
                }).orElseThrow(() -> new InvalidConfirmationKeyException(confirmationKey));
    }

    private void approveEmail(User user) {
        user.setApproveEmail(true);
        userRepo.save(user);
    }

    private boolean existsByEmail(String email) {
        return userRepo.findByEmail(email).isPresent();
    }


}

