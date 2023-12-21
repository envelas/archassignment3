package edu.sdccd.cisc191;

import java.io.Serializable;
import java.util.Collections;

public class Assignment implements Comparable<Assignment>, Sortable, Serializable {
    private String name;

    private AssignmentTrackerDatabase assignmentTrackerDatabase;
    private static final long serialVersionUID = 1L;

    Assignment(String name, AssignmentTrackerDatabase assignmentTrackerDatabase) {
        this.name = name;
        this.assignmentTrackerDatabase = assignmentTrackerDatabase;
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Assignment otherAssignment) {
        return this.name.compareTo(otherAssignment.name);
    }

    @Override
    public void sort() {
        Collections.sort(assignmentTrackerDatabase.getAssignments());
    }
}
