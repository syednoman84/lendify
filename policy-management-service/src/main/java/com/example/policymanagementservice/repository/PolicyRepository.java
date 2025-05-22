package com.example.policymanagementservice.repository;

import com.example.policymanagementservice.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PolicyRepository extends JpaRepository<Policy, UUID> {
    Optional<Policy> findByName(String name);
    Optional<Policy> findByProductId(String productId);
}
