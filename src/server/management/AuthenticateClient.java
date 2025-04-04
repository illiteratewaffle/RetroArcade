package server.management;

import client.Encoder;
import server.player.Player;
import server.player.PlayerHandler;
import server.player.PlayerManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static server.management.JsonConverter.fromJson;
import static server.management.JsonConverter.toJson;
import static server.management.ServerLogger.log;

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
            Map<String, Object> authData = fromJson(auth);
            // Make sure the json contains the type, username, and password.
            int playerId = authenticate(authData, printWriter, clientSocket);
            // If the authentication was successful, create a PlayerHandler
            if (playerId == -1) {
                //Create a blocking queue and player handler to handle the player connection on the server side.
                BlockingQueue<ThreadMessage> queue = new LinkedBlockingQueue<>();
                PlayerHandler playerHandler = new PlayerHandler(clientSocket, queue);
                //Create a thread for the player and start it.
                Thread playerThread = Thread.ofVirtual().start(playerHandler);
                // Add the new player the ThreadRegistry
                ThreadRegistry.threadRegistry.put(playerThread, queue);
                // Add the new player to the playerList
                synchronized (ThreadRegistry.playerList) {
                    ThreadRegistry.playerList.add(new Player(playerThread, playerHandler));
                    // log("The new player is on Thread", playerThread.threadId());
                    ThreadRegistry.playerList.notifyAll();
                }
            }

        } catch (IOException e){
            log("Failure to initialize AuthenticateClient BufferedReader/BufferedWriter: ", e.toString());
            // TODO: close client's connection
        }
    }

    /**
     * This is used to send an error message
     * @param printWriter the PrintWriter to the client
     * @param clientSocket the Socket that the client is connected on
     * @param errorMessage the error message for the client and log command
     */
    private void sendError(PrintWriter printWriter, Socket clientSocket, String errorMessage) {
        log("Authenticate Client:", errorMessage, clientSocket.getRemoteSocketAddress());
        HashMap<String, Object> response = new HashMap<>();
        response.put("type", "error");
        response.put("message", errorMessage);
        printWriter.println(toJson(response));
        try {
            clientSocket.close();
        } catch (IOException e) {
            log("Failure to close socket: ", clientSocket.getRemoteSocketAddress());
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
                    // TODO: Implement login logic here
                    int playerId = PlayerManager.authenticatePlayer(username, password);
                    if (playerId != -1) {
                        sendError(printWriter, clientSocket, "Login failed, invalid username or password");
                        return -1;
                    }
                    return playerId;
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
                    // TODO: Implement registration logic here
                    int playerId = PlayerManager.registerPlayer(username, password, email);
                    if (playerId == -1) {
                        sendError(printWriter, clientSocket, "Registration failed, account already exists");
                        return -1;
                    }
                    return playerId;
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
}
