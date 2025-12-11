package com.clinic;

import com.clinic.utils.setNavigator;
import database.DBConnection;
import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // ✅ TEST DATABASE CONNECTION FIRST
        testDatabaseConnection();

        // Set the primary stage for navigation
        setNavigator.setStage(primaryStage);

        // Set window properties
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(700);

        // Start with login page using SimpleNavigator
        setNavigator.goToPage("/FXML/SignIn.fxml", "Sign in");
    }

    // ✅ CREATE A METHOD FOR DATABASE TESTING
    private void testDatabaseConnection() {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connected to MySQL successfully!");
            } else {
                System.out.println("❌ Connection failed!");
            }
        } catch (Exception e) {
            System.out.println("❌ Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}