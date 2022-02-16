package com.example.demo.payload.response;

import lombok.Getter;
import lombok.Setter;

public class SignInResponse {
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String email;
    @Getter @Setter private String username;
    @Getter @Setter private String token;

    public SignInResponse(){}

    public SignInResponse(String firstName, String lastName, String email, String username, String token){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.token = token;
    }
}
