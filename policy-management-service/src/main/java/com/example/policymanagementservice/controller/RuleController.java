package com.example.policymanagementservice.controller;

import com.example.policymanagementservice.entity.Rule;
import com.example.policymanagementservice.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @GetMapping("/by-policy/{policyId}")
    public ResponseEntity<List<Rule>> getRulesByPolicy(@PathVariable UUID policyId) {
        return ResponseEntity.ok(ruleService.getRulesByPolicyId(policyId));
    }

    @PostMapping
    public ResponseEntity<Rule> createRule(@RequestBody Rule rule) {
        return ResponseEntity.ok(ruleService.saveRule(rule));
    }
}
