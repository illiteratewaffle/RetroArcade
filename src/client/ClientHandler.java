package client;
import server.player.PlayerHandler;

import java.io.IOException;
import java.net.Socket;

/**
 * Handles client connections and communication.
 */
public class ClientHandler  {
    private final Socket clientSocket;
    private final PlayerHandler playerHandler;
    private boolean running;

    /**
     * Constructor for ClientHandler.
     *
     * @param clientSocket  the Socket that the client is connected on
     * @param playerHandler The associated PlayerHandler for communication.
     */
    public ClientHandler(Socket clientSocket, PlayerHandler playerHandler) {
        this.clientSocket = clientSocket;
        this.playerHandler = playerHandler;
        this.running = true;
    }
      /**
     * The function that the thread runs
     */
    public void run() {
        try {
            while (running) {
                // Implement client communication handling logic
            }
        } catch (Exception e) {

        } finally {
            closeConnection();  // Ensure the connection is closed on error or exit
        }
    }

    /**
     * Processes function requests.
     */
    public void processRequest(String request) {
        // Implement request processing
    }

    /**
     * Converts data into a JSON string.
     */
    public String ConvertToJson(Object data) {

        try {
            // Convert recieved Function to JSON
            return "{}";  // Placeholder
        } catch (Exception e) {
            return "{}"; // Return an empty JSON object on error
        }
    }

    /**
     * Converts a JSON string into a return function.
     */
    public Object ConvertFromJson(String jsonData) {
        // Convert JSON encoding to Function to send back
        return null;
    }

    /**
     * Sends JSON string to the associated PlayerHandler.
     */
    public void sendToPlayerHandler(String message) {
        try {
            // Send encrypted message to PlayerHandler
          //  return null;
        } catch (Exception e) {
            System.err.println("Error decoding JSON: " + e.getMessage());
          //  return null;
        }
    }

    /**
     * Sends a response function back to sender.
     */
    public void sendResponse(String response) {
        // Send response to Sender
    }

    /**
     * Closes the client connection.
     */
    public void closeConnection() {
        running = false;
        try {
            clientSocket.close();
        } catch (IOException e) {
            return;
        }
    }
}
