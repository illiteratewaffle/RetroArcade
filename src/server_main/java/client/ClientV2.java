package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static client.TemporaryClient.outputToServer;
import static management.JsonConverter.toJson;

public class ClientV2 {
    private static BufferedReader input;
    private static PrintWriter output;
    private static boolean running = true;

    // Make a shit ton of static functions, have a login
    public static void login(String serverAddress, int serverPort, String username, String password) {
        // Make a Thread for input to server
        Thread thread = Thread.ofVirtual().start(() -> listenServer(serverAddress, serverPort, username, password));
    }

    // Make a shit ton of static functions, have a login
    public static void register(String serverAddress, int serverPort, String username, String password, String email) {
        // Make a Thread for input to server
        Thread thread = Thread.ofVirtual().start(() -> listenServer(serverAddress, serverPort, username, password, email));
    }

    public static void logout() {
        System.out.println("Shutting down disconnection from server");
        running = false;
    }

    /**
     * This function is for listening to the server
     * @param serverAddress the ip address for the server
     * @param serverPort the port for the server
     */
    private static void listenServer(String serverAddress, int serverPort, String username, String password, String email) {
        try (Socket socket = new Socket(serverAddress, serverPort)) {
            // Create input and output streams for communication
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
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
            // Now listen to all inputs from the server
            while (running) {
                String response = input.readLine();
                System.out.println(response);
                // TODO: Take response and put it in some kind of thread safe data type
            }
        } catch (IOException e) {
            // TODO: RETURN A FALSE TO START SERVER MAYBE?
            System.out.println("Unable to connect to server");
        }
    }
}
