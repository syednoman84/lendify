package com.example.servicingaccountservice.repository;

import com.example.servicingaccountservice.entity.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserApplicationRepository extends JpaRepository<UserApplication, UUID> {
    Optional<UserApplication> findByUserIdAndActiveTrue(UUID userId);
}

