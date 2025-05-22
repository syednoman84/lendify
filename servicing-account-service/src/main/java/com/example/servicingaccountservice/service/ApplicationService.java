package com.example.servicingaccountservice.service;

import com.example.servicingaccountservice.dto.request.ApplicationPatchRequest;
import com.example.servicingaccountservice.entity.Application;
import com.example.servicingaccountservice.entity.UserApplication;
import com.example.servicingaccountservice.repository.ApplicationRepository;
import com.example.servicingaccountservice.repository.UserApplicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class ApplicationService {

    @Autowired private UserApplicationRepository userAppRepo;
    @Autowired private ApplicationRepository applicationRepo;
    @Autowired private ObjectMapper objectMapper;

    public Optional<Application> getActiveApplicationForUser(UUID userId) {
        return userAppRepo.findByUserIdAndActiveTrue(userId)
                .flatMap(ua -> applicationRepo.findByApplicationId(ua.getApplicationId()));
    }

    public Application createNewApplication(UUID userId, String productId, String requestUrl) {
        UUID appId = UUID.randomUUID();
        Instant now = Instant.now();

        // 1. Save the user-application mapping
        UserApplication userApp = new UserApplication();
        userApp.setUserId(userId);
        userApp.setApplicationId(appId);
        userApp.setProductId(productId);
        userAppRepo.save(userApp);

        // 2. Create base JSON structure with metadata
        ObjectNode initialData = objectMapper.createObjectNode();
        initialData.put("applicationId", appId.toString());
        initialData.put("createdAt", now.toString());
        initialData.put("modifiedAt", now.toString());
        initialData.put("requestUrl", requestUrl);

        // 3. Save application record
        Application app = new Application();
        app.setApplicationId(appId);
        app.setCreatedAt(now);
        app.setModifiedAt(now);
        app.setRequestUrl(requestUrl);
        app.setData(initialData);
        return applicationRepo.save(app);
    }


    public Application patchApplication(UUID applicationId, ApplicationPatchRequest patchRequest) throws Exception {
        Application existingApp = applicationRepo.findByApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        ObjectNode currentData = (ObjectNode) existingApp.getData();
        ObjectNode patchData = (ObjectNode) objectMapper.readTree(patchRequest.getPatchJson());

        currentData.setAll(patchData);
        existingApp.setData(currentData);
        existingApp.setModifiedAt(Instant.now());

        return applicationRepo.save(existingApp);
    }

}
