package com.example.usermanagementservice.service;

import com.example.usermanagementservice.dto.request.LoginRequest;
import com.example.usermanagementservice.dto.request.SignupRequest;
import com.example.usermanagementservice.dto.response.JwtResponse;
import com.example.usermanagementservice.entity.Permission;
import com.example.usermanagementservice.entity.Role;
import com.example.usermanagementservice.entity.User;
import com.example.usermanagementservice.repository.RoleRepository;
import com.example.usermanagementservice.repository.UserRepository;
import com.example.usermanagementservice.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtils jwtUtils;

    public JwtResponse signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        Set<Role> roles = request.getRoleNames().stream()
                .map(name -> roleRepository.findByName(name)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + name)))
                .collect(Collectors.toSet());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roles);

        userRepository.save(user);

        return generateJwtResponse(user);
    }

    public JwtResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return generateJwtResponse(user);
    }

    private JwtResponse generateJwtResponse(User user) {
        String token = jwtUtils.generateToken(user);

        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        Set<String> permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getName)
                .collect(Collectors.toSet());

        return new JwtResponse(token, user.getUsername(), roles, permissions);
    }
}
