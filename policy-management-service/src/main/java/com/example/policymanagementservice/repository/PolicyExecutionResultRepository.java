package com.example.policymanagementservice.repository;

import com.example.policymanagementservice.entity.PolicyExecutionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PolicyExecutionResultRepository extends JpaRepository<PolicyExecutionResult, UUID> {
    Optional<PolicyExecutionResult> findByApplicationId(UUID applicationId);
}
