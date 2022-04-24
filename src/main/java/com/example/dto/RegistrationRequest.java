package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
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
    @NotEmpty
    private String role;
}
