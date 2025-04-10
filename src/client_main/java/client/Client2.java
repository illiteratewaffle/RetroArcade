package client;

import GUI_client.TTTController;
import GameLogic_Client.Ivec2;
import client.*;

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


public class Client2 {
    private static BufferedReader input;
    private static PrintWriter output;
    private static final ConcurrentLinkedQueue<Map<String, Object>> messages = new ConcurrentLinkedQueue<>();
    private static boolean running = true;
    private static Map<String, Object> profileData = new HashMap<>();
    private static Map<String, Object> otherProfileData = new HashMap<>();
    private static Socket clientSocket;

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

    public static void logout() throws IOException {
        System.out.println("Shutting down disconnection from server");
        running = false;
        // TODO: COULD THIS STILL BE WRITTEN TO BEFORE THE SERVER FULLY CLOSES?
        messages.clear();
        clientSocket.close();
    }

    private static void authenticate(String username, String password, String email) {
        // Authenticate user
        Map<String, Object> authData = new HashMap<>();
        if (email == null) {
            authData.put("type", "login");
            authData.put("email", email);
        } else {
            authData.put("type", "register");
        }
        authData.put("username", username);
        authData.put("password", password);
        // Send authData to the server to authenticate the user
        output.println(JsonConverter.toJson(authData));
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
                Map<String, Object> response = JsonConverter.fromJson(input.readLine());
                // Check if message is "type":"chat"
                if (response.get("type") instanceof String && response.get("type").equals("chat")) {
                    Thread.ofVirtual().start(() -> receivedChatMessage(response));
                } else if (response.get("type") instanceof String && response.get("type").equals("game")) {
                    if (response.get("command") instanceof String && response.get("command").equals("startTurn")) {
                        Thread.ofVirtual().start(Client2::receivedStartTurn);
                    } else if (response.get("command") instanceof String && response.get("command").equals("startGame")) {
                        Thread.ofVirtual().start(() -> receivedStartGame((int) response.get("data")));
                    }
                }
                synchronized (messages) {
                    messages.add(response);
                    messages.notifyAll();
                }
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

    private static void receivedChatMessage(Map<String, Object> response) {
        String sender = (String) response.get("sender");
        String message = (String) response.get("message");
        TTTController.currentMessage = String.format("%s: %s\n", sender, message);
        TTTController.msgReceived.set(true);
        // TODO: GUI SHIT HERE
    }

    private static void receivedStartTurn() {
        TTTController.isYourTurn.set(true);
    }

    private static void receivedStartGame(int piece) {
        System.out.println("hello1");
        System.out.println(getCurrentPlayer());
        System.out.println(piece);
        TTTController.yourPiece = piece;
        if (piece == 1) {
            System.out.println("player 1");
            TTTController.isYourTurn.set(true);
        } else if (piece == 2){
            System.out.println("player 2");
            TTTController.yourPiece = 2;
        }
        System.out.println("end");
    }

    private static void forwardToServer(Map<String, Object> forward) {
        try {
            output.println(JsonConverter.toJson(forward));
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
                        messages.remove(message);
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

    public static void getProfileInfo() {
        // Send the bio request to the server.
        Map<String, Object> forwardBio = new HashMap<>();
        forwardBio.put("type", "profile-info-request");
        forwardBio.put("info", "bio");
        forwardToServer(forwardBio);
        Map<String, Object> messageBio = getResponseFromServer("info", "bio");
        profileData.put("bio", messageBio);

        // Send the nickname request to the server
        Map<String, Object> forwardNickname = new HashMap<>();
        forwardNickname.put("type", "profile-info-request");
        forwardNickname.put("info", "nickname");
        forwardToServer(forwardNickname);
        Map<String, Object> messageNickname = getResponseFromServer("info", "nickname");
        profileData.put("nickname", messageNickname);

        // Send the username request to the server
        Map<String, Object> forwardUsername = new HashMap<>();
        forwardUsername.put("type", "profile-info-request");
        forwardUsername.put("info", "nickname");
        forwardToServer(forwardUsername);
        Map<String, Object> messageUsername = getResponseFromServer("info", "username");
        profileData.put("username", messageUsername);

        // Send the profile path request to the server
        Map<String, Object> forwardProfilePath = new HashMap<>();
        forwardProfilePath.put("type", "profile-info-request");
        forwardProfilePath.put("info", "profile-path");
        forwardToServer(forwardProfilePath);
        Map<String, Object> messageProfilePath = getResponseFromServer("info", "profile-path");
        profileData.put("profile-path", messageProfilePath);

        // Send the friends list request to the server
        Map<String, Object> forwardFriends = new HashMap<>();
        forwardFriends.put("type", "profile-info-request");
        forwardFriends.put("info", "friends");
        forwardToServer(forwardFriends);
        Map<String, Object> messageFriends = getResponseFromServer("info", "friends");
        profileData.put("friends", messageFriends);

        // Send the friend requests, request to the server
        Map<String, Object> forwardFriendRequests = new HashMap<>();
        forwardFriendRequests.put("type", "profile-info-request");
        forwardFriendRequests.put("info", "friendRequests");
        forwardToServer(forwardFriendRequests);
        Map<String, Object> messageFriendRequest = getResponseFromServer("info", "friendRequests");
        profileData.put("friendRequests", messageFriendRequest);

        // Send the game history request to the server
        Map<String, Object> forwardGameHistory = new HashMap<>();
        forwardGameHistory.put("type", "profile-info-request");
        forwardGameHistory.put("info", "gameHistory");
        forwardToServer(forwardGameHistory);
        Map<String, Object> messageGameHistory = getResponseFromServer("info", "gameHistory");
        profileData.put("gameHistory", messageGameHistory);

        // Send the win loss ratio request to the server.
        Map<String, Object> forwardRatio = new HashMap<>();
        forwardRatio.put("type", "profile-info-request");
        forwardRatio.put("info", "winLossRatio");
        forwardToServer(forwardRatio);
        Map<String, Object> messageRatio = getResponseFromServer("info", "winLossRatio");
        profileData.put("winLossRatio", messageRatio);

        // Send the rating request to the server.
        Map<String, Object> forwardRating = new HashMap<>();
        forwardRating.put("type", "profile-info-request");
        forwardRating.put("info", "rating");
        forwardToServer(forwardRating);
        Map<String, Object> messageRating = getResponseFromServer("info", "rating");
        profileData.put("rating", messageRating);

        // Send the rank request to the server.
        Map<String, Object> forwardRank = new HashMap<>();
        forwardRank.put("type", "profile-info-request");
        forwardRank.put("info", "rank");
        forwardToServer(forwardRank);
        Map<String, Object> messageRank = getResponseFromServer("info", "rank");
        profileData.put("rank", messageRank);

        // Send the wins request to the server
        Map<String, Object> forwardWins = new HashMap<>();
        forwardWins.put("type", "profile-info-request");
        forwardWins.put("info", "wins");
        forwardToServer(forwardWins);
        Map<String, Object> messageWins = getResponseFromServer("info", "wins");
        profileData.put("wins", messageWins);
    }

    public static String getBio() {
        return (String) profileData.get("bio");
    }

    public static String getNickname() {
        return (String) profileData.get("nickname");
    }

    public static String getUsername() {
        return (String) profileData.get("username");
    }

    public static String getProfilePath() {
        return (String) profileData.get("profile-path");
    }

    public static List<String> getFriends() {
        return (List<String>) profileData.get("friends");
    }

    public static List<String> getFriendRequests() {
        return (List<String>) profileData.get("friendRequests");
    }

    public static List<String> getGameHistory() {
        return (List<String>) profileData.get("gameHistory");
    }

    public static Double getWinLossRatio(int i) {
        List<Double> winLossRatio = (List<Double>) profileData.get("winLossRatio");
        return winLossRatio.get(i);
    }

    public static Integer getRating(int i) {
        List<Integer> rating = (List<Integer>) profileData.get("rating");
        return rating.get(i);
    }

    public static String getRank(int i) {
        List<String> ranks = (List<String>) profileData.get("rank");
        return ranks.get(i);
    }

    public static int getWins(int i) {
        List<Integer> wins = (List<Integer>) profileData.get("wins");
        return wins.get(i);
    }

    public static void getOtherProfileInfo(String username) {

        // Send the request to the server for the other profiles info.
        Map<String, Object> viewProfileForward = new HashMap<>();
        viewProfileForward.put("type", "view-profile");
        viewProfileForward.put("username", username);
        forwardToServer(viewProfileForward);

        // Wait to receive the info
        Map<String, Object> otherProfile = getResponseFromServer("type", "view-profile");

        //Retrieve all the data in the correct form from the other profile.
        String otherBio = (String) otherProfile.get("bio");
        String otherUsername = (String) otherProfile.get("username");
        String otherNickname = (String) otherProfile.get("nickname");
        String otherProfilePath = (String) otherProfile.get("profile-path");
        List<String> otherFriends = (List<String>) otherProfile.get("friends");
        List<String> otherGameHistory = (List<String>) otherProfile.get("gameHistory");
        List<Double> otherWinLossRatio = (List<Double>) otherProfile.get("winLossRatio");
        List<Integer> otherRating = (List<Integer>) otherProfile.get("rating");
        List<String> otherRank = (List<String>) otherProfile.get("rank");
        List<Integer> otherWins = (List<Integer>) otherProfile.get("wins");

        // Put the other profile data in the attribute.
        otherProfile.put("bio", otherBio);
        otherProfile.put("username", otherUsername);
        otherProfile.put("nickname", otherNickname);
        otherProfile.put("profile-path", otherProfilePath);
        otherProfile.put("friends", otherFriends);
        otherProfile.put("gameHistory", otherGameHistory);
        otherProfile.put("wins", otherWins);
        otherProfile.put("rating", otherRating);
        otherProfile.put("rank", otherRank);
        otherProfile.put("wins", otherWins);
    }

    public static String getOtherBio() {
        return (String) otherProfileData.get("bio");
    }

    public static String getOtherNickname() {
        return (String) otherProfileData.get("nickname");
    }

    public static String getOtherUsername() {
        return (String) otherProfileData.get("username");
    }

    public static String getOtherProfilePath() {
        return (String) otherProfileData.get("profile-path");
    }

    public static List<String> getOtherFriends() {
        return (List<String>) otherProfileData.get("friends");
    }


    public static List<String> getOtherGameHistory() {
        return (List<String>) otherProfileData.get("gameHistory");
    }

    public static Double getOtherWinLossRatio(int i) {
        List<Double> winLossRatio = (List<Double>) otherProfileData.get("winLossRatio");
        return winLossRatio.get(i);
    }

    public static Integer getOtherRating(int i) {
        List<Integer> rating = (List<Integer>) otherProfileData.get("rating");
        return rating.get(i);
    }

    public static String getOtherRank(int i) {
        List<String> ranks = (List<String>) otherProfileData.get("rank");
        return ranks.get(i);
    }

    public static int getOtherWins(int i) {
        List<Integer> wins = (List<Integer>) otherProfileData.get("wins");
        return wins.get(i);
    }

    public static boolean getOtherOnlineStatus() {
        return (boolean) otherProfileData.get("online");
    }

    public static void acceptRequest(String username) {

        //Create the map for the thread message
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("type", "accept-friend-request");
        messageMap.put("username", username);
        forwardToServer(messageMap);

    }

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
    public static int getWinner() {
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
    public static boolean getGameOngoing() {
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
    public static ArrayList<int[][]> getBoardCells(int layerMask) {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "getBoardCells");
        forward.put("parameter", layerMask);
        forwardToServer(forward);
        // Now wait until the correct response comes back
        Map<String, Object> response = getResponseFromServer("command", "getBoardCells");
        // Get the data
        System.out.println(response.get("data"));
        return ConverterTools.tripleListToListOf2dArray((List<List<List<Integer>>>) response.get("data"));
    }

    /**
     * @return The size of the Board.
     */
    public static Ivec2 getBoardSize() {
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
    public static int getCurrentPlayer() {
        // Send message to server
        Map<String, Object> forward = new HashMap<>();
        forward.put("type", "game");
        forward.put("command", "getCurrentPlayer");
        forwardToServer(forward);
        // Now wait until the correct response comes back
        try {
            Thread.sleep(300);
        } catch (InterruptedException e){
        }
        Map<String, Object> response = getResponseFromServer("command", "getCurrentPlayer");
        try {
            Thread.sleep(300);
        } catch(InterruptedException e){

        }
        // Get the data
        return (int) response.get("data");
    }

    /**
     * @return True if the Game Ongoing has been changed
     * since the last call to <code>receiveInput</code> or <code>removePlayer</code>.
     */
    public static boolean gameOngoingChangedSinceLastCommand() {
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
    public static boolean winnersChangedSinceLastCommand() {
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
    public static boolean currentPlayerChangedSinceLastCommand() {
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
    public static int boardChangedSinceLastCommand() {
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

    public static boolean checkDraw() {
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

