package com.clinic.controllers;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import  com.clinic.utils.AlertUtils;
import com.clinic.models.Patient;
import com.clinic.utils.ValidationUtil;
import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class receptionistController {

    @FXML public TextField nameField;
    @FXML public TextField  DOBField;
    @FXML public TextField genderField; // "Male" or "Female"
    @FXML public TextField phoneField;
    @FXML public TextField emailField;
    @FXML public TextField addressField;
    @FXML public TextField emergencyContactField;
    @FXML private TableView<Patient> patientTable;

    // Add these TableColumn fields that match your FXML columns
    @FXML private TableColumn<Patient, String> name;
    @FXML private TableColumn<Patient, String> gender;
    @FXML private TableColumn<Patient, String> phone;
    @FXML private TableColumn<Patient, String> DOB;
    @FXML private TableColumn<Patient, String> Address;
    @FXML private TableColumn<Patient, String> Email;
    @FXML private TableColumn<Patient, String> Emergency_Contact;


    @FXML
    public void initialize() {
        // Link each column to Patient object properties
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        DOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        Address.setCellValueFactory(new PropertyValueFactory<>("address"));
        Emergency_Contact.setCellValueFactory(new PropertyValueFactory<>("emergency"));

        // Load initial data
        loadPatientData();
    }
    @FXML
    private void handleregistretion(){
//             1. get values from all the fields
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String gender = genderField.getText().trim();
        String dob = DOBField.getText().trim();
        String address = addressField.getText().trim();
        String Emergency = emergencyContactField.getText().trim();

//        2. validate the input
        // 3. Validate all fields
        Map<String, String> errors = new HashMap<>();

        // Validate login username
        String usernameError = ValidationUtil.validateUsername(name);
        if (!usernameError.isEmpty()) errors.put("Username", usernameError);

        // Validate email
        String emailError = ValidationUtil.validateEmail(email);
        if (!emailError.isEmpty()) errors.put("Email", emailError);

        // Validate phone
        String phoneError = ValidationUtil.validatePhone(phone);
        if (!phoneError.isEmpty()) errors.put("Phone", phoneError);

//        3. create patient object
        Patient patient = new Patient();
        patient.setName(name);
        patient.setEmail(email);
        patient.setAddress(address);
        patient.setGender(gender);
        patient.setPhone(phone);
        patient.setEmergencyContact(Emergency);
        patient.setDateOfBirth(LocalDate.parse(dob));

//        4. handle the sending to mysql
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = DBConnection.getConnection();
            String sql = " INSERT INTO patients (name, date_of_birth, gender, phone, email, address, emergency_contact)  VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);

            ps.setString(1, patient.getFullName());
            ps.setString(2, patient.getDateOfBirth().toString());
            ps.setString(3, patient.getGender());
            ps.setString(4, patient.getPhone());
            ps.setString(5, patient.getEmail());
            ps.setString(6, patient.getAddress());
            ps.setString(7, patient.getEmergencyContact());

            ps.executeUpdate();
            ps.close();
            conn.close();

            AlertUtils.showSuccess("Patient registered successfully!!!");
        } catch (Exception e) {
                e.printStackTrace();
            AlertUtils.showError("Registration failed: " + e.getMessage());
        }
    }

//    Handle the retrieving of fields form the database
public List<Patient> getAllPatients() {
    List<Patient> list = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = DBConnection.getConnection();
        // CORRECT SQL - Use actual table name and column names
        String sql = "SELECT * FROM patients";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            // Create Patient object with correct constructor or setters
            Patient p = new Patient();
            p.setName(rs.getString("name"));
            p.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate()); // Convert SQL Date to LocalDate
            p.setGender(rs.getString("gender"));
            p.setPhone(rs.getString("phone"));
            p.setEmail(rs.getString("email"));
            p.setAddress(rs.getString("address"));
            p.setEmergencyContact(rs.getString("emergency_contact"));
            p.setPatientId(rs.getString("patient_id")); // If you have this column

            list.add(p);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // Close resources in finally block
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }

    return list;
}

//    getter and setter for the table patient
    public TableView<Patient> getPatientTable() {
        return patientTable;
    }
    public void setPatientTable(TableView<Patient> patientTable) {
        this.patientTable = patientTable;
    }



//    load the PATIENT DATA FROM THE DATABASE
    private void loadPatientData() {
        // Use your existing getAllPatients() method
        List<Patient> patients = getAllPatients();

        // Convert List to ObservableList for TableView
        ObservableList<Patient> patientObservableList = FXCollections.observableArrayList(patients);

        // Set the data to table
        patientTable.setItems(patientObservableList);
    }
//use to refresh the table to see the newly added patient
    @FXML
    private void handleRefresh() {
        loadPatientData();  // Just call this method
        System.out.println("Table refreshed with " + patientTable.getItems().size() + " patients");
    }
}
