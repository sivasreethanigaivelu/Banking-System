package com.example.auth_service.service;

import com.example.auth_service.entity.Customer;
import com.example.auth_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByPhone(phone).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(customer.getPhone(), customer.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + customer.getRole())));
    }
}
