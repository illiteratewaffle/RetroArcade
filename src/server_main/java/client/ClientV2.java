package client;

import GameLogic_Client.Ivec2;
import management.ConverterTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import static management.JsonConverter.fromJson;
import static management.JsonConverter.toJson;

public class ClientV2 {
    private static BufferedReader input;
    private static PrintWriter output;
    private static final ConcurrentLinkedQueue<Map<String, Object>> messages = new ConcurrentLinkedQueue<>();
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
        // TODO: COULD THIS STILL BE WRITTEN TO BEFORE THE SERVER FULLY CLOSES?
        messages.clear();
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

    private static void forwardToServer(Map<String, Object> forward) {
        try {
            output.println(toJson(forward));
        } catch (IllegalArgumentException e) {
            System.out.println("Attempted to send invalid json: " + e.getMessage());
        }
    }

    private static Map<String, Object> getResponseFromServer(String key, String value) {
        try {
            while (true) {
                synchronized (messages) {
                    messages.wait();
                }
                // Check for the wanted message
                for (Map<String, Object> message : messages) {
                    if (message.get(key).equals(value)) {
                        return message;
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Failed to receive a response from the server: " + e.getMessage());
            return null;
        }
    }

    // NOW WE DO THE FUCKING CALL FUNCTIONS BABY WOO!!!

    /**
     * Receive a 2D-Integer-Coordinate Input from the Player, and process it.
     * @param ivec2 A 2D-Integer-Coordinate Input that corresponds to a Board Cell.
     */
    public void receiveInput(Ivec2 ivec2) {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "receiveInput");
        forward.put("parameter", ConverterTools.ivecToList(ivec2));
        forwardToServer(forward);
    }

    /**
     * Remove the player of a given Index (counting from 0) from the game.
     * This will not change the index of other Players.
     * @param player The index of the player to remove.
     * @throws IndexOutOfBoundsException If no players with the given Index exists.
     */
    public void removePlayer(int player) throws IndexOutOfBoundsException {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "receiveInput");
        forward.put("parameter", player);
        forwardToServer(forward);
    }

    /**
     * @return An array of integers containing the Index of the winners of the game.
     * If there are multiple winners, the game may be interpreted as a tie between said winners.
     */
    public int getWinner() {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "getWinner");
        forwardToServer(forward);
        // Now wait until the correct response comes back
        Map<String, Object> response = getResponseFromServer("command", "getWinner");
        // Get the data
        return (int) response.get("data");
    }

    /**
     * @return <code>True</code> if the game is still ongoing; <code>False</code> otherwise.
     */
    public boolean getGameOngoing() {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "getGameOngoing");
        forwardToServer(forward);
        // Now wait until the correct response comes back
        Map<String, Object> response = getResponseFromServer("command", "getGameOngoing");
        // Get the data
        return (boolean) response.get("data");
    }

    /**
     * @param layerMask A bit-string, where the bits of all the layers to query are set to 1.
     * @return
     * An array list of 2D integer arrays representing the cells of the board at each of the requested layer.
     */
    public ArrayList<int[][]> getBoardCells(int layerMask) {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "getBoardSize");
        forwardToServer(forward);
        // Now wait until the correct response comes back
        Map<String, Object> response = getResponseFromServer("command", "getBoardSize");
        // Get the data
        return ConverterTools.tripleListToListOf2dArray((List<List<List<Integer>>>) response.get("data"));
    }

    /**
     * @return The size of the Board.
     */
    public Ivec2 getBoardSize() {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "getBoardSize");
        forwardToServer(forward);
        // Now wait until the correct response comes back
        Map<String, Object> response = getResponseFromServer("command", "getBoardSize");
        // Get the data
        return ConverterTools.listToIvec((List<Integer>) response.get("data"));
    }

    /**
     * @return The index of the current player (the player whose turn is currently ongoing).
     */
    public int getCurrentPlayer() {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "getCurrentPlayer");
        forwardToServer(forward);
        // Now wait until the correct response comes back
        Map<String, Object> response = getResponseFromServer("command", "getCurrentPlayer");
        // Get the data
        return (int) response.get("data");
    }

    /**
     * @return True if the Game Ongoing has been changed
     * since the last call to <code>receiveInput</code> or <code>removePlayer</code>.
     */
    public boolean gameOngoingChangedSinceLastCommand() {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "gameOngoingChangedSinceLastCommand");
        forwardToServer(forward);
        // Now wait until the correct response comes back
        Map<String, Object> response = getResponseFromServer("command", "gameOngoingChangedSinceLastCommand");
        // Get the data
        return (boolean) response.get("data");
    }

    /**
     * @return True if the List of Winners has been changed
     * since the last call to <code>receiveInput</code> or <code>removePlayer</code>.
     */
    public boolean winnersChangedSinceLastCommand() {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "winnersChangedSinceLastCommand");
        forwardToServer(forward);
        // Now wait until the correct response comes back
        Map<String, Object> response = getResponseFromServer("command", "winnersChangedSinceLastCommand");
        // Get the data
        return (boolean) response.get("data");
    }

    /**
     * @return True if the Current Player has been changed
     * since the last call to <code>receiveInput</code> or <code>removePlayer</code>.
     */
    public boolean currentPlayerChangedSinceLastCommand() {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "currentPlayerChangedSinceLastCommand");
        forwardToServer(forward);
        // Now wait until the correct response comes back
        Map<String, Object> response = getResponseFromServer("command", "currentPlayerChangedSinceLastCommand");
        // Get the data
        return (boolean) response.get("data");
    }

    /**
     * @return The bit-mask for all the Layers that has been changed
     * since the last call to <code>receiveInput</code> or <code>removePlayer</code>.
     */
    public int boardChangedSinceLastCommand() {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "boardChangedSinceLastCommand");
        forwardToServer(forward);
        // Now wait until the correct response comes back
        Map<String, Object> response = getResponseFromServer("command", "boardChangedSinceLastCommand");
        // Get the data
        return (int) response.get("data");
    }
}
