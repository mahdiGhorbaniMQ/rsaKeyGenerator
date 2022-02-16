package com.example.demo.payload.response;

import lombok.Getter;
import lombok.Setter;

public class JwtResponse {
    @Getter @Setter private String token;
    private String type = "Bearer";
    @Getter @Setter private Long id;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String username;
    @Getter @Setter private String email;

    public JwtResponse(String accessToken, String firstName, String lastName, Long id, String username, String email) {
        this.token = accessToken;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
    }
}