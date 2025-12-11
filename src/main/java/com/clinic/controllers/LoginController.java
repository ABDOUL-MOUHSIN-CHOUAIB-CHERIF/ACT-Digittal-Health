package com.clinic.controllers;

import com.clinic.utils.setNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
//        if (loginSuccessful) {
            setNavigator.goToPage("/FXML/dashboard.fxml", "Dashboard");
//        }
    }
    @FXML
    private void handleSignIn() {
        setNavigator.goToPage("/FXML/SignIn.fxml", "Sign In");
//
    }

}