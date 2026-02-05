package com.mycompany.student.registration.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main entry point for the application. Launches the JavaFX stage and loads the primary FXML.
 * 
 * @authors
 * OGWANG GIFT GIDEON   VU-BCS-2503-0706-EVE
 * SUUBI DEBORAH 	VU-DIT-2503-1213-EVE
 * NYEBA OSCAR MATHEW	VU-BCS-2503-1204-EVE 
 * NALWOGA MADRINE      VU-BIT-2503-2460-EVE 
 * NAMAGAMBE PRECIOUS   VU-BSC-2503-0355-EVE
 * KINTU BRIAN          VU-DIT-2503-0306-EVE
 * 
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/mycompany/student/registration/app/primary.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("New Student Registration Form");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}