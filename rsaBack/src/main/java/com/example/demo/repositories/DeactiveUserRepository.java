package com.example.demo.repositories;

import com.example.demo.models.DeactiveUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeactiveUserRepository extends JpaRepository<DeactiveUser,Long> {
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<DeactiveUser> findByUsername(String username);

    DeactiveUser findByEmail(String email);
}
