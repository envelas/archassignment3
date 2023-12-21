package edu.sdccd.cisc191;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.Serializable;

public class StartApp extends Application implements Serializable {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AssignmentsTrackerApp assignmentsTrackerApp = new AssignmentsTrackerApp();
        assignmentsTrackerApp.start(primaryStage);
    }
}
