package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TemporaryClient {
    public static void main(String[] args) {
        // Server's address (or IP)
        String serverAddress = "10.9.181.75";
        // Server's port
        int port = 5050;
        if (args.length == 2) {
            serverAddress = args[0];
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("The command line arguments must be: \"server address\" port");
            }
        }
        joinServer(serverAddress, port);
    }
    private static void joinServer(String serverAddress, int port) {

        try (Socket socket = new Socket(serverAddress, port)) {
            // Create input and output streams for communication
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Thread thread = Thread.ofVirtual().start(() -> outputToServer(socket));

            // read from the server
            while (true) {
                // Read response from the server
                String response = input.readLine();
                System.out.println("Server response: " + response);
                // System.out.println(response);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void outputToServer(Socket socket) {
        // write to the server
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                // Send a message to the server
                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();
                output.println(message);
                // System.out.println("Sent to server: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
