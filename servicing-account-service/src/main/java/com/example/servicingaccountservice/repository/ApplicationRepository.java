package com.example.servicingaccountservice.repository;

import com.example.servicingaccountservice.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    Optional<Application> findByApplicationId(UUID applicationId);
}
