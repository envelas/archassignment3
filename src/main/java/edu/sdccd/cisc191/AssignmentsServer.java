package edu.sdccd.cisc191;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssignmentsServer {
    public static void main(String[] args) {
        //Setting my port number
        int portNumber = 12345;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

            //Defining a thread pool To handle multiple clients concurrently
            ExecutorService executorService = Executors.newFixedThreadPool(5);

            while (true) {
                //Listening for the connection from the client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection successful" + clientSocket.getInetAddress());

                // Retrieve and load the saved assignments
                List<Assignment> assignments = loadAssignments();


                // Pass the retrieved data to the client at the specified thread
                executorService.submit(() -> sendAssignmentsData(clientSocket, assignments));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Assignment> loadAssignments() {
        //Defining the method for retrieving and loading the data
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get("tasks.dat")))) {
            AssignmentsTrackerApp loadedApp = (AssignmentsTrackerApp) ois.readObject();
            return loadedApp.getAssignments();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load saved assignments", e);
        }
    }

    private static void sendAssignmentsData(Socket clientSocket, List<Assignment> assignments) {
        //Defining the method for passing the data on to the client
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
            SavedAssignments data = new SavedAssignments(assignments);

            out.writeObject(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
