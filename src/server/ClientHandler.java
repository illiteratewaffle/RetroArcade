package server;
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
        while (running) {
            // Implement client communication
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
        // Convert recieved Function to JSON
        return "{}";  // Placeholder
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
        // Send encrypted message to PlayerHandler
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
