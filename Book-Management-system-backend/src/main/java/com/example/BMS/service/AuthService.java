package com.example.BMS.service;

import com.example.BMS.dto.RegisterDto;
import com.example.BMS.model.Customer;
import com.example.BMS.model.Role;
import com.example.BMS.model.User;
import com.example.BMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import org.slf4j.Logger; // <-- Add this import
import org.slf4j.LoggerFactory; // <-- Add this import

@Service
public class AuthService {

    // --- DEBUG LOGGING ---
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(RegisterDto registerDto) {
        // --- DEBUG LOGGING ---
        logger.info("Registering user with email: {}", registerDto.getEmail());
        logger.info("Raw password before encoding: '{}'", registerDto.getPassword());

        // 1. Create the User object for authentication
        User newUser = new User();
        newUser.setEmail(registerDto.getEmail());

        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        newUser.setPassword(encodedPassword);

        // --- DEBUG LOGGING ---
        logger.info("Hashed password being saved: '{}'", encodedPassword);

        newUser.setRole(Role.ROLE_USER);

        // 2. Create the Customer object for personal details
        Customer newCustomer = new Customer();
        newCustomer.setFullName(registerDto.getFullName());
        newCustomer.setMobileNumber(registerDto.getMobileNumber());
        newCustomer.setRegisterOn(LocalDate.now());

        // 3. Link the User and Customer together
        newUser.setCustomer(newCustomer);
        newCustomer.setUser(newUser);

        // 4. Save the User. Thanks to cascading, the Customer will also be saved.
        return userRepository.save(newUser);
    }
}