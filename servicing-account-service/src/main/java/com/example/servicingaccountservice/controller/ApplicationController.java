package com.example.servicingaccountservice.controller;

import com.example.servicingaccountservice.dto.request.ApplicationPatchRequest;
import com.example.servicingaccountservice.entity.Application;
import com.example.servicingaccountservice.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired private ApplicationService appService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getApplication(@PathVariable UUID userId) {
        return appService.getActiveApplicationForUser(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(
            @RequestParam UUID userId,
            @RequestParam String productId,
            @RequestParam String requestUrl) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appService.createNewApplication(userId, productId, requestUrl));
    }


    @PatchMapping("/{applicationId}")
    public ResponseEntity<Application> patchApplication(
            @PathVariable UUID applicationId,
            @RequestBody ApplicationPatchRequest patchRequest) {
        try {
            return ResponseEntity.ok(appService.patchApplication(applicationId, patchRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }
}
