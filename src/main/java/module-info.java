module com.clinic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.clinic to javafx.fxml;
    opens com.clinic.controllers to javafx.fxml;

    exports com.clinic;
    exports com.clinic.controllers;
}