package com.example.policymanagementservice.service;

import com.example.policymanagementservice.entity.Rule;
import com.example.policymanagementservice.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    public List<Rule> getRulesByPolicyId(UUID policyId) {
        return ruleRepository.findByPolicyId(policyId);
    }

    public Rule saveRule(Rule rule) {
        return ruleRepository.save(rule);
    }
}
