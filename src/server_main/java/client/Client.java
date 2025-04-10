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
import java.util.concurrent.TimeoutException;

import static management.JsonConverter.fromJson;
import static management.JsonConverter.toJson;

public class Client {
    private static Socket clientSocket;
    private static BufferedReader input;
    private static PrintWriter output;
    private static final ConcurrentLinkedQueue<Map<String, Object>> messages = new ConcurrentLinkedQueue<>();
    private static boolean running = true;

    public int yourplayer = 0;

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
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            clientSocket = socket;
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
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            clientSocket = socket;
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
        if (email == null) {
            authData.put("type", "login");
        } else {
            authData.put("type", "register");
            authData.put("email", email);
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
                // Take a response
                Map<String, Object> response = fromJson(input.readLine());
                // TODO: ADD SUPPORT FOR GAME STARTTURN AND GAME STARTGAME
                if (response.get("type") instanceof String && response.get("type").equals("chat")) {
                    // If the message is a chat, tell the GUI
                    Thread.ofVirtual().start(() -> receivedChatMessage(response));
                } else if (response.get("type") instanceof String && response.get("type").equals("game") &&
                        response.get("command") instanceof String && response.get("command").equals("startGame")) {
                    // If the message is a startGame, tell the GUI
                    Thread.ofVirtual().start(() -> receivedStartGame((int) response.get("data")));
                } else if (response.get("type") instanceof String && response.get("type").equals("game") &&
                        response.get("command") instanceof String && response.get("command").equals("startTurn")) {
                    // If the message is a startTurn, tell the GUI
                    Thread.ofVirtual().start(Client::receivedStartTurn);
                } else {
                    // Otherwise, put it in the message queue
                    synchronized (messages) {
                        messages.add(response);
                        messages.notifyAll();
                    }
                }
                // TODO: TEMPORARY PRINT TO SHOW IT WAS RECEIVED
                System.out.println("Received message: " + response);
            } catch (IOException e) {
                System.err.println("Server may have shut down?: " + e.getMessage());
                // Shut down thread if IOException
                break;
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid json message received: " + e.getMessage());
            }
        }
    }

    private static void receivedChatMessage(Map<String, Object> response) {
        String sender = (String) response.get("sender");
        String message = (String) response.get("message");
        // TODO: GUI SHIT HERE
    }

    private static void receivedStartTurn() {
        // TODO: GUI SHIT HERE
    }

    private static void receivedStartGame(int piece) {
        // TODO: GUI SHIT HERE
        System.out.println("Game started: "+piece);

    }

    private static void forwardToServer(Map<String, Object> forward) {
        try {
            output.println(toJson(forward));
        } catch (IllegalArgumentException e) {
            System.err.println("Attempted to send invalid json: " + e.getMessage());
        }
    }

    /**
     * Waits until the server responds with the
     * @param key
     * @param value
     * @return
     */
    private static Map<String, Object> getResponseFromServer(String key, String value) throws TimeoutException {
        // Set timeout for the message response
        long timeoutMillis = 5000;
        long startTime = System.currentTimeMillis();
        long remainingTime = timeoutMillis;
        // Attempt to recieve the message from the server
        try {
            synchronized (messages) {
                while (remainingTime > 0) {
                    // Wait until the messages queue is updated
                    messages.wait();

                    // Check for the wanted message
                    for (Map<String, Object> message : messages) {
                        if (message.get(key).equals(value)) {
                            // Remove the message from the queue then return it
                            messages.remove(message);
                            return message;
                        }
                    }

                    // Calculate how much time is left
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    remainingTime -= elapsedTime;
                }
                // Failed to receive a response back from the server within timeoutMillis
                System.err.println("Failed to receive a response back from the server within " + timeoutMillis + " milliseconds.");
                throw new TimeoutException("Failed to receive a response back from the server within " + timeoutMillis + " milliseconds.");
            }
        } catch (InterruptedException e) {
            System.err.println("Failed to receive a response from the server: " + e.getMessage());
            throw new TimeoutException("Failed to receive a response from the server: " + e.getMessage());
        }
    }

    // NOW WE DO THE FUCKING CALL FUNCTIONS BABY WOO!!!

    public static void sendChatMessage(String message) {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "chat");
        forward.put("message", message);
        forwardToServer(forward);
    }

    public static void enqueue(int gameType) {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "enqueue");
        forward.put("game-type", gameType);
        forwardToServer(forward);
    }

    /**
     * Receive a 2D-Integer-Coordinate Input from the Player, and process it.
     * @param ivec2 A 2D-Integer-Coordinate Input that corresponds to a Board Cell.
     */
    public static void receiveInput(Ivec2 ivec2) {
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
    public static void removePlayer(int player) throws IndexOutOfBoundsException {
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
    public static int getWinner() throws TimeoutException {
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
    public static boolean getGameOngoing() throws TimeoutException {
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
    public static ArrayList<int[][]> getBoardCells(int layerMask) throws TimeoutException {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "getBoardCells");
        forward.put("parameter", layerMask);
        forwardToServer(forward);
        // Now wait until the correct response comes back
        Map<String, Object> response = getResponseFromServer("command", "getBoardCells");
        // Get the data
        return ConverterTools.tripleListToListOf2dArray((List<List<List<Integer>>>) response.get("data"));
    }

    /**
     * @return The size of the Board.
     */
    public static Ivec2 getBoardSize() throws TimeoutException {
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
    public static int getCurrentPlayer() throws TimeoutException {
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
    public static boolean gameOngoingChangedSinceLastCommand() throws TimeoutException {
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
    public static boolean winnersChangedSinceLastCommand() throws TimeoutException {
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
    public static boolean currentPlayerChangedSinceLastCommand() throws TimeoutException {
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
    public static int boardChangedSinceLastCommand() throws TimeoutException {
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

    public static boolean checkDraw() throws TimeoutException {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "checkDraw");
        forwardToServer(forward);
        // Now wait until the correct response comes back
        Map<String, Object> response = getResponseFromServer("command", "checkDraw");
        // Get the data
        return (boolean) response.get("data");
    }
}
