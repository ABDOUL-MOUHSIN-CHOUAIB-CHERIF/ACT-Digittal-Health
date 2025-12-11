package com.clinic.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient {
    private int id;
    private String patientId; // Custom ID like PAT-001
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender; // "Male" or "Female"
    private String phone;
    private String email;
    private String address;
    private String emergencyContact;
    private String medicalHistory;
    private LocalDateTime createdAt;

    // Constructors
    public Patient() {
        this.createdAt = LocalDateTime.now();
    }

    // Constructor for new patients (without database id)
    public Patient(String patientId, String firstName, String lastName,
                   LocalDate dateOfBirth, String gender, String phone,
                   String email, String address, String emergencyContact) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

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

    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Utility Methods
    public String getFullName() {
        return firstName + " " + lastName;
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
                "id=" + id +
                ", patientId='" + patientId + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + getAge() +
                '}';
    }
}