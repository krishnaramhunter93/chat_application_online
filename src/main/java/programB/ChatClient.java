package programB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            new MessageReceiver().start(); // Thread to handle incoming messages

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            System.out.println("To send a private message, use the format [recipientID]:message. For broadcast, type a message directly.");
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);  // Send user input to server
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thread for receiving messages from the server
    private class MessageReceiver extends Thread {
        @Override
        public void run() {
            String response;
            try {
                while ((response = in.readLine()) != null) {
                    System.out.println(response);  // Display message received from server
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.startConnection("127.0.0.1", 12345); // Connect to localhost server
    }
}
