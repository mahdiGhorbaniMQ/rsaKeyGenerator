package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
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

    @NotBlank
    @Size(max = 20)
    @Column @Getter @Setter
    private String username;

    @NotBlank
    @Size(max = 120)
    @Column @Getter @Setter
    private String password;

    public User(){}
    public User(String firstName,String lastName,String email,String username,String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
