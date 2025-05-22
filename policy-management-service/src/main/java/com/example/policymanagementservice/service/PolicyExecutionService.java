package com.example.policymanagementservice.service;

import java.util.UUID;

public interface PolicyExecutionService {
    void executePolicy(UUID applicationId, String productId, String requestUrl);
}
