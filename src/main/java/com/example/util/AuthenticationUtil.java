package com.example.util;

import com.example.domain.User;
import com.example.exception.UserNotFoundException;
import com.example.repository.UserRepo;
import com.example.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class AuthenticationUtil {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtService jwtService;
    private final UserRepo userRepo;

    public String create(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtService.issue(authentication);
    }

    public User getCurrentUser() {
        return getLoggedUser()
                .flatMap(userRepo::findByEmailAndApproveEmailTrue)
                .orElseThrow(UserNotFoundException::new);
    }


    private Optional<String> getLoggedUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication()).map(Principal::getName);
    }
}
