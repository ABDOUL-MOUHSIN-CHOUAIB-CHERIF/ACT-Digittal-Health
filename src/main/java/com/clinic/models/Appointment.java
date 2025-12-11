package com.clinic.models;

import java.time.LocalDateTime;

public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    private LocalDateTime appointmentDate;
    private String status; // "Scheduled", "Completed", "Cancelled", "No-Show"
    private String notes;
    private LocalDateTime createdAt;

    // Constructors
    public Appointment() {
        this.status = "Scheduled";
        this.createdAt = LocalDateTime.now();
    }

    // Constructor for new appointments
    public Appointment(int patientId, int doctorId, LocalDateTime appointmentDate, String notes) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.status = "Scheduled";
        this.notes = notes;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Utility Methods
    public boolean isScheduled() {
        return "Scheduled".equalsIgnoreCase(status);
    }

    public boolean isCompleted() {
        return "Completed".equalsIgnoreCase(status);
    }

    public boolean isCancelled() {
        return "Cancelled".equalsIgnoreCase(status);
    }

    public boolean isNoShow() {
        return "No-Show".equalsIgnoreCase(status);
    }

    public void markAsCompleted() {
        this.status = "Completed";
    }

    public void markAsCancelled() {
        this.status = "Cancelled";
    }

    public void markAsNoShow() {
        this.status = "No-Show";
    }

    public boolean isUpcoming() {
        return isScheduled() && appointmentDate.isAfter(LocalDateTime.now());
    }

    public boolean isPast() {
        return appointmentDate.isBefore(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", date=" + appointmentDate +
                ", status='" + status + '\'' +
                '}';
    }
}