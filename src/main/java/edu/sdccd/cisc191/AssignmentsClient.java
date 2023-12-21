package edu.sdccd.cisc191;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AssignmentsClient {
    public static void main(String[] args) {
        //Setting my server address, in this case I will just be using local
        String serverAddress = "localhost";
        //Setting my port number
        int portNumber = 12345;

        try (Socket socket = new Socket(serverAddress, portNumber);
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Reading the saved data being sent from the server
            SavedAssignments data = (SavedAssignments) in.readObject();

            // Accessing the saved data through the common SavedAssignments class
            List<Assignment> assignments = data.getAssignments();

            // Save the assignments locally so they can be booted on the app
            saveAssignments(assignments, "assignments.dat");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void saveAssignments(List<Assignment> assignments, String filename) {
        // Defining the method for writing the data to the local disk
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filename)))) {
            oos.writeObject(assignments);
            System.out.println("Assignments saved");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save assignments", e);
        }
    }
}
