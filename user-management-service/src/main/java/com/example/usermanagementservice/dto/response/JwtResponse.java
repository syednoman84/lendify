package com.example.usermanagementservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String username;
    private Set<String> roles;
    private Set<String> permissions;
}

