package com.example.demo.payload.request;

import lombok.Getter;

public class RegisterRequest {
    @Getter String activationCode;
    @Getter String email;
}
