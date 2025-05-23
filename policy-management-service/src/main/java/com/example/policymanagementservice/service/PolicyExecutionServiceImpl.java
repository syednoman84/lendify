package com.example.policymanagementservice.service;

import com.example.policymanagementservice.entity.Policy;
import com.example.policymanagementservice.entity.PolicyExecutionResult;
import com.example.policymanagementservice.repository.PolicyExecutionResultRepository;
import com.example.policymanagementservice.repository.PolicyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class PolicyExecutionServiceImpl implements PolicyExecutionService {

    @Autowired private PolicyRepository policyRepo;
    @Autowired private PolicyExecutionResultRepository executionResultRepo;
    @Autowired private ObjectMapper objectMapper;

    @Override
    public JsonNode executePolicy(UUID applicationId, String productId, String requestUrl) {
        // 1. Load policy by productId
        Policy policy = policyRepo.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Policy not found for product: " + productId));

        System.out.println("[PolicyExecutionServiceImpl] Fetched Policy name: " + policy.getName());

        // 2. Parse the structure and extract the first customer task
        JsonNode structure = policy.getStructure();
        JsonNode tasks = structure.path("tasks");

        Optional<JsonNode> firstTaskOpt = findFirstCustomerTask(tasks);
        if (firstTaskOpt.isEmpty()) {
            throw new RuntimeException("No customer-facing task found in policy");
        }

        JsonNode firstTask = firstTaskOpt.get();

        // 3. Create and store the policy execution result
        ObjectNode executionContext = objectMapper.createObjectNode();
        executionContext.put("currentTaskIndex", 0);
        executionContext.set("currentTask", firstTask);

        PolicyExecutionResult result = new PolicyExecutionResult();
        result.setApplicationId(applicationId);
        result.setPolicy(policy);
        result.setPolicyName(policy.getName());
        result.setLastExecutedAt(Instant.now());
        result.setExecutionContext(executionContext);

        executionResultRepo.save(result);

        // 4. Return the first task to present to customer
        return firstTask;
    }

    private Optional<JsonNode> findFirstCustomerTask(JsonNode tasks) {
        if (tasks.isArray()) {
            for (JsonNode task : tasks) {
                String type = task.path("type").asText();
                if ("CUSTOMER".equalsIgnoreCase(type)) {
                    return Optional.of(task);
                }
            }
        }
        return Optional.empty();
    }
}
