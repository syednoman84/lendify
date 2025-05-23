package com.example.policymanagementservice.controller;

import com.example.policymanagementservice.service.PolicyExecutionService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/policies")
public class PolicyExecutionController {

    @Autowired
    private PolicyExecutionService policyExecutionService;

    @PostMapping("/execute")
    public ResponseEntity<JsonNode> executePolicy(
            @RequestParam UUID applicationId,
            @RequestParam String productId,
            @RequestParam String requestUrl) {
        JsonNode firstTask = policyExecutionService.executePolicy(applicationId, productId, requestUrl);
        return ResponseEntity.ok(firstTask);
    }
}
