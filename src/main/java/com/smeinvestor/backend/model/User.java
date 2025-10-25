package com.smeinvestor.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.LocalDateTime;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String email;
    
    private String password;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Role {
        SME, INVESTOR
    }

    // Constructors
    public User() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(String email, String password, Role role) {
        this();
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}