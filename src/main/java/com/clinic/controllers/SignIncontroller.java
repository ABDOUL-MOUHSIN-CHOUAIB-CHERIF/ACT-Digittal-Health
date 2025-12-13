package com.clinic.controllers;

import com.clinic.models.User;
import com.clinic.utils.setNavigator;
import com.clinic.utils.ValidationUtil;
import com.clinic.utils.PasswordUtil;
import com.clinic.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import java.util.HashMap;
import java.util.Map;
import database.DBConnection;
import java.sql.*;

public class SignIncontroller {

    // FXML Fields - adjust these based on your actual FXML
    @FXML public TextField usernameField;
    @FXML public TextField emailField;
    @FXML public TextField phoneField;
    @FXML public PasswordField passwordField;
    @FXML public PasswordField confirmpasswordField;
    @FXML public TextField departmentField;
    @FXML private ComboBox<String> roleComboBox;

    @FXML
    public void initialize() {
        // Set up role dropdown with options
        roleComboBox.getItems().addAll("DOCTOR", "RECEPTIONIST", "PHARMACIST", "ADMIN");
        roleComboBox.setPromptText("Select your role");
    }
    @FXML
    private void handleRegister() {
        // 1. Get values from ALL form fields
        String fullName = usernameField.getText().trim();      // Full name from username field
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmpasswordField.getText();
        String role = roleComboBox.getValue(); // Changed from RoleField to roleComboBox
        String department = departmentField.getText().trim();

        // 3. Create login username - FIRST NAME ONLY
        String loginUsername;
        if (fullName.contains(" ")) {
            // Get first name only: "John Doe" → "john"
            loginUsername = fullName.split(" ")[0].toLowerCase();
        } else {
            // If only one name, use it
            loginUsername = fullName.toLowerCase();
        }

        // 3. Validate all fields
        Map<String, String> errors = new HashMap<>();

        // Validate login username
        String usernameError = ValidationUtil.validateUsername(fullName);
        if (!usernameError.isEmpty()) errors.put("Username", usernameError);

        // Validate email
        String emailError = ValidationUtil.validateEmail(email);
        if (!emailError.isEmpty()) errors.put("Email", emailError);

        // Validate phone
        String phoneError = ValidationUtil.validatePhone(phone);
        if (!phoneError.isEmpty()) errors.put("Phone", phoneError);

        // Validate password
        String passwordError = ValidationUtil.validatePassword(password, confirmPassword);
        if (!passwordError.isEmpty()) errors.put("Password", passwordError);

        // Validate role
        if (role == null || role.isEmpty()) {
            AlertUtils.showError("Please select a role from the dropdown");
            roleComboBox.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            return;
        }
        // Validate full name (simplified - no splitting)
        if (fullName.isEmpty()) {
            errors.put("Full Name", "Full name is required");
        }

        // Validate department
        if (department.isEmpty()) {
            errors.put("Department", "Department is required");
        }

        // 4. Check for validation errors
        if (!errors.isEmpty()) {
            showErrors(errors);
            return;
        }
        // 4. check oif the two password are the same
        if (!password.equals(confirmPassword)) {
            AlertUtils.showError("Passwords don't match!\nPlease enter the same password in both fields.");
            return;
        }

        // 5. Hash the password - IMPORTANT: CHECK IF PASSWORD IS NOT EMPTY
       String hashedPassword = PasswordUtil.hashPassword(password);

        // 6. Create User object
        User user = new User();
        user.setUsername(fullName);        // For login: "john doe" (from full name)
        user.setPasswordHash(hashedPassword);
        user.setRole(role.toUpperCase());
        user.setEmail(email);
        user.setPhone(phone);
        user.setDepartment(department);
        user.setActive(true);  // Assuming new users are active by default

        // 7. handle the sending of data to mysql
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();

            // CORRECTED SQL - 7 columns, 7 placeholders
            String sql = "INSERT INTO users (username, password_hash, role, email, phone, department, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);

            // CORRECTED parameter indexes (1-7)
            ps.setString(1, user.getUsername());           // username
            ps.setString(2, user.getPasswordHash());       // password_hash
            ps.setString(3, user.getRole());               // role
            ps.setString(4, user.getEmail());              // email
            ps.setString(5, user.getPhone());              // phone
            ps.setString(6, user.getDepartment());         // department
            ps.setInt(7, user.isActive() ? 1 : 0);         // is_active

            ps.executeUpdate();
            ps.close();
            conn.close();
            System.out.println("User added successfully!");

            AlertUtils.showSuccess("Registration successful! You can now login.");
            setNavigator.goToPage("/FXML/dashboard.fxml", "Dashboard");
            clearForm();

        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.showError("Registration failed: " + e.getMessage());
        }
    }

    @FXML
    private void goToLogin() {
        setNavigator.goToPage("/FXML/login.fxml", "Log In");
    }

    private void showErrors(Map<String, String> errors) {
        StringBuilder errorMsg = new StringBuilder("Please fix the following errors:\n\n");
        for (Map.Entry<String, String> error : errors.entrySet()) {
            errorMsg.append("• ").append(error.getKey()).append(": ").append(error.getValue()).append("\n");
        }
        AlertUtils.showError(errorMsg.toString());
    }

    private void clearForm() {
        usernameField.clear();
        emailField.clear();
        phoneField.clear();
        passwordField.clear();
        confirmpasswordField.clear();
        roleComboBox.setValue(null);
        departmentField.clear();
    }
}