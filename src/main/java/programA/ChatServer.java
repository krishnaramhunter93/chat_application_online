package programA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {
    // A map to store the userID and the corresponding output stream of the client
    private static Map<Integer, PrintWriter> clientWriters = new HashMap<>();
    private static int userID = 1;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Chat server started...");

            while (true) {
                // Accept client connection
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                // Store client output stream with the assigned user ID
                synchronized (clientWriters) {
                    clientWriters.put(userID, out);
                }

                // Start a new thread to handle each client
                new ClientHandler(clientSocket, userID++).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to send a message to a specific user or broadcast if recipientID is -1
    public static void sendMessage(String message, int recipientID, int senderID) {
        synchronized (clientWriters) {
            // Log the message on the server console
            if (recipientID == -1) {
                System.out.println("User " + senderID + " (Broadcast): " + message);
                // Broadcast to all clients
                for (PrintWriter writer : clientWriters.values()) {
                    writer.println("User " + senderID + " (Broadcast): " + message);
                }
            } else {
                PrintWriter writer = clientWriters.get(recipientID);
                if (writer != null) {
                    System.out.println("Private message from User " + senderID + " to User " + recipientID + ": " + message);
                    writer.println("Private message from User " + senderID + ": " + message);
                } else {
                    System.out.println("User " + recipientID + " not found.");
                }
            }
        }
    }

    // Method to remove a client from the clientWriters map when they disconnect
    public static void removeClient(int userID) {
        synchronized (clientWriters) {
            clientWriters.remove(userID);
        }
    }
}

// ClientHandler class to handle communication with each client
class ClientHandler extends Thread {
    private Socket clientSocket;
    private int userID;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket, int userID) {
        this.clientSocket = socket;
        this.userID = userID;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("Welcome, User " + userID + ". Enter your message format as [recipientID]:message");

            String message;
            while ((message = in.readLine()) != null) {
                // Parse message to extract recipient ID and actual message
                String[] splitMessage = message.split(":", 2);
                int recipientID = -1; // Default is broadcast
                if (splitMessage.length == 2) {
                    try {
                        recipientID = Integer.parseInt(splitMessage[0]);
                    } catch (NumberFormatException e) {
                        out.println("Invalid recipient format. Use [recipientID]:message or type a message for broadcast.");
                        continue;
                    }
                    message = splitMessage[1]; // Extract the actual message
                }

                // Send the message to the specific recipient or broadcast it
                ChatServer.sendMessage(message, recipientID, userID);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Clean up when a client disconnects
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChatServer.removeClient(userID);
        }
    }
}


