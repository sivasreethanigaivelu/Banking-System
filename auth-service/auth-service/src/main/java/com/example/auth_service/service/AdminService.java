package com.example.auth_service.service;

import com.example.auth_service.dto.AdminRegisterRequest;
import com.example.auth_service.dto.CustomerResponse;
import com.example.auth_service.dto.RegisterResponse;
import com.example.auth_service.entity.Customer;
import com.example.auth_service.exception.UserAlreadyExistsException;
import com.example.auth_service.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminService(CustomerRepository customerRepository,
                        BCryptPasswordEncoder passwordEncoder) {

        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse createAdmin(AdminRegisterRequest request) {

        if (customerRepository.existsByPhone(request.getPhone())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        Customer admin = new Customer();

        admin.setFullName(request.getFullName());
        admin.setPhone(request.getPhone());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole("ADMIN");
        admin.setActive(true);

        customerRepository.save(admin);

        return new RegisterResponse(
                "Admin created successfully",
                admin.getRole()
        );
    }
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customer -> new CustomerResponse(
                        customer.getFullName(),
                        customer.getPhone(),
                        customer.getRole()
                ))
                .toList();
    }
}
