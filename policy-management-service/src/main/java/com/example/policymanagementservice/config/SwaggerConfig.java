package com.example.policymanagementservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiDocs() {
        return new OpenAPI().info(new Info()
                .title("Policy Management API")
                .description("Upload and manage policies")
                .version("1.0"));
    }
}
