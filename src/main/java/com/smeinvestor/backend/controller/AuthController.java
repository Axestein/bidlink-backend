package com.smeinvestor.backend.controller;

import com.smeinvestor.backend.model.User;
import com.smeinvestor.backend.service.JwtService;
import com.smeinvestor.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup/sme")
    public ResponseEntity<?> signupSme(@RequestBody SignupRequest request) {
        try {
            User user = userService.registerUser(request.getEmail(), request.getPassword(), User.Role.SME);
            return ResponseEntity.ok(Map.of("message", "SME created successfully!"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/signup/investor")
    public ResponseEntity<?> signupInvestor(@RequestBody SignupRequest request) {
        try {
            User user = userService.registerUser(request.getEmail(), request.getPassword(), User.Role.INVESTOR);
            return ResponseEntity.ok(Map.of("message", "Investor created successfully!"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/login/sme")
    public ResponseEntity<?> loginSme(@RequestBody LoginRequest request) {
        return loginUser(request, User.Role.SME);
    }

    @PostMapping("/login/investor")
    public ResponseEntity<?> loginInvestor(@RequestBody LoginRequest request) {
        return loginUser(request, User.Role.INVESTOR);
    }

    private ResponseEntity<?> loginUser(LoginRequest request, User.Role expectedRole) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            
            User user = userService.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            if (user.getRole() != expectedRole) {
                return ResponseEntity.badRequest().body(Map.of("message", "Invalid role for this login endpoint"));
            }
            
            String token = jwtService.generateToken(
                    org.springframework.security.core.userdetails.User.builder()
                            .username(user.getEmail())
                            .password("")
                            .authorities(user.getRole().name())
                            .build()
            );
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid email or password"));
        }
    }

    // Request DTOs
    public static class SignupRequest {
        private String email;
        private String password;

        // Getters and Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginRequest {
        private String email;
        private String password;

        // Getters and Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}