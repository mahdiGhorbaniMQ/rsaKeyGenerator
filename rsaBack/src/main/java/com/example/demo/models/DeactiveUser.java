package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "deactive_users")
public class DeactiveUser {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id @Getter @Setter @Column
    private Long id;

    @Column @Getter @Setter
    private String firstName;

    @Column @Getter @Setter
    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Column @Getter @Setter @Email
    private String email;

    @Size(max = 20)
    @Column @Getter @Setter
    private String username;

    @Size(max = 120)
    @Column @Getter @Setter
    private String password;

    @Size(max = 10)
    @Column @Getter @Setter
    private String activationCode;

    public DeactiveUser(){}
    public DeactiveUser(String firstName,String lastName,String email,String username,String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;

        int code = (int)(Math.random()*89999999);
        code+=10000000;
        this.activationCode = String.valueOf(code);
    }
}
