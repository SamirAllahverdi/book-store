package com.example.repository;

import com.example.domain.EmailConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailConfirmationRepo extends JpaRepository<EmailConfirmation, Long> {

    Optional<EmailConfirmation> findByConfirmationKey(String confirmationKey);
}
