package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @Email
    private String email;
    @NotEmpty
    private String password;
    private String firstName;
    private String lastName;
    @NotEmpty
    private String role;
}
