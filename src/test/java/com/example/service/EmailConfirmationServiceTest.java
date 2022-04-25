package com.example.service;

import com.example.domain.EmailConfirmation;
import com.example.repository.EmailConfirmationRepo;
import com.example.service.impl.EmailConfirmationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class EmailConfirmationServiceTest {

    @Mock
    private EmailConfirmationRepo emailConfirmationRepo;
    private EmailConfirmationService emailConfirmationService;
    private EmailConfirmation DUMMY_EMAIL_CONFIRMATION;

    @BeforeEach
    void init() {
        emailConfirmationService = new EmailConfirmationServiceImpl(emailConfirmationRepo);
        DUMMY_EMAIL_CONFIRMATION = EmailConfirmation.builder()
                .confirmationKey("6fc448cd-29ec-4617-9209-df6d987b649c")
                .build();
    }


    @Test
    public void testFindByConfirmationKey() {
        when(emailConfirmationRepo.findByConfirmationKey(DUMMY_EMAIL_CONFIRMATION.getConfirmationKey())).thenReturn(Optional.of(DUMMY_EMAIL_CONFIRMATION));

        Optional<EmailConfirmation> emailConfirmationOpt = emailConfirmationService.findByConfirmationKey(DUMMY_EMAIL_CONFIRMATION.getConfirmationKey());
        assertTrue("Email Confirmation is null", emailConfirmationOpt.isPresent());
        assertEquals(DUMMY_EMAIL_CONFIRMATION.getConfirmationKey(), emailConfirmationOpt.get().getConfirmationKey());
    }

}
