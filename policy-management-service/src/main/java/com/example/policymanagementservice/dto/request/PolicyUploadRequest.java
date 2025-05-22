package com.example.policymanagementservice.dto.request;

import lombok.Data;

@Data
public class PolicyUploadRequest {
    private String name;
    private Integer version;
    private String status;
    private String content; // JSON string
}
