package com.clinic.models;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String username;
    private String password_hash;
    private String role;
    private String email;
    private String phone;
    private String department;
    private boolean isActive;
    private LocalDateTime createdAt;

    // Constructors
    public User() {
        // Default constructor
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

    // Constructor for creating new users (without id)
    public User(String username, String password_hash, String role,
                String firstName, String lastName, String email,
                String phone, String department) {
        this.username = username;
        this.password_hash = password_hash;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

    // Constructor with all fields (for loading from database)
    public User(int id, String username, String passwordHash, String role,
                String firstName, String lastName, String email,
                String phone, String department, boolean isActive,
                LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {

        return this.password_hash;
    }
    public void setPasswordHash(String passwordHash) {
        this.password_hash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Utility Methods
    public boolean hasRole(String roleToCheck) {
        return this.role != null && this.role.equalsIgnoreCase(roleToCheck);
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }

    public boolean isDoctor() {
        return "DOCTOR".equalsIgnoreCase(role);
    }

    public boolean isPharmacist() {
        return "PHARMACIST".equalsIgnoreCase(role);
    }

    public boolean isReceptionist() {
        return "RECEPTIONIST".equalsIgnoreCase(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}