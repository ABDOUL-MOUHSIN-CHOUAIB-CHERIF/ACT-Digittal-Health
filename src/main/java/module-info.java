module com.clinic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    requires jbcrypt;


    opens com.clinic to javafx.fxml;
    opens com.clinic.controllers to javafx.fxml;

    exports com.clinic;
    exports com.clinic.controllers;
    exports com.clinic.utils;
    opens com.clinic.utils to javafx.fxml;
    exports com.clinic.models;
    opens com.clinic.models to javafx.fxml;
}