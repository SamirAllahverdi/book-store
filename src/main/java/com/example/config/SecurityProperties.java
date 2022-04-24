package com.example.config;

import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    public static final String BEARER = "Bearer";

    public static final String CLAIM_AUTHORITIES = "authorities";

    private final JwtProperties jwt = new JwtProperties();

    private final CorsConfiguration cors = new CorsConfiguration();

    @Data
    public static class JwtProperties {
        private String secretKey;
        private long expirationWithMinutes;
        public Key getKey() {
            return Keys.hmacShaKeyFor(secretKey.getBytes());
        }
    }
}
