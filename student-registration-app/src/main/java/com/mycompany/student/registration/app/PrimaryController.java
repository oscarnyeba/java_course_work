package com.mycompany.student.registration.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

/**
 * Controller for primary.fxml. Manages form logic, validation, UI updates, and CSV persistence.
 */
public class PrimaryController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField confirmEmailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<Integer> yearCombo;
    @FXML private ComboBox<Integer> monthCombo;
    @FXML private ComboBox<Integer> dayCombo;
    @FXML private RadioButton maleRadio;
    @FXML private RadioButton femaleRadio;
    @FXML private RadioButton civilRadio;
    @FXML private RadioButton cseRadio;
    @FXML private RadioButton electricalRadio;
    @FXML private RadioButton ecRadio;
    @FXML private RadioButton mechanicalRadio;
    @FXML private TextArea dataTextArea;
    @FXML private Button submitButton;
    @FXML private Button cancelButton;

    @FXML private Label firstNameError;
    @FXML private Label lastNameError;
    @FXML private Label emailError;
    @FXML private Label confirmEmailError;
    @FXML private Label passwordError;
    @FXML private Label confirmPasswordError;
    @FXML private Label dobError;
    @FXML private Label genderError;
    @FXML private Label departmentError;

    private ToggleGroup genderGroup;
    private ToggleGroup deptGroup;
    private static final String CSV_FILE = "students.csv";
    private static final int CURRENT_YEAR = 2026; // From system time
    private Map<RadioButton, String> deptAbbrevs;

    @FXML
    private void initialize() {
        // DOB combos
        List<Integer> years = new ArrayList<>();
        // for (int y = CURRENT_YEAR - 60; y <= CURRENT_YEAR - 16; y++) years.add(y); // Age 16-60
        // Revered the loop to start with latest years
        for (int y = CURRENT_YEAR - 16; y >= CURRENT_YEAR - 60; y--) years.add(y); // Age 16-60

        yearCombo.setItems(FXCollections.observableList(years));

        List<Integer> months = new ArrayList<>();
        for (int m = 1; m <= 12; m++) months.add(m);
        monthCombo.setItems(FXCollections.observableList(months));

        // Dynamic days
        yearCombo.valueProperty().addListener((obs, old, newVal) -> updateDays());
        monthCombo.valueProperty().addListener((obs, old, newVal) -> updateDays());

        // Gender group
        genderGroup = new ToggleGroup();
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);

        // Dept group and abbrevs
        deptGroup = new ToggleGroup();
        civilRadio.setToggleGroup(deptGroup);
        cseRadio.setToggleGroup(deptGroup);
        electricalRadio.setToggleGroup(deptGroup);
        ecRadio.setToggleGroup(deptGroup);
        mechanicalRadio.setToggleGroup(deptGroup);

        deptAbbrevs = new HashMap<>();
        deptAbbrevs.put(civilRadio, "Civil");
        deptAbbrevs.put(cseRadio, "CSE");
        deptAbbrevs.put(electricalRadio, "Electrical");
        deptAbbrevs.put(ecRadio, "E&C");
        deptAbbrevs.put(mechanicalRadio, "Mechanical");

        // Load CSV data
        loadFromCSV();

        // Hide errors
        hideAllErrors();
    }

    private void updateDays() {
        Integer year = yearCombo.getValue();
        Integer month = monthCombo.getValue();
        if (year == null || month == null) return;
        YearMonth ym = YearMonth.of(year, month);
        List<Integer> days = new ArrayList<>();
        for (int d = 1; d <= ym.lengthOfMonth(); d++) days.add(d);
        dayCombo.setItems(FXCollections.observableList(days));
    }

    @FXML
    private void handleSubmit() {
        hideAllErrors();
        List<String> errors = new ArrayList<>();

        String firstName = firstNameField.getText().trim();
        if (firstName.isEmpty()) {
            showError(firstNameError, "Required");
            errors.add("First name required");
        }

        String lastName = lastNameField.getText().trim();
        if (lastName.isEmpty()) {
            showError(lastNameError, "Required");
            errors.add("Last name required");
        }

        String email = emailField.getText().trim();
        String confirmEmail = confirmEmailField.getText().trim();
        if (email.isEmpty()) {
            showError(emailError, "Required");
            errors.add("Email required");
        } else if (!email.equals(confirmEmail)) {
            showError(confirmEmailError, "Mismatch");
            errors.add("Email mismatch");
        }

        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (password.length() < 8 || password.length() > 20) {
            showError(passwordError, "8-20 chars");
            errors.add("Password length invalid");
        } else if (!password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
            showError(passwordError, "Letter + digit");
            errors.add("Password must have letter and digit");
        } else if (!password.equals(confirmPassword)) {
            showError(confirmPasswordError, "Mismatch");
            errors.add("Password mismatch");
        }

        Integer year = yearCombo.getValue();
        Integer month = monthCombo.getValue();
        Integer day = dayCombo.getValue();
        LocalDate dob = null;
        if (year == null || month == null || day == null) {
            showError(dobError, "Complete");
            errors.add("Incomplete DOB");
        } else {
            dob = LocalDate.of(year, month, day);
            int age = CURRENT_YEAR - year;
            if (age < 16 || age > 60) {
                showError(dobError, "Age 16-60");
                errors.add("Age out of range");
            }
        }

        String gender = maleRadio.isSelected() ? "M" : femaleRadio.isSelected() ? "F" : null;
        if (gender == null) {
            showError(genderError, "Select");
            errors.add("Gender required");
        }

        RadioButton selectedDept = (RadioButton) deptGroup.getSelectedToggle();
        String dept = selectedDept != null ? deptAbbrevs.get(selectedDept) : null;
        if (dept == null) {
            showError(departmentError, "Select");
            errors.add("Department required");
        }

        if (!errors.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errors");
            alert.setHeaderText("Fix these:");
            alert.setContentText(String.join("\n", errors));
            alert.showAndWait();
            return;
        }

        // Generate ID
        String id = generateNextId();

        // Format and append
        String output = String.format("ID: %s [%s %s / %s %s] %s %s", id, lastName, firstName, gender, dept, dob, email);
        dataTextArea.appendText(output + "\n");

        // Save to CSV
        saveToCSV(id, lastName, firstName, gender, dept, dob.toString(), email);

        // Clear
        handleCancel();
    }

    @FXML
    private void handleCancel() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        confirmEmailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        yearCombo.setValue(null);
        monthCombo.setValue(null);
        dayCombo.setValue(null);
        genderGroup.selectToggle(null);
        deptGroup.selectToggle(null);
        hideAllErrors();
    }

    private void hideAllErrors() {
        Label[] errorLabels = {firstNameError, lastNameError, emailError, confirmEmailError, passwordError, confirmPasswordError, dobError, genderError, departmentError};
        for (Label label : errorLabels) {
            label.setVisible(false);
        }
    }

    private void showError(Label label, String msg) {
        label.setText(msg);
        label.setTextFill(Color.RED);
        label.setVisible(true);
    }

    private String generateNextId() {
        int maxSeq = 0;
        if (Files.exists(Paths.get(CSV_FILE))) {
            try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
                br.readLine(); // Header
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 0) {
                        String[] idParts = parts[0].split("-");
                        if (idParts.length == 2 && Integer.parseInt(idParts[0]) == CURRENT_YEAR) {
                            int seq = Integer.parseInt(idParts[1]);
                            if (seq > maxSeq) maxSeq = seq;
                        }
                    }
                }
            } catch (IOException | NumberFormatException e) {
                // Silent, start from 0
            }
        }
        return String.format("%d-%05d", CURRENT_YEAR, maxSeq + 1);
    }

    private void saveToCSV(String id, String last, String first, String gender, String dept, String dob, String email) {
        boolean exists = Files.exists(Paths.get(CSV_FILE));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            if (!exists) {
                bw.write("ID,LastName,FirstName,Gender,Department,DOB,Email\n");
            }
            bw.write(String.format("%s,%s,%s,%s,%s,%s,%s\n", id, last, first, gender, dept, dob, email));
        } catch (IOException e) {
            // Handle if critical
        }
    }

    private void loadFromCSV() {
        if (!Files.exists(Paths.get(CSV_FILE))) return;
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            br.readLine(); // Header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    String output = String.format("ID: %s [%s %s / %s %s] %s %s", parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                    dataTextArea.appendText(output + "\n");
                }
            }
        } catch (IOException e) {
            // Silent
        }
    }
}