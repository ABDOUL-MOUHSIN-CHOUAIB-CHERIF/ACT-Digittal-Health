package com.clinic.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // hashing plain password
    public static String hashPassword(String plainPassword) {
        // Check for null or empty password
        if (plainPassword == null) {
            System.out.println("ERROR: Password is null in hashPassword()");
            throw new IllegalArgumentException("Password cannot be null");
        }

        if (plainPassword.trim().isEmpty()) {
            System.out.println("ERROR: Password is empty in hashPassword()");
            throw new IllegalArgumentException("Password cannot be empty");
        }

        try {
            // hash the password using BCrypt.hashpw
            System.out.println("Hashing password with BCrypt...");
            String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));

            // Verify hash was created
            if (hashed == null || hashed.isEmpty()) {
                System.out.println("ERROR: BCrypt returned null or empty hash!");
                throw new RuntimeException("Password hashing failed");
            }

            System.out.println("Password hashed successfully (length: " + hashed.length() + ")");
            return hashed;

        } catch (Exception e) {
            System.out.println("ERROR in hashPassword: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    // verify the password
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            System.out.println("ERROR: Cannot verify null password or hash");
            return false;
        }

        try {
            // verify using BCrypt.checkpw
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            System.out.println("ERROR in verifyPassword: " + e.getMessage());
            return false;
        }
    }
}
