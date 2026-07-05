package com.example.auth_service.controller;

import com.example.auth_service.dto.AdminRegisterRequest;
import com.example.auth_service.dto.CustomerResponse;
import com.example.auth_service.dto.RegisterResponse;
import com.example.auth_service.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create")
    public ResponseEntity<RegisterResponse> createAdmin(
            @Valid @RequestBody AdminRegisterRequest request) {

        RegisterResponse response = adminService.createAdmin(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

        return ResponseEntity.ok(adminService.getAllCustomers());

    }
}
