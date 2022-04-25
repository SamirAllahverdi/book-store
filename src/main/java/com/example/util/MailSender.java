package com.example.util;

import com.example.config.LocaleMessageConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component("customMailSender")
@RequiredArgsConstructor
public class MailSender {

    public static final String LOCAL_HOST = "http://localhost:";
    private final LocaleMessageConfig localeMessageConfig;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${server.port}")
    private String port;

    public void sendEmail(String email, String confirmationKey) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(email);
        msg.setFrom(from);
        msg.setSubject(localeMessageConfig.translate(LocaleMessageConfig.CONFIRMATION_MAIL_SUBJECT));
        String text = localeMessageConfig.translate(LocaleMessageConfig.CONFIRMATION_MAIL_TEXT) + buildLink(confirmationKey);
        msg.setText(text);
        javaMailSender.send(msg);
    }


    private String buildLink(String confirmationKey) {
        return UriComponentsBuilder.fromUriString(LOCAL_HOST + port + "/confirm")
                .queryParam("confirmationKey", confirmationKey)
                .buildAndExpand().toString();
    }

}
