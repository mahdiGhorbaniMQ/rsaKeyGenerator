package com.example.demo.repositories;

import com.example.demo.models.RsaKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.PrivateKey;

public interface RsaKeyRepository extends JpaRepository<RsaKey,Long> {
    RsaKey getByUsername(String username);
    Boolean existsByPrivateKey(PrivateKey privateKey);
    Boolean existsByUsername(String username);
}
