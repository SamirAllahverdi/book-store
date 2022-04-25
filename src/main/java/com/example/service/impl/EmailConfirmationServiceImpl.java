package com.example.service.impl;

import com.example.domain.EmailConfirmation;
import com.example.domain.User;
import com.example.repository.EmailConfirmationRepo;
import com.example.service.EmailConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailConfirmationServiceImpl implements EmailConfirmationService {

    private final EmailConfirmationRepo emailConfirmationRepo;

    @Override
    public String generateConfirmationKey(User user) {

        EmailConfirmation emailConfirmation = new EmailConfirmation();
        emailConfirmation.setUser(user);
        emailConfirmation.setConfirmationKey(UUID.randomUUID().toString());
        emailConfirmationRepo.save(emailConfirmation);

        return emailConfirmation.getConfirmationKey();
    }


    @Override
    public Optional<EmailConfirmation> findByConfirmationKey(String confirmationKey) {
       return emailConfirmationRepo.findByConfirmationKey(confirmationKey);
    }
}
