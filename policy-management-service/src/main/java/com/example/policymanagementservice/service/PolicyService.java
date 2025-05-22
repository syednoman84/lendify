package com.example.policymanagementservice.service;

import com.example.policymanagementservice.entity.Policy;
import com.example.policymanagementservice.repository.PolicyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PolicyService {

    @Autowired private PolicyRepository policyRepo;
    @Autowired private ObjectMapper objectMapper;

    public Policy savePolicy(Policy policy) {
        return policyRepo.save(policy);
    }

    public Optional<Policy> getPolicyByProductId(String productId) {
        return policyRepo.findByProductId(productId);
    }

    public Optional<Policy> getPolicyById(UUID id) {
        return policyRepo.findById(id);
    }

    public Optional<Policy> getPolicyByName(String name) {
        return policyRepo.findByName(name);
    }
}
