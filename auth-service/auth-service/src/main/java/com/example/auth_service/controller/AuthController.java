package com.example.auth_service.controller;

import com.example.auth_service.dto.*;
import com.example.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/customer/{phone}")
    public ResponseEntity<CustomerResponse> getCustomer(
            @PathVariable String phone){

        return ResponseEntity.ok(authService.getCustomer(phone));

    }
    @PutMapping("/customer/{phone}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable String phone,
            @Valid @RequestBody UpdateCustomerRequest request){

        return ResponseEntity.ok(authService.updateCustomer(phone, request));
    }
    @PatchMapping("/customer/{phone}")
    public ResponseEntity<CustomerResponse> patchCustomer(
            @PathVariable String phone,
            @RequestBody PatchCustomerRequest request){

        return ResponseEntity.ok(authService.patchCustomer(phone, request));
    }
    @DeleteMapping("/customer/{phone}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String phone){

        return ResponseEntity.ok(authService.deleteCustomer(phone));
    }
}