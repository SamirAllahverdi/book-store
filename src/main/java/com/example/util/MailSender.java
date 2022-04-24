package com.example.util;

import com.example.config.LocaleMessageConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("customMailSender")
@RequiredArgsConstructor
public class MailSender {
    private final LocaleMessageConfig localeMessageConfig;
    private final JavaMailSender javaMailSender;
    public static final String CONFIRMATION_MAIL_SUBJECT = "confirmation-mail-subject";
    public static final String CONFIRMATION_MAIL_TEXT = "confirmation-mail-text";

    public void sendEmail(String email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(localeMessageConfig.translate(CONFIRMATION_MAIL_SUBJECT));
        msg.setText(localeMessageConfig.translate(CONFIRMATION_MAIL_TEXT)); //TODO:
        javaMailSender.send(msg);
    }

}
