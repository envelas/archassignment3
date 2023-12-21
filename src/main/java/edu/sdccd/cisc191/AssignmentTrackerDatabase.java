package edu.sdccd.cisc191;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AssignmentTrackerDatabase implements Serializable {
    transient private ObservableList<Assignment> assignments = FXCollections.observableArrayList();
    private static final long serialVersionUID = 1L;

    void setAssignments(ObservableList<Assignment> assignments) {
        this.assignments = assignments;
    }

    void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    void deleteAssignment(Assignment assignment) {
        assignments.remove(assignment);
    }

    ObservableList<Assignment> getAssignments() {
        return assignments;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(new ArrayList<>(assignments));
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        assignments = FXCollections.observableArrayList((List<Assignment>) in.readObject());

    }
}
