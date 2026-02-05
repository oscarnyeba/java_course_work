module com.mycompany.student.registration.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.student.registration.app to javafx.fxml;
    exports com.mycompany.student.registration.app;
}