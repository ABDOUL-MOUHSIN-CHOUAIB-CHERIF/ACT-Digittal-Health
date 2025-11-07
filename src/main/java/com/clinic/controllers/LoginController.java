package com.clinic.controllers;

import com.clinic.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    @FXML
    public void initialize() {
        // Set up focus and enter key handling
        setupEventHandlers();

        // For testing - remove in production
        usernameField.setText("admin");
        passwordField.setText("admin123");
    }

    private void setupEventHandlers() {
        // Enter key to login from password field
        passwordField.setOnAction(e -> handleLogin());

        // Enter key to move to password field from username
        usernameField.setOnAction(e -> passwordField.requestFocus());
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Please enter both username and password");
            return;
        }

        // Simple authentication for testing
        if ("admin".equals(username) && "admin123".equals(password)) {
            System.out.println("Login successful!");
            // Add navigation to dashboard here
        } else {
            System.out.println("Invalid credentials");
            passwordField.clear();
        }
    }
    @FXML
    private void goToDashboard() throws IOException {
        Main.changeScene("dashboard.fxml");
    }
}