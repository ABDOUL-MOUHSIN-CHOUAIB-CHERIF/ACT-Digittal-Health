package com.clinic.controllers;

import com.clinic.models.Todaypatient;
import database.DBConnection;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.time.LocalTime;

public class DoctorController {

    // ===== TABLE VIEW =====
    @FXML private TableView<Todaypatient> todaysPatientsTable;
    @FXML private TableColumn<Todaypatient, String> patientNameCol;
    @FXML private TableColumn<Todaypatient, LocalTime> registrationTimeCol;
    @FXML private TableColumn<Todaypatient, String> statusCol;
    @FXML private TableColumn<Todaypatient, Void> actionCol; // Action column for Consult button

    // ===== CONSULTATION FORM =====
    @FXML
    private ScrollPane consultationScroll;

    @FXML
    private VBox consultationForm;

    @FXML private TextField medicationField;
    @FXML private TextField illnessField;
    @FXML private TextArea symptomsField;
    @FXML private TextArea treatmentField;

    @FXML private RadioButton statusUnderTreatment;
    @FXML private RadioButton statusRecovered;
    @FXML private ToggleGroup statusGroup;

    @FXML private Label selectedPatientName;
    @FXML private Label selectedPatientTime;

    private Todaypatient selectedPatient;

    @FXML
    public void initialize() {
        // Hide consultation form initially
        consultationScroll.setVisible(false);

        // Setup table columns
        patientNameCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getName())
        );
        registrationTimeCol.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getRegistrationTime())
        );
        statusCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStatus() != null ? data.getValue().getStatus() : "")
        );

        // Setup Action column with button
        setupActionColumn();

        // Load today's patients
        loadTodayPatients();

        // Handle row selection as alternative to Action button
        handlePatientSelection();
    }

    private void loadTodayPatients() {
        ObservableList<Todaypatient> list = FXCollections.observableArrayList();

        String sql = """
            SELECT patient_id, name, registration_time
            FROM patients
            WHERE DATE(registration_time) = CURRENT_DATE
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Todaypatient(
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getTime("registration_time").toLocalTime()
                ));
            }

            todaysPatientsTable.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handlePatientSelection() {
        todaysPatientsTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        openConsultationForm(newVal);
                    }
                });
    }

    private void setupActionColumn() {
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Consult");

            {
                btn.setStyle("-fx-background-color: #1a73e8; -fx-text-fill: white;");
                btn.setOnAction(e -> {
                    Todaypatient patient = getTableView().getItems().get(getIndex());
                    openConsultationForm(patient);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    private void openConsultationForm(Todaypatient patient) {
        selectedPatient = patient;
        consultationScroll.setVisible(true);
        clearForm();
        selectedPatientName.setText("Patient: " + patient.getName());
        selectedPatientTime.setText("Registered: " + patient.getRegistrationTime());
    }

    @FXML
    private void clearForm() {
        illnessField.clear();
        symptomsField.clear();
        treatmentField.clear();
        medicationField.clear();
        statusGroup.selectToggle(null);
    }

    @FXML
    private void saveConsultation() {
        if (selectedPatient == null) return;

        RadioButton selectedStatus = (RadioButton) statusGroup.getSelectedToggle();
        if (selectedStatus == null) return;

        String sql = """
            INSERT INTO consultations
            (patient_id, illness, symptoms, treatment, medication, status, consultation_date)
            VALUES (?, ?, ?, ?, ?, ?, CURRENT_DATE)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, selectedPatient.getPatientId());
            ps.setString(2, illnessField.getText());
            ps.setString(3, symptomsField.getText());
            ps.setString(4, treatmentField.getText());
            ps.setString(5, medicationField.getText());
            ps.setString(6, selectedStatus.getText());

            ps.executeUpdate();

            // Optionally update the status column in table
            selectedPatient.setStatus(selectedStatus.getText());
            todaysPatientsTable.refresh();

            // Hide form and clear selection
            consultationScroll.setVisible(false);
            selectedPatientName.setText("Patient: ");
            selectedPatientTime.setText("Registered: ");
            todaysPatientsTable.getSelectionModel().clearSelection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
