package com.example.policymanagementservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.UUID;

public interface PolicyExecutionService {
    JsonNode executePolicy(UUID applicationId, String productId, String requestUrl);
}
