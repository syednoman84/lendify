package com.example.policymanagementservice.controller;

import com.example.policymanagementservice.entity.Policy;
import com.example.policymanagementservice.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    @Autowired private PolicyService policyService;

    @PostMapping
    public ResponseEntity<Policy> savePolicy(@RequestBody Policy policy) {
        return ResponseEntity.status(HttpStatus.CREATED).body(policyService.savePolicy(policy));
    }

    @GetMapping("/by-product")
    public ResponseEntity<Policy> getByProduct(@RequestParam String productId) {
        return policyService.getPolicyByProductId(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Policy> getById(@PathVariable UUID id) {
        return policyService.getPolicyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
