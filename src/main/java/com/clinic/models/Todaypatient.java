package com.clinic.models;

import java.time.LocalTime;

public class Todaypatient {

    private int patientId;
    private String name;
    private LocalTime registrationTime;
    private String status; // Add status field

    // Constructor
    public Todaypatient(int patientId, String name, LocalTime registrationTime) {
        this.patientId = patientId;
        this.name = name;
        this.registrationTime = registrationTime;
        this.status = ""; // default empty
    }

    // ===== Getters & Setters =====
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
