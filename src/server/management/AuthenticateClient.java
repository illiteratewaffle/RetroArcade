package server.management;

import client.Encoder;
import server.player.Player;
import server.player.PlayerHandler;

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
            Map<String, Object> thing = fromJson(auth);
            // Make sure the json contains the type, username, and password.
            if (thing.containsKey("type")) {
                // Safely extract the "type" field as a String; default to empty string if it's missing or not a String
                String type = (thing.get("type") instanceof String) ? (String) thing.get("type") : "";
                // If the type is login
                if (type.equals("login")) {
                    // If it contains
                    if (thing.containsKey("username") && thing.containsKey("password")
                            && thing.get("username") instanceof String && thing.get("password") instanceof String) {
                        String username = (String) thing.get("username");
                        String password = (String) thing.get("password");
                        // TODO: Implement login logic here
                    } else {
                        sendError(printWriter, clientSocket, "missing or invalid fields");
                    }
                } else if (type.equals("register")) {
                    if (thing.containsKey("username") && thing.containsKey("password") && thing.containsKey("email")
                            && thing.get("username") instanceof String && thing.get("password") instanceof String
                            && thing.get("email") instanceof String) {
                        String username = (String) thing.get("username");
                        String password = (String) thing.get("password");
                        String email = (String) thing.get("email");
                        // TODO: Implement registration logic here
                    } else {
                        sendError(printWriter, clientSocket, "Registration failed, missing or invalid fields for");
                    }
                } else {
                    sendError(printWriter, clientSocket, "Authentication failed, invalid json hashmap value for \"type\" for");
                }
            } else {
                sendError(printWriter, clientSocket, "Authentication failed, missing json hashmap key \"type\" for");
            }
        } catch (IOException e){
            log("Failure to initialize AuthenticateClient BufferedReader/BufferedWriter: ", e.toString());
            // TODO: close client's connection
        }

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

}
