package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static management.JsonConverter.fromJson;
import static management.JsonConverter.toJson;

public class TemporaryClient {
    public static void main(String[] args) {
        // Server's address (or IP)
        String serverAddress = "localhost";
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
                if (response != null) {
                    // Check what type of message it is
                    Map<String, Object> data = fromJson(response);
                    if (data.containsKey("type") && data.get("type").equals("error")) {
                        // if it is an error, print the error
                        System.err.println("Error: " + data.get("message"));
                    } else if (data.containsKey("type") && data.get("type").equals("message")) {
                        System.out.println(data.get("sender") + ": " + data.get("message"));
                    } else {
                        System.out.println(response);
                    }
                }
                // System.out.println(response);
            }
            // System.out.println("Disconnected from the server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void outputToServer(Socket socket) {
        // write to the server
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Ask for the information to log in
            Map<String, Object> authData = getAuthData();
            output.println(toJson(authData));

            while (true) {
                // Send a message to the server
                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();

                Map<String, Object> data = new HashMap<>();
                if (message.startsWith("enqueue")) {
                    if (message.contains("0")) {
                        data.put("type", "enqueue");
                        data.put("game-type", 0);
                    } else if (message.contains("1")) {
                        data.put("type", "enqueue");
                        data.put("game-type", 1);
                    } else if (message.contains("2")) {
                        data.put("type", "enqueue");
                        data.put("game-type", 2);
                    }
                } else {
                    data.put("type", "message");
                    data.put("message", message);
                }

                output.println(toJson(data));
                // System.out.println("Sent to server: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> getAuthData() {
        Scanner scanner = new Scanner(System.in);
        Map<String, Object> authData = new HashMap<>();

        String type;
        while (true) {
            System.out.print("Are you logging in or registering? (login/register): ");
            type = scanner.nextLine().trim().toLowerCase();

            if (!type.equals("login") && !type.equals("register")) {
                System.out.println("Invalid input. Must be 'login' or 'register'.");
            } else {
                break;
            }
        }

        authData.put("type", type);

        System.out.print("Enter username: ");
        authData.put("username", scanner.nextLine().trim());

        System.out.print("Enter password: ");
        authData.put("password", scanner.nextLine().trim());

        if (type.equals("register")) {
            System.out.print("Enter email: ");
            authData.put("email", scanner.nextLine().trim());
        }

        return authData;
    }
}

