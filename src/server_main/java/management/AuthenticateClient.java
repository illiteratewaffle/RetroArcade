package management;

import management.ThreadMessage;
import management.ThreadRegistry;
import player.PlayerHandler;
import player.PlayerManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static management.JsonConverter.fromJson;
import static management.JsonConverter.toJson;
import static management.ServerLogger.log;

public class AuthenticateClient implements Runnable {
    Socket clientSocket;

    public AuthenticateClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * This creates a thread that authenticates the player before creating a PlayerHandler thread.
     */
    public void run() {
        // Authenticate the player before allowing them to connect
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            // Check their input
            String auth = bufferedReader.readLine();
            // TODO: Make it so it doesn't crash
            Map<String, Object> authData = fromJson(auth);
            // Make sure the json contains the type, username, and password.
            int playerId = authenticate(authData, printWriter, clientSocket);
            // If the authentication was successful, create a PlayerHandler
            if (playerId != -1) {
                log("AuthenticateClient: Player " + playerId + " authenticated.");
                //Create a blocking queue and player handler to handle the player connection on the server side.
                BlockingQueue<ThreadMessage> queue = new LinkedBlockingQueue<>();
                PlayerHandler playerHandler = new PlayerHandler(clientSocket, queue);
                //Create a thread for the player and start it.
                // TODO: Custom thread name
                Thread playerThread = Thread.ofVirtual().start(playerHandler);
                // Add the new player the ThreadRegistry
                ThreadRegistry.register(playerThread, queue);
                // Add the new player to the playerList
                ThreadRegistry.registerPlayer(playerId, playerHandler);
            }

        } catch (IOException e) {
            log("AuthenticateClient: Failure to initialize BufferedReader/BufferedWriter: ", e.toString());
            // Close the client's socket
            try {
                clientSocket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private int authenticate(Map<String, Object> authData, PrintWriter printWriter, Socket clientSocket) {
        if (authData.containsKey("type")) {
            // Safely extract the "type" field as a String; default to empty string if it's missing or not a String
            String type = (authData.get("type") instanceof String) ? (String) authData.get("type") : "";
            // If the type is login
            if (type.equals("login")) {
                // If it contains
                if (authData.containsKey("username") && authData.containsKey("password")
                        && authData.get("username") instanceof String && authData.get("password") instanceof String) {
                    String username = (String) authData.get("username");
                    String password = (String) authData.get("password");
                    // login logic
                    try {
                        return PlayerManager.authenticatePlayer(username, password);
                    } catch (SQLException e) {
                        sendError(printWriter, clientSocket, e.toString());
                        return -1;
                    }
                } else {
                    sendError(printWriter, clientSocket, "missing or invalid fields");
                    return -1;
                }
            } else if (type.equals("register")) {
                if (authData.containsKey("username") && authData.containsKey("password") && authData.containsKey("email")
                        && authData.get("username") instanceof String && authData.get("password") instanceof String
                        && authData.get("email") instanceof String) {
                    String username = (String) authData.get("username");
                    String password = (String) authData.get("password");
                    String email = (String) authData.get("email");
                    // registration logic
                    try {
                        // check boolean on ProfileCreation.createNewProfile(username, email, password)
                        // something?
                        return PlayerManager.registerPlayer(username, email, password);
                    } catch (SQLException e) {
                        sendError(printWriter, clientSocket, e.toString());
                        return -1;
                    }
                } else {
                    sendError(printWriter, clientSocket, "Registration failed, missing or invalid fields");
                    return -1;
                }
            } else {
                sendError(printWriter, clientSocket, "Authentication failed, invalid json hashmap value for \"type\"");
                return -1;
            }
        } else {
            sendError(printWriter, clientSocket, "Authentication failed, missing json hashmap key \"type\"");
            return -1;
        }
    }

    /**
     * This is used to send an error message
     *
     * @param printWriter  the PrintWriter to the client
     * @param clientSocket the Socket that the client is connected on
     * @param errorMessage the error message for the client and log command
     */
    private void sendError(PrintWriter printWriter, Socket clientSocket, String errorMessage) {
        log("AuthenticateClient:", errorMessage, clientSocket.getRemoteSocketAddress());
        HashMap<String, Object> response = new HashMap<>();
        response.put("type", "error");
        response.put("message", errorMessage);
        printWriter.println(toJson(response));
        printWriter.flush();
        log("AuthenticateClient: Sent: " + toJson(response));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            log("AuthenticateClient: Failure to close socket: ", clientSocket.getRemoteSocketAddress());
        }
    }
}
