package com.clinic.controllers;

import com.clinic.utils.setNavigator;
import database.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.*;
import com.clinic.models.User;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private ComboBox<String> roleComboBox;
  public void initialize(){

  }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String selectedRole = roleComboBox.getValue(); // Get selected role

        // Validate role is selected
        if (selectedRole == null || selectedRole.isEmpty()) {
            System.out.println("❌ Please select your role");
            return;
        }
        // Check if fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("❌ Please enter username and password");
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();

            // Fetch user with active status check
            String sql = "SELECT * FROM users WHERE username = ? AND is_active = 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Get stored hash
                String storedHash = rs.getString("password_hash");

                // Verify password using BCrypt
                if (BCrypt.checkpw(password, storedHash)) {
                    // Password matches - create user object
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setRole(rs.getString("role"));

                    System.out.println("✅ Login successful for: " + username);

                    // Navigate based on role
                    redirectBasedOnRole(user);

                } else {
                    System.out.println("❌ Wrong password for: " + username);
                }
            } else {
                System.out.println("❌ User not found or inactive: " + username);
            }

        } catch (Exception e) {
            System.out.println("❌ Authentication error: " + e.getMessage());
        } finally {
            // ALWAYS close resources
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    private void redirectBasedOnRole(User user) {
        String role = user.getRole().toUpperCase();

        switch(role) {
            case "DOCTOR":
                setNavigator.goToPage("/FXML/doctor_dashboard.fxml", "Doctor Dashboard");
                break;
            case "RECEPTIONIST":
                setNavigator.goToPage("/FXML/receptionist.fxml", "Reception Dashboard");
                break;
            case "PHARMACIST":
                setNavigator.goToPage("/FXML/pharmacy_dashboard.fxml", "Pharmacy Dashboard");
                break;
            case "ADMIN":
                setNavigator.goToPage("/FXML/dashboard.fxml", "Admin Dashboard");
                break;
            default:
                setNavigator.goToPage("/FXML/dashboard.fxml", "Dashboard");
                break;
        }
    }

    @FXML
    private void handleSignIn() {
        setNavigator.goToPage("/FXML/SignIn.fxml", "Sign In");
    }
}