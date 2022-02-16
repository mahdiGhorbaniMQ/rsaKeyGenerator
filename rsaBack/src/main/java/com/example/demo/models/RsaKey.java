package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.security.PrivateKey;
import java.security.PublicKey;

@Entity
public class RsaKey {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column @Id
    private Long id;

    @Getter @Setter @Column
    private String username;

    @Getter @Setter @Column(length = 10000)
    private PublicKey publicKey;

    @Getter @Setter @Column(length = 10000)
    private PrivateKey privateKey;

    public RsaKey(){}

    public RsaKey(String username, PublicKey publicKey, PrivateKey privateKey){
        this.username = username;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }
}
