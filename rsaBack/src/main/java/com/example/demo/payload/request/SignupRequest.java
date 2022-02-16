package com.example.demo.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignupRequest {
    @Getter @Setter
    @NotBlank
    @Size(min = 3, max = 20)
    private String firstName;

    @Getter @Setter
    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;

    @Getter @Setter
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @Getter @Setter
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Getter @Setter
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}