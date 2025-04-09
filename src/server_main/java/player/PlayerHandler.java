package player;

import AuthenticationAndProfile.Profile;
import AuthenticationAndProfile.ProfileDatabaseAccess;
import GameLogic_Client.Ivec2;
import com.almasb.fxgl.net.Server;
import management.*;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import static management.JsonConverter.fromJson;
import static management.JsonConverter.toJson;
import static management.ServerLogger.log;

/**
 * This class will have a thread created whenever a new player joins
 */
public class PlayerHandler implements Runnable {
    private final Socket clientSocket;
    private final BlockingQueue<ThreadMessage> queue; //HERE
    private final NetworkManager networkManager; //HERE
    private final Profile profile;
    //private final ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> queue;
    private boolean running;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private Thread gameSessionManagerThread = null;
    private final Object gameSessionLock = new Object();
    private Thread mainThread = null;

    /**
     * Get the Thread that the PlayerHandler is on
     * @return the mainThread for the PlayerHandler
     */
    public synchronized Thread getThread() {
        return mainThread;
    }

    /**
     * Set the Thread of the GameSessionManager
     * @param thread the new GameSessionManagerThread
     */
    public synchronized void setGameSessionManagerThread(Thread thread) {
        synchronized (gameSessionLock) {
            // Update the gameSessionManagerThread then notify gameSessionLock
            gameSessionManagerThread = thread;
            if (thread != null) {
                // If the thread is not null, notifyAll
                gameSessionLock.notifyAll();
            }
        }
    }

    /**
     * Get the Profile for the player
     * @return the Profile object for the player
     */
    public synchronized Profile getProfile() {
        return profile;
    }

    /**
     * Method to send a friend request to another user.
     * @param message The message from the client side containing the command to send a friend request.
     */
    private synchronized void sendFriendRequest(ThreadMessage message) {

        //Check if the message contains the id of the recipient.
        if (message.getContent().containsKey("id")) {

            //If it does, then send the friend request to user associated with the id.
            int recipientID = (int) message.getContent().get("id");
            try {
                this.getProfile().getFriendsList().sendFriendRequest(recipientID);
                ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " sent friend request to " + recipientID);

            } catch (SQLException | IOException e) {
                ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " could not send friend request to " + recipientID);
            }
        } else {
            ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " unable to send friend request, incoming message did not contain recipient id.");
        }
    }

    /**
     * Method to accept a friend request from another user.
     * @param message The message from the client containing the command to accept the friend request.
     */
    private synchronized void acceptFriendRequest(ThreadMessage message) {

        //Check to make sure that the message contains the player id of the sender.
        if (message.getContent().containsKey("id")) {

            //If it does, then get the id of the sender and accept the friend request.
            int senderID = (int) message.getContent().get("id");
            try {
                this.getProfile().getFriendsList().acceptFriendRequest(senderID);
                ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " accepted friend request from " + senderID);
            } catch (IOException | SQLException e) {
                ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " could not accept friend request from " + senderID);
            }
        } else {
            ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " unable to accept friend request, client message does not contain sender id.");
        }
    }

    /**
     * Method to send a direct game request to another user.
     * @param queueMessage The thread message from the queue of type "send-game"
     */
    private synchronized void sendGameRequest(ThreadMessage queueMessage) {

        //Check to see if the message even contains the recipient id we need.
        if (queueMessage.getContent().containsKey("id")) {
            Integer recipientID = (Integer) queueMessage.getContent().get("id");

            //First, obtain the player handler of the recipient using their id.
            Thread recipientThread = ThreadRegistry.getHandler(recipientID).getThread();

            if (recipientThread != null) {

                //Check if the incoming message contains the game type needed.
                if (queueMessage.getContent().containsKey("game-type")) {

                    //Create the map for the thread message containing the message type.
                    Map<String, Object> requestMap = new HashMap<>();
                    requestMap.put("type", "game-request");
                    requestMap.put("id", recipientID);
                    requestMap.put("game-type", queueMessage.getContent().get("game-type"));

                    //Create the actual thread message for a game request out of the request map.
                    ThreadMessage requestMessage = new ThreadMessage(Thread.currentThread(), requestMap);

                    //Send the message and log it.
                    networkManager.sendMessage(recipientThread, requestMessage);
                    ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " sent game request to " + recipientID);
                } else {
                    ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " unable to send game request, incoming message lacks game type.");
                }
            } else {
                ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + "Unable to send game request, recipient thread was null.");
            }
        } else {
            ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " unable to send game request, Thread Message did not contain recipient id.");
        }
    }

    /**
     * Method to accept a game request from another user.
     * @param requestMessage The message containing the request to join a game.
     */
    private synchronized void acceptGameRequest(ThreadMessage requestMessage) {

        if (requestMessage.getContent().containsKey("id")) {

            //Get the id, and by extension the player handler of the user sending the game request.
            Integer senderID = (Integer) requestMessage.getContent().get("id");
            PlayerHandler sender = ThreadRegistry.getHandler(senderID);

            //Make sure that the request message contains the game type to be played.
            if (requestMessage.getContent().containsKey("game-type")) {

                int gameType = (int) requestMessage.getContent().get("game-type");
                ServerController.createFriendsGame(PlayerHandler.this, sender, gameType);
                ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " created friends game with " + sender.getProfile().getUsername());
            } else {
                ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + "unable to accept game request, incoming message does not containn game type.");
            }
        } else {
            ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " unable to accept game request because message does not contain a sender id.");
        }
    }

    /**
     * Method to give the client the bio of the current user.
     * @return A Thread Message containing the bio of the current user.
     */
    private ThreadMessage getBio() {

        //Get the bio from the profile.
       String bio = this.getProfile().getBio();

        //Create the hash map for the thread message
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("type", "profile-info-request");
        messageMap.put("info", "bio");
        messageMap.put("message", bio);

        //Create the thread message and return it
        ThreadMessage requestMessage = new ThreadMessage(Thread.currentThread(), messageMap);
        return requestMessage;
    }

    /**
     * Method to give the client the nickname of the current user.
     * @return A Thread Message containing the nickname of the current user.
     */
    private ThreadMessage getNickname() {

        //Get the nickname from the profile.
        String nickname = this.getProfile().getNickname();

        //Create the hash map for the thread message.
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("type", "profile-info-request");
        messageMap.put("info", "nickname");
        messageMap.put("message", nickname);

        //Create the thread message and return it.
        ThreadMessage requestMessage = new ThreadMessage(Thread.currentThread(), messageMap);
        return requestMessage;
    }

    /**
     * Method to return the username of the current user.
     * @return A Thread Message containing the username of the user.
     */
    private ThreadMessage getUsername() {

        //Get the username from the profile.
        String username = this.getProfile().getUsername();

        //Create the hashmap for the thread message.
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("type", "profile-info-request");
        messageMap.put("info", "username");
        messageMap.put("message", username);

        //Create the thread message and return it.
        ThreadMessage requestMessage = new ThreadMessage(Thread.currentThread(), messageMap);
        return requestMessage;
    }

    /**
     * Method to give the client the profile picture path of teh current user.
     * @return A Thread Message containing the profile path.
     */
    private ThreadMessage getProfilePath() {

        //Get the profile picture path from the profile.
        String profilePath = this.getProfile().getProfilePicFilePath();

        //Create the hashmap for the thread message.
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("type", "profile-info-request");
        messageMap.put("info", "profile-path");
        messageMap.put("message", profilePath);

        //Create the thread message and return it.
        ThreadMessage requestMessage = new ThreadMessage(Thread.currentThread(), messageMap);
        return requestMessage;
    }

    /**
     * Method to give the client the friends list of the current user.
     * @return A Thread Message containing the friends list of the user.
     */
    private ThreadMessage getFriends() {

        //Get the friends list from the profile.
        List<Integer> friendIDS = (List<Integer>) this.getProfile().getFriendsList().getFriends();

        //Convert the friendIDS that we get from profile into their usernames.
        List<String> friends = new ArrayList<>();
        for (Integer friendID : friendIDS) {
            String name = null;

            try {
                name = PlayerManager.getUsername(friendID);
            } catch (SQLException e) {
                ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " unable to get username for friend " + friendID);
            }

            friends.add(name);
        }

        //Create the hashmap for the thread message.
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("type", "profile-info-request");
        messageMap.put("info", "friends");
        messageMap.put("message", friends);

        //Create the thread message and return it.
        ThreadMessage requestMessage = new ThreadMessage(Thread.currentThread(), messageMap);
        return requestMessage;
    }

    /**
     * Method to give the client the friend requests list of the current user.
     * @return A Thread Message containing the friend requests list of the user.
     */
    private ThreadMessage getFriendRequests() {

        //Get the friend request list of the user.
        List<Integer> friendRequestIDS = new ArrayList<>();
        try {
            friendRequestIDS = this.getProfile().getFriendsList().getFriendRequests();
        } catch (SQLException s) {
            ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " unable to get friend requests.");
        }

        //Convert the friendRequestIDS that we get from profile into their usernames.
        List<String> friendRequests = new ArrayList<>();
        for (Integer friendRequest : friendRequestIDS) {
            String name = null;

            try {
                name = PlayerManager.getUsername(friendRequest);
            } catch (SQLException e) {
                ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " unable to get username for friend " + friendRequest);
            }

            friendRequests.add(name);
        }

        //Create the hashmap for the thread message.
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("type", "profile-info-request");
        messageMap.put("info", "friendRequests");
        messageMap.put("message", friendRequests);

        //Create the thread message and return it.
        ThreadMessage requestMessage = new ThreadMessage(Thread.currentThread(), messageMap);
        return requestMessage;
    }

    /**
     * Method to give the client the game history of the current user.
     * @return A Thread Message containing the game history of the current user.
     */
    private ThreadMessage getGameHistory() {

        //Get the game history of the user.
        List<String> gameHistory = this.getProfile().getGameHistory().getGameHistory();

        //Create the hashmap for the thread message.
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("type", "profile-info-request");
        messageMap.put("info", "gameHistory");
        messageMap.put("message", gameHistory);

        //Create the thread message and return it.
        ThreadMessage requestMessage = new ThreadMessage(Thread.currentThread(), messageMap);
        return requestMessage;
    }

    /**
     * Method to give the client the win loss ratio of the current user.
     * @return A Thread Message containing the win loss ratio of the user.
     */
    private ThreadMessage getWinLossRatio() {

        //Get the win loss ratios of the user.
        double ratio0 = this.getProfile().getPlayerRanking().getWinLossRatio(0);
        double ratio1 = this.getProfile().getPlayerRanking().getWinLossRatio(1);
        double ratio2 = this.getProfile().getPlayerRanking().getWinLossRatio(2);
        double[] ratioArray = {ratio0, ratio1, ratio2};
        List<Double> ratio = ConverterTools.convertDoubleArrayToDoubleList(ratioArray);

        //Create the hashmap for the thread message.
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("type", "profile-info-request");
        messageMap.put("info", "winLossRatio");
        messageMap.put("message", ratio);

        //Create the thread message and return it.
        ThreadMessage requestMessage = new ThreadMessage(Thread.currentThread(), messageMap);
        return requestMessage;
    }

    /**
     * Method to give the client the rating of the current user.
     * @return A Thread Message containing the rating of the user.
     */
    private ThreadMessage getRating() {

        //Get the rating of the user.
        int id = this.getProfile().getID();
        try {
            int rating0 = this.getProfile().getPlayerRanking().getRating(id, 0);
            int rating1 = this.getProfile().getPlayerRanking().getRating(id, 1);
            int rating2 = this.getProfile().getPlayerRanking().getRating(id, 2);
            int[] ratingArray = {rating0, rating1, rating2};
            List<Integer> rating = ConverterTools.convertIntArrayToList(ratingArray);

            //Create the hashmap for the thread message.
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("type", "profile-info-request");
            messageMap.put("info", "rank");
            messageMap.put("message", rating);

            //Create the thread message and return it.
            ThreadMessage requestMessage = new ThreadMessage(Thread.currentThread(), messageMap);
            return requestMessage;
        } catch (SQLException e) {
            ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " could not get ratings.");
        }
        return null;
    }

    /**
     * Method to give the client the rank of the current user.
     * @return A Thread Message containing the rank of the current user.
     */
    private ThreadMessage getRank() {

        //Get the ratings of the user
        int id = this.getProfile().getID();
        try {
            int rating0 = this.getProfile().getPlayerRanking().getRating(id, 0);
            int rating1 = this.getProfile().getPlayerRanking().getRating(id, 1);
            int rating2 = this.getProfile().getPlayerRanking().getRating(id, 2);

            //Get the ranks of the user
            String ranking0 = this.getProfile().getPlayerRanking().getRank(rating0);
            String ranking1 = this.getProfile().getPlayerRanking().getRank(rating1);
            String ranking2 = this.getProfile().getPlayerRanking().getRank(rating2);
            String[] rankArray = {ranking0, ranking1, ranking2};
            List<String> rank = ConverterTools.convertStringArrayToStringList(rankArray);

            //Create the hashmap for the thread message.
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("type", "profile-info-request");
            messageMap.put("info", "rank");
            messageMap.put("message", rank);

            //Create the thread message and return it.
            ThreadMessage requestMessage = new ThreadMessage(Thread.currentThread(), messageMap);
            return requestMessage;

        } catch (SQLException e) {
            ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " could not get rank.");
        }
        return null;
    }

    /**
     * Method to give the client the wins of the current user.
     * @return A Thread Message containing the wins of the current user.
     */
    private ThreadMessage getWins() {

        //Get the wins of the user
        try {
            int win0 = this.getProfile().getPlayerRanking().getWins(0);
            int win1 = this.getProfile().getPlayerRanking().getWins(1);
            int win2 = this.getProfile().getPlayerRanking().getWins(2);
            int[] winArray = {win0, win1, win2};
            List<Integer> win = ConverterTools.convertIntArrayToList(winArray);

            //Create the hashmap for the thread message.
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("type", "profile-info-request");
            messageMap.put("info", "wins");
            messageMap.put("message", win);

            //Create the thread message and return it.
            ThreadMessage requestMessage = new ThreadMessage(Thread.currentThread(), messageMap);
            return requestMessage;
        } catch (SQLException e) {
            ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " could not get wins.");
        }
        return null;
    }

    private ThreadMessage viewProfile(ThreadMessage message) {

        //Get the profile of the user whose information we want to put in the thread message.
        String username = (String) message.getContent().get("username");
        try {
            int id = PlayerManager.getProfileID(username);
            Profile profile = ProfileDatabaseAccess.obtainProfile(id);

            //Get all the profile data we need to be displayed
            String bio = profile.getBio();
            String nickname = profile.getNickname();
            String profilePath = profile.getProfilePicFilePath();
            List<Integer> friendIDS = profile.getFriendsList().getFriends();

            //Convert the friendIDS that we get from profile into their usernames.
            List<String> friends = new ArrayList<>();
            for (Integer friendID : friendIDS) {
                String name = PlayerManager.getUsername(friendID);
                friends.add(name);
            }

            List<String> gameHistory = profile.getGameHistory().getGameHistory();

            double ratio0 = profile.getPlayerRanking().getWinLossRatio(0);
            double ratio1 = profile.getPlayerRanking().getWinLossRatio(1);
            double ratio2 = profile.getPlayerRanking().getWinLossRatio(2);
            double[] ratio = {ratio0, ratio1, ratio2};

            int rating0 = profile.getPlayerRanking().getRating(id, 0);
            int rating1 = profile.getPlayerRanking().getRating(id, 1);
            int rating2 = profile.getPlayerRanking().getRating(id, 2);
            int[] rating = {rating0, rating1, rating2};

            String ranking0 = profile.getPlayerRanking().getRank(rating0);
            String ranking1 = profile.getPlayerRanking().getRank(rating1);
            String ranking2 = profile.getPlayerRanking().getRank(rating2);
            String[] rank = {ranking0, ranking1, ranking2};

            int win0 = profile.getPlayerRanking().getWins(0);
            int win1 = profile.getPlayerRanking().getWins(1);
            int win2 = profile.getPlayerRanking().getWins(2);
            int[] win = {win0, win1, win2};

            boolean onlineStatus = profile.getOnlineStatus();

            //Create the map for the thread message
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("type", "view-profile");
            messageMap.put("bio", bio);
            messageMap.put("nickname", nickname);
            messageMap.put("username", username);
            messageMap.put("profilePath", profilePath);
            messageMap.put("friends", friends);
            messageMap.put("gameHistory", gameHistory);
            messageMap.put("winLossRatio", ratio);
            messageMap.put("rating", rating);
            messageMap.put("rank", rank);
            messageMap.put("wins", win);
            messageMap.put("onlineStatus", onlineStatus);

            //Create the thread message and return it.
            ThreadMessage requestMessage = new ThreadMessage(Thread.currentThread(), messageMap);
            return requestMessage;

        } catch (SQLException | IOException e) {
            ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " could not obtain the ID of the user whose profile we are trying to view.");
        }
        return null;
    }


    /**
     * Disconnects the player from the server by removing them from the thread registry,
     * matchmaking queues, and notifying the game session manager if needed.
     * Also updates the player's online status and logs the disconnection.
     */
    private void disconnectPlayer() {
        //Unregister the player from the thread registry and the player list.
        ThreadRegistry.unregister(PlayerHandler.this);

        // Remove a player from any queues they may be in
        ServerController.dequeuePlayer(PlayerHandler.this);

        //Check if the player is in a game session, and if so handle the game session ending.
        if (gameSessionManagerThread != null) {
            //Create the thread message map to send to the game session manager
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("type", "disconnection");

            //Create the thread message and send it to game session manager
            ThreadMessage disconnectMessage = new ThreadMessage(mainThread, messageMap);
            networkManager.sendMessage(gameSessionManagerThread, disconnectMessage);
        }

        //Set the players current game status to null, and their online status to false.
        try {
            this.getProfile().setOnlineStatus(false);
            this.getProfile().setCurrentGame(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Log that the player has disconnected.
        ServerLogger.log("PlayerHandler: Player " + profile.getUsername() + ":" + profile.getID() + " disconnected.");
    }

    /**
     * Method to send a thread message to the client over the client socket.
     * @param message The Thread Message being sent over to the client side.
     */
    public void sendToClient(ThreadMessage message) {
        try {
            String jsonString = JsonConverter.toJson(message.getContent());
            printWriter.println(jsonString);
            printWriter.flush();
        } catch (IllegalArgumentException e) {
            ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " could not send message to client.");
        }
    }


    /**
     * Constructs a new PlayerHandler to manage communication with a connected client.
     *
     * @param clientSocket the socket used to communicate with the client
     * @param queue the message queue for handling communication between threads
     * @param profile the player's profile associated with this connection
     */
    public PlayerHandler(Socket clientSocket, BlockingQueue<ThreadMessage> queue, Profile profile) {
        this.clientSocket = clientSocket;
        //Create a dedicated queue for messages related to this player's thread.
        this.queue = queue;
        this.profile = profile;
        //Create a network manager dedicated to this player's thread.
        this.networkManager = new NetworkManager();
        this.running = true;
        // Initialize the BufferedReader and BufferedWriter
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            log("PlayerHandler: Failure to initialize PlayerHandler BufferedReader/BufferedWriter:", e.toString());
        }
    }

    /**
     * The main thread execution method for the PlayerHandler.
     * Listens to the blocking queue for messages and sends them to the client.
     * Also starts a listener thread to handle incoming messages from the client,
     * and sets the player's online status to true when the session begins.
     */
    public void run() {
        // Assign the mainThread to the thread created by ConnectionManager
        mainThread = Thread.currentThread();
        // Start the PlayerHandlerListener thread
        PlayerHandlerListener playerHandlerListener = new PlayerHandlerListener();
        Thread playerHandlerListenerThread = Thread.ofVirtual().start(playerHandlerListener);

        //Set the players online status to true
        try {
            this.getProfile().setOnlineStatus(true);
        } catch (SQLException e) {
            ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " could not set online status to true.");
        }
        while (running) {
            try {
                // Take a message from the blocking queue
                ThreadMessage threadMessage = queue.take();
                // Convert to json formatting then send it to the client
                printWriter.println(toJson(threadMessage.getContent()));
            } catch (InterruptedException e) {
                log("PlayerHandler: Failure to take message blocking queue for PlayerHandler:", e.toString());
            }
        }
    }

    /**
     * A listener class that runs in its own thread to handle incoming messages from the client.
     * It processes JSON-formatted messages and routes them to the appropriate system components,
     * such as the matchmaking queue or the game session manager.
     */
    private class PlayerHandlerListener implements Runnable {
        /**
         * The function that the thread runs, listens to the input from the client
         */
        public void run() {
            while (running) {
                try {
                    // Read the json string from the server
                    String message = bufferedReader.readLine();

                    //If message == null, then that means the player has disconnected and this thread should be terminated.
                    if (message == null) {
                        // Disconnection
                        disconnectPlayer();
                        break;
                    }

                    // Convert the json formatting and send it to the GameSessionManager
                    try {
                        Map<String, Object> jsonMap = fromJson(message);
                        // TODO: TEMPORARY, CHECK IF TYPE IS ENQUEUE
                        if (jsonMap.containsKey("type") && jsonMap.get("type").equals("enqueue")) {
                            if (jsonMap.containsKey("game-type") && jsonMap.get("game-type").equals(0)) {
                                ServerController.enqueuePlayer(PlayerHandler.this, 0);
                            } else if (jsonMap.containsKey("game-type") && jsonMap.get("game-type").equals(1)) {
                                ServerController.enqueuePlayer(PlayerHandler.this, 1);
                            } else if (jsonMap.containsKey("game-type") && jsonMap.get("game-type").equals(2)) {
                                ServerController.enqueuePlayer(PlayerHandler.this, 2);
                            } else {
                                log("PlayerHandler: Unknown game-type: " + jsonMap.get("game-type"));
                            }
                        }
                        // TODO: this threadMessage has to be like "sorted" to where it needs to go
                        ThreadMessage threadMessage = new ThreadMessage(mainThread, jsonMap);
                        // TODO: this code should only run if we are sending information to the gameSessionManager
                        routeMessage(threadMessage);
                    } catch (IllegalArgumentException e) {
                        // TODO: Should this be handled better? wait maybe send back a message?
                        log("PlayerHandler: Failure to parse message:", e.toString());
                    }
                } catch (IOException e) {
                    disconnectPlayer();
                    break;
                }
//                catch (InterruptedException e) {
//                    // just gotta say this should be passed in through some way man, maybe a function? idk doesn't matter
//                    log("PlayerHandler: Never received the thread for the GameSessionManager:", e.toString());
//                }
            }
        }
    }

    /**
     * Route the ThreadMessage to the corresponding function
     * @param threadMessage the ThreadMessage that is to be routed
     */
    public void routeMessage(ThreadMessage threadMessage) {
        // Get the sender and the content from the ThreadMessage
        Map<String, Object> content = threadMessage.getContent();
        Thread sender = threadMessage.getSender();
        Map<String, Object> forward = new HashMap<>();

        // This is nasty bro
        switch ((String) content.get("type")) {
            case "enqueue":
                if (content.containsKey("game-type") && content.get("game-type") instanceof Integer gameType
                        && (gameType > 2 || gameType < 0)) {
                    ServerController.enqueuePlayer(PlayerHandler.this, gameType);
                }
                break;
            case "send-friend-request":
                sendFriendRequest(threadMessage);
                break;
            case "accept-friend-request":
                acceptFriendRequest(threadMessage);
                break;
            case "send-game-request":
                sendGameRequest(threadMessage);
                break;
            case "game-request":
                acceptGameRequest(threadMessage);
                break;
            case "game":
            case "chat":
                // Wait for there to be a GameSessionManager
//                synchronized (gameSessionLock) {
//                    if (gameSessionManagerThread == null)
//                        gameSessionLock.wait();
//                }
                networkManager.sendMessage(gameSessionManagerThread, threadMessage);
                break;
            case "profile-info-request":

                //Check the profile info being requested
               switch ((String) content.get("info")) {

                   case "bio":
                       sendToClient(getBio());
                   case "nickname":
                       sendToClient(getNickname());
                   case "username":
                       sendToClient(getUsername());
                   case "profilePath":
                       sendToClient(getProfilePath());
                   case "friends":
                       sendToClient(getFriends());
                   case "friendRequests":
                       sendToClient(getFriendRequests());
                   case "gameHistory":
                       sendToClient(getGameHistory());
                   case "winLossRatio":
                       sendToClient(getWinLossRatio());
                   case "rating":
                       sendToClient(getRating());
                   case "rank":
                       sendToClient(getRank());
                   case "wins":
                       sendToClient(getWins());
               }
            case "view-profile":
                sendToClient(viewProfile(threadMessage));
            default:
                log("PlayerHandler: Message not recognized: " + threadMessage.getContent());
        }
    }
}
