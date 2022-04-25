package com.example.dto;

import com.example.domain.enumeration.UserRole;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @Email
    private String email;
    @NotEmpty
    @ToString.Exclude
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;
}
