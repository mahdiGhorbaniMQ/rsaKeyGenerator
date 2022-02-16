package com.example.demo.payload.response;

import lombok.Getter;
import lombok.Setter;

public class UserDetailsResponse {
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String email;
    @Getter @Setter private String username;
    public UserDetailsResponse(){}
    public UserDetailsResponse(String firstName,String lastName,String email,String username){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
    }
}
