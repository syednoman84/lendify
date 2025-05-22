package com.example.servicingaccountservice.dto.request;

import lombok.Data;

@Data
public class ApplicationPatchRequest {
    private String patchJson; // Partial JSON from the frontend
}
