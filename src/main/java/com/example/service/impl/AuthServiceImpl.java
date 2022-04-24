package com.example.service.impl;

import com.example.config.LocaleMessageConfig;
import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.dto.RegistrationRequest;
import com.example.dto.RegistrationResponse;
import com.example.exception.AlreadyExistException;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepo;
import com.example.service.AuthService;
import com.example.util.AuthenticationCreator;
import com.example.util.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public static final String REGISTRATION_RESPONSE = "registration-response";
    private final UserRepo userRepo;
    private final MailSender mailSender;
    private final AuthenticationCreator authenticationCreator;
    private final PasswordEncoder encoder;
    private final LocaleMessageConfig localeMessageConfig;

    @Override
    public LoginResponse login(LoginRequest request) { //TODO: password check
        String token = authenticationCreator.create(request.getEmail(), request.getPassword());
        return LoginResponse.builder()
                .token(token)
                .email(request.getEmail())
                .build();
    }

    @Override
    public RegistrationResponse register(RegistrationRequest request) {
        if (existsByEmail(request.getEmail()))
            throw new AlreadyExistException(request.getEmail());

        userRepo.save(UserMapper.INSTANCE.toEntity(request, encoder.encode(request.getPassword())));
        mailSender.sendEmail(request.getEmail());
        return new RegistrationResponse(localeMessageConfig.translate(REGISTRATION_RESPONSE));
    }


    private boolean existsByEmail(String email) {
        return userRepo.findByEmail(email).isPresent();
    }


}

