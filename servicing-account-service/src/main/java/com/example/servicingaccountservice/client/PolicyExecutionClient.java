package com.example.servicingaccountservice.client;

import com.example.servicingaccountservice.config.FeignClientConfig;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "policy-management", url = "${services.policy-management.base-url}", configuration = FeignClientConfig.class)
public interface PolicyExecutionClient {

    @PostMapping("/api/policies/execute")
    JsonNode executePolicy(
            @RequestParam("applicationId") UUID applicationId,
            @RequestParam("productId") String productId,
            @RequestParam("requestUrl") String requestUrl
    );
}

