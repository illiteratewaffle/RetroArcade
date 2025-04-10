package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import static management.JsonConverter.fromJson;
import static management.JsonConverter.toJson;

public class ClientV2 {
    private static BufferedReader input;
    private static PrintWriter output;
    private static ConcurrentLinkedQueue<Map<String, Object>> messages = new ConcurrentLinkedQueue<>();
    private static boolean running = true;

    /**
     * Login to the server
     * @param serverAddress the server ip address
     * @param serverPort the server port
     * @param username the username of the player
     * @param password the password of the player
     * @return boolean true if successful, false if not
     */
    public static boolean login(String serverAddress, int serverPort, String username, String password) {
        // Make a Thread for input to server
        try (Socket socket = new Socket(serverAddress, serverPort)) {
            // Create input and output streams for communication
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            // Authenticate the user before creating a thread
            authenticate(username, password, null);
            Thread thread = Thread.ofVirtual().start(() -> listenServer(socket));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Make a shit ton of static functions, have a login
    public static boolean register(String serverAddress, int serverPort, String username, String password, String email) {
        // Make a Thread for input to server
        try (Socket socket = new Socket(serverAddress, serverPort)) {
            // Create input and output streams for communication
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            // Authenticate the user before creating a thread
            authenticate(username, password, email);
            Thread thread = Thread.ofVirtual().start(() -> listenServer(socket));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void logout() {
        System.out.println("Shutting down disconnection from server");
        running = false;
    }

    private static void authenticate(String username, String password, String email) {
        // Authenticate user
        Map<String, Object> authData = new HashMap<>();
        if (email != null) {
            authData.put("type", "login");
            authData.put("email", email);
        } else {
            authData.put("type", "register");
        }
        authData.put("username", username);
        authData.put("password", password);
        // Send authData to the server to authenticate the user
        output.println(toJson(authData));
    }

    /**
     * This function is for listening to the server
     * @param socket the Socket for the connection
     */
    private static void listenServer(Socket socket) {
        // Now listen to all inputs from the server
        while (running) {
            try {
                // Take a response and put it in the ConcurrentLinkedQueue
                String response = input.readLine();
                messages.add(fromJson(response));
                messages.notifyAll();
                // TODO: TEMPORARY PRINT TO SHOW IT WAS RECEIVED
                System.out.println("Received message: " + response);
            } catch (IOException e) {
                System.out.println("Server may have shut down?: " + e.getMessage());
                // Shut down thread if IOException
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid json message received: " + e.getMessage());
            }
        }
    }
}
