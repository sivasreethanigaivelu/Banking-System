package com.example.auth_service.service;

import com.example.auth_service.dto.*;
import com.example.auth_service.entity.Customer;
import com.example.auth_service.exception.UserAlreadyExistsException;
import com.example.auth_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public RegisterResponse register(RegisterRequest request) {

        if (customerRepository.existsByPhone(request.getPhone())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        Customer customer = new Customer();

        customer.setFullName(request.getFullName());

        customer.setPhone(request.getPhone());

        customer.setPassword(passwordEncoder.encode(request.getPassword()));

        customer.setRole("CUSTOMER");

        customer.setActive(true);

        customerRepository.save(customer);

        return new RegisterResponse("Customer Registered Successfully", customer.getRole());
    }
    public LoginResponse login(LoginRequest request) {

        Customer customer = customerRepository.findByPhone(request.getPhone()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(
                customer.getPhone(),
                customer.getRole(),
                customer.getCustomerId()
        );

        return new LoginResponse(token, "Login successful", customer.getRole());
    }
    public CustomerResponse getCustomer(String phone) {

        Customer customer = customerRepository.findByPhone(phone).orElseThrow(() -> new RuntimeException("User not found"));

        return new CustomerResponse(
                customer.getFullName(),
                customer.getPhone(),
                customer.getRole()
        );
    }
    public CustomerResponse updateCustomer(String oldPhone, UpdateCustomerRequest request) {

        Customer customer = customerRepository.findByPhone(oldPhone).orElseThrow(() -> new RuntimeException("User not found"));

        customer.setFullName(request.getFullName());
        customer.setPhone(request.getPhone());

        customerRepository.save(customer);

        return new CustomerResponse(
                customer.getFullName(),
                customer.getPhone(),
                customer.getRole()
        );
    }
    public CustomerResponse patchCustomer(String phone, PatchCustomerRequest request){

        Customer customer = customerRepository.findByPhone(phone).orElseThrow(() -> new RuntimeException("User not found"));

        if(request.getFullName()!=null){
            customer.setFullName(request.getFullName());
        }

        if(request.getPhone()!=null){
            customer.setPhone(request.getPhone());
        }

        customerRepository.save(customer);

        return new CustomerResponse(
                customer.getFullName(),
                customer.getPhone(),
                customer.getRole()
        );
    }
    public String deleteCustomer(String phone){

        Customer customer = customerRepository.findByPhone(phone).orElseThrow(() -> new RuntimeException("User not found"));

        customerRepository.delete(customer);

        return "Customer deleted successfully";
    }
}

