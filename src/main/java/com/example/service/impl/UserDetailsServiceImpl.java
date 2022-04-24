package com.example.service.impl;

import com.example.exception.CustomNotFoundException;
import com.example.model.CustomSpringSecurityUser;
import com.example.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email)
                .map(a -> new CustomSpringSecurityUser(a.getEmail(), a.getPassword(), a.getAuthorities()))
                .orElseThrow(() -> new CustomNotFoundException(email));
    }
}