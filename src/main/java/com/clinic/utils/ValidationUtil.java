package com.clinic.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationUtil {

    // FIXED: Changed return type and parameters
    public static Map<String, String> validateUser(String email, String phone,
                                                   String password, String confirmPassword, String role,
                                                   String username, String firstName, String lastName, String department) {

        Map<String, String> errors = new HashMap<>();

        // Call each validation method
        String emailError = validateEmail(email);
        if (!emailError.isEmpty()) errors.put("Email", emailError);

        String phoneError = validatePhone(phone);
        if (!phoneError.isEmpty()) errors.put("Phone", phoneError);

        String passwordError = validatePassword(password, confirmPassword);
        if (!passwordError.isEmpty()) errors.put("Password", passwordError);

        String roleError = validateRole(role);
        if (!roleError.isEmpty()) errors.put("Role", roleError);

        String usernameError = validateUsername(username);
        if (!usernameError.isEmpty()) errors.put("Username", usernameError);

        // Simple validations for other fields
        if (firstName == null || firstName.trim().isEmpty())
            errors.put("First Name", "First name is required");

        if (lastName == null || lastName.trim().isEmpty())
            errors.put("Last Name", "Last name is required");

        if (department == null || department.trim().isEmpty())
            errors.put("Department", "Department is required");

        return errors;
    }

    // Email validation logic
    public static String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email is required";
        }
        String trimmedEmail = email.trim();

        if (!trimmedEmail.contains("@")) {
            return "Email must contain @";
        }
        if (!trimmedEmail.contains(".")) {
            return "Email must contain .";
        }
        if (trimmedEmail.charAt(0) == '@') {
            return "Email cannot start with @";
        }
        if (trimmedEmail.endsWith("@")) {
            return "Email cannot end with @";
        }
        if (trimmedEmail.length() > 100) {
            return "Email cannot exceed 100 characters";
        }
        // Basic format: something@something.something
        String[] parts = trimmedEmail.split("@");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            return "Invalid email format";
        }
        if (!parts[1].contains(".")) {
            return "Email must have domain with . (example: @gmail.com)";
        }
        return ""; // Empty string means valid
    }

    public static String validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return "Phone number is required";
        }

        String cleanedPhone = phone.trim()
                .replace(" ", "")
                .replace("-", "")
                .replace("(", "")
                .replace(")", "");

        // Cameroon formats: +237XXXXXXXXX or 6XXXXXXXXX or 237XXXXXXXXX
        if (cleanedPhone.startsWith("+237")) {
            // International format: +237 followed by 9 digits
            String digits = cleanedPhone.substring(4);
            if (digits.length() != 9 || !digits.matches("\\d+")) {
                return "Invalid Cameroon phone format. Use: +237 XXX XXX XXX";
            }
            if (!digits.startsWith("6") && !digits.startsWith("2")) {
                return "Cameroon mobile numbers start with 6 or 2";
            }

        } else if (cleanedPhone.startsWith("237")) {
            // Local international format without +
            String digits = cleanedPhone.substring(3);
            if (digits.length() != 9 || !digits.matches("\\d+")) {
                return "Invalid Cameroon phone format. Use: 237 XXX XXX XXX";
            }
            if (!digits.startsWith("6") && !digits.startsWith("2")) {
                return "Cameroon mobile numbers start with 6 or 2";
            }

        } else if (cleanedPhone.startsWith("6")) {
            // Local format: 6XXXXXXXXX
            if (cleanedPhone.length() != 9 || !cleanedPhone.matches("\\d+")) {
                return "Invalid local phone format. Use: 6XX XXX XXX";
            }

        } else {
            return "Invalid Cameroon phone number format";
        }

        if (cleanedPhone.length() > 15) {
            return "Phone number cannot exceed 15 characters";
        }

        return ""; // Valid
    }

    public static String validatePassword(String password, String confirmPassword) {
        if (password == null || password.trim().isEmpty()) {
            return "Password is required";
        }

        String trimmedPassword = password.trim();

        if (trimmedPassword.length() < 6) {
            return "Password must be at least 6 characters";
        }

        if (trimmedPassword.length() > 50) {
            return "Password cannot exceed 50 characters";
        }

        // Optional: Check for at least one number
        if (!trimmedPassword.matches(".*\\d.*")) {
            return "Password must contain at least one number";
        }

        // Optional: Check for at least one uppercase letter
        if (!trimmedPassword.matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter";
        }

        // Optional: Check for at least one lowercase letter
        if (!trimmedPassword.matches(".*[a-z].*")) {
            return "Password must contain at least one lowercase letter";
        }

        // Check password confirmation
        if (confirmPassword != null && !trimmedPassword.equals(confirmPassword.trim())) {
            return "Passwords do not match";
        }

        return ""; // Valid
    }

    public static String validateRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            return "Role is required";
        }

        String upperRole = role.trim().toUpperCase();

        List<String> validRoles = Arrays.asList("DOCTOR", "PHARMACIST", "RECEPTIONIST", "ADMIN");

        if (!validRoles.contains(upperRole)) {
            return "Invalid role. Must be: DOCTOR, PHARMACIST, RECEPTIONIST, or ADMIN";
        }

        return ""; // Valid
    }

    public static String validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "Username is required";
        }

        String trimmedUsername = username.trim();

        if (trimmedUsername.length() < 3) {
            return "Username must be at least 3 characters";
        }

        if (trimmedUsername.length() > 50) {
            return "Username cannot exceed 50 characters";
        }

        // Only allow letters, numbers, underscores
        if (!trimmedUsername.matches("^[a-zA-Z0-9 _.-]+$")) {
            return "Username can only contain letters, numbers, and underscores";
        }

        return ""; // Valid
    }
}