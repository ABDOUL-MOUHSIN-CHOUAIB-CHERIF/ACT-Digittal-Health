package com.clinic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class setNavigator {
    private static Stage mainStage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }
    public static void goToPage(String fxmlFile, String title) {
        try {
            Parent root = FXMLLoader.load(setNavigator.class.getResource(fxmlFile));
            Scene scene = new Scene(root, 1000, 700);

            // CSS will be loaded from the FXML file itself
            mainStage.setScene(scene);
            mainStage.setTitle("ACT Digital Health - " + title);
            mainStage.show();

        } catch (Exception e) {
            System.out.println("Error loading: " + fxmlFile);
            e.printStackTrace();
        }
    }
}