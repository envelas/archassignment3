package edu.sdccd.cisc191;

import java.io.Serializable;
import java.util.List;

public class SavedAssignments implements Serializable {
    //This is the "common" class amongst my server and client
    private List<Assignment> assignments;

    public SavedAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }
}
