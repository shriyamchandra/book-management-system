package com.example.BMS.controller;

import com.example.BMS.dto.AuthResponseDto;
import com.example.BMS.dto.LoginDto;
import com.example.BMS.dto.RegisterDto;
import com.example.BMS.service.AuthService;
import com.example.BMS.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
    authService.register(registerDto);
    return ResponseEntity.ok("User registered successfully!");
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> loginUser(@RequestBody LoginDto loginDto) {
    try {
      // Authenticate using the user's EMAIL and password
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

      // If successful, generate a token using the EMAIL
      String token = jwtService.generateToken(loginDto.getEmail());
      return ResponseEntity.ok(new AuthResponseDto(token));

    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
  }
}