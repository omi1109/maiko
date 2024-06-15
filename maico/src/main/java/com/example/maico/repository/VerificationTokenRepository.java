package com.example.maico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.maico.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
    public VerificationToken findByToken(String token);
}
