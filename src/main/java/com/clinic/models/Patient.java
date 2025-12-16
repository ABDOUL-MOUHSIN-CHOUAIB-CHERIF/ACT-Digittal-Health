package com.clinic.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient {

    private String patientId; // Custom ID like PAT-001
    private String name;
    private LocalDate dateOfBirth;
    private String gender; // "Male" or "Female"
    private String phone;
    private String email;
    private String address;
    private String emergencyContact;
    private String status;
    private  String illness;
    private LocalDateTime createdAt;

    // Constructors
    public Patient() {
        this.createdAt = LocalDateTime.now();
    }

    // Constructor for new patients (without database id)
    public Patient(String patientId, String name, String status, String illness ,
                   LocalDate dateOfBirth, String gender, String phone,
                   String email, String address, String emergencyContact) {
        this.name = name;
        this.patientId = patientId;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.status = status;
        this.illness = illness;
        this.createdAt = LocalDateTime.now();
    }

    public Patient(String name, String dob, String gender, String phone, String email, String address, String emergency) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public String getStatus() { return status; }
    public void setFirstName(String status) { this.status = status; }

    public String getIllness() { return illness; }
    public void setLastName(String illness) { this.illness = illness; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getDob() {
        return dateOfBirth != null ? dateOfBirth.toString() : "";
    }

    public String getEmergency() {
        return emergencyContact;
    }
    // Utility Methods
    public String getFullName() {
        return name;
    }

    public int getAge() {
        if (dateOfBirth == null) return 0;
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    public boolean isMale() {
        return "Male".equalsIgnoreCase(gender);
    }

    public boolean isFemale() {
        return "Female".equalsIgnoreCase(gender);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name=" + name +
                ", patientId='" + patientId + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + getAge() +
                '}';
    }
}