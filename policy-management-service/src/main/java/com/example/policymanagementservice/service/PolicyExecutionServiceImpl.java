package com.example.policymanagementservice.service;

import com.example.policymanagementservice.entity.Policy;
import com.example.policymanagementservice.entity.PolicyExecutionResult;
import com.example.policymanagementservice.repository.PolicyExecutionResultRepository;
import com.example.policymanagementservice.repository.PolicyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;


import java.util.Optional;

@Service
public class PolicyExecutionServiceImpl implements PolicyExecutionService {

    @Autowired
    private PolicyRepository policyRepository;
    @Autowired private PolicyExecutionResultRepository executionResultRepository;
    @Autowired private ObjectMapper objectMapper;

    @Override
    public void executePolicy(UUID applicationId, String productId, String requestUrl) {
        // Step 1: Load the policy using the productId
        Optional<Policy> optionalPolicy = policyRepository.findByProductId(productId);
        if (optionalPolicy.isEmpty()) {
            throw new RuntimeException("No policy found for productId: " + productId);
        }
        Policy policy = optionalPolicy.get();

        // Step 2: Start with initial data (this will later be fetched from the application service)
        JsonNode initialExecutionContext = objectMapper.createObjectNode();

        // Step 3: Evaluate first task (just fetch the first customer task from policy for now)
        JsonNode firstTask = policy.getStructure()
                .get("tasks")
                .elements().next(); // assuming task array is not empty

        // Step 4: Save to PolicyExecutionResult
        PolicyExecutionResult result = new PolicyExecutionResult();
        result.setApplicationId(applicationId);
        result.setPolicyName(policy.getName());
        result.setExecutionContext(initialExecutionContext);
        result.setLastExecutedAt(Instant.now());
        result.setCurrentTask(firstTask);

        executionResultRepository.save(result);
    }
}

