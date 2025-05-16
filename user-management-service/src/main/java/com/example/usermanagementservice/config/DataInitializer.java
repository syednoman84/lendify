package com.example.usermanagementservice.config;


import com.example.usermanagementservice.entity.Permission;
import com.example.usermanagementservice.entity.Role;
import com.example.usermanagementservice.repository.PermissionRepository;
import com.example.usermanagementservice.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final PermissionRepository permissionRepo;

    public DataInitializer(RoleRepository roleRepo, PermissionRepository permissionRepo) {
        this.roleRepo = roleRepo;
        this.permissionRepo = permissionRepo;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Define permissions
        List<String> permissionNames = List.of(
                "VIEW_APPLICATIONS",
                "APPROVE_APPLICATIONS",
                "UPLOAD_DOCUMENTS",
                "VIEW_OFFERS"
        );

        Map<String, Permission> permissions = new HashMap<>();
        for (String name : permissionNames) {
            Permission permission = permissionRepo.findByName(name).orElseGet(() -> {
                Permission newPerm = new Permission();
                newPerm.setName(name);
                return permissionRepo.save(newPerm);
            });
            permissions.put(name, permission);
        }

        // Create roles and assign permissions
        if (roleRepo.findByName("CUSTOMER").isEmpty()) {
            Role customer = new Role();
            customer.setName("CUSTOMER");
            customer.setPermissions(Set.of(
                    permissions.get("UPLOAD_DOCUMENTS"),
                    permissions.get("VIEW_OFFERS")
            ));
            roleRepo.save(customer);
        }

        if (roleRepo.findByName("ADMIN").isEmpty()) {
            Role admin = new Role();
            admin.setName("ADMIN");
            admin.setPermissions(Set.of(
                    permissions.get("VIEW_APPLICATIONS"),
                    permissions.get("APPROVE_APPLICATIONS")
            ));
            roleRepo.save(admin);
        }
    }
}

