package com.example.demo.payload.request;

import lombok.Getter;
import lombok.Setter;

public class SignInRequest {
    @Getter @Setter private String username;
    @Getter @Setter private String password;
}
