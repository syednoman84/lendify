package com.example.usermanagementservice.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private Set<String> roleNames; // e.g., ["ADMIN", "REVIEWER"]
}

