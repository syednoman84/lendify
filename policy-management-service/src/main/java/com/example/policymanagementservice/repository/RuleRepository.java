package com.example.policymanagementservice.repository;

import com.example.policymanagementservice.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RuleRepository extends JpaRepository<Rule, UUID> {
    List<Rule> findByPolicyId(UUID policyId);
}
