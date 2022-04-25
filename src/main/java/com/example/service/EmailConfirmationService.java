package com.example.service;

import com.example.domain.EmailConfirmation;
import com.example.domain.User;

import java.util.Optional;

public interface EmailConfirmationService {
    public String generateConfirmationKey(User user);

    Optional<EmailConfirmation> findByConfirmationKey(String confirmationKey);
}
