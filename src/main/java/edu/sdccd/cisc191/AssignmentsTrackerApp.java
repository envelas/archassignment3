package edu.sdccd.cisc191;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.Serializable;

import java.io.*;
import java.util.Collections;


public class AssignmentsTrackerApp extends AssignmentTrackerDatabase implements Serializable{
    transient private ListView<Assignment> assignmentListView = new ListView<>();


    public AssignmentsTrackerApp() {
        assignmentListView.setItems(getAssignments());
    }
    private static final long serialVersionUID = 1L;


    public void start(Stage primaryStage) {
        Button addButton = new Button("Add Assignment");
        addButton.setOnAction(e -> showAddAssignmentDialog());

        Button sortButton = new Button("Sort Assignments");
        sortButton.setOnAction(e -> sortAssignments());

        Button deleteButton = new Button("Delete Assignment");
        deleteButton.setOnAction(e -> deleteSelectedAssignment());

        VBox vbox = new VBox(addButton, sortButton, deleteButton, assignmentListView);
        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Assignments Tracker");
        primaryStage.setOnCloseRequest(e -> saveData());
        loadData();
        assignmentListView.setItems(getAssignments());
        primaryStage.show();
    }

    private void showAddAssignmentDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Assignment");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter Assignment name:");

        dialog.showAndWait().ifPresent(name -> addAssignment(new Assignment(name,this)));
        updateListView();
    }

    private void sortAssignments() {
        Collections.sort(getAssignments());
        updateListView();
    }

    private void deleteSelectedAssignment() {
        Assignment selectedAssignment = assignmentListView.getSelectionModel().getSelectedItem();
        if (selectedAssignment != null) {
            deleteAssignment(selectedAssignment);
            updateListView();
        }
    }

    private void updateListView() {
        assignmentListView.setItems(getAssignments());
    }


    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("assignments.dat"))) {
            oos.writeObject(this);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("assignments.dat"))) {
            AssignmentsTrackerApp loadedApp = (AssignmentsTrackerApp) ois.readObject();
            ObservableList<Assignment> loadedAssignments = loadedApp.getAssignments();
            setAssignments(loadedAssignments);
        }
        catch (FileNotFoundException e) {
            saveData();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load saved data", e);
        }
    }
    }
