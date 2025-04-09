package player;

import AuthenticationAndProfile.Profile;
import GameLogic_Client.Ivec2;
import com.almasb.fxgl.net.Server;
import management.*;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
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
        if (message.getContent().containsKey("ID")) {

            //If it does, then send the friend request to user associated with the id.
            int recipientID = (int) message.getContent().get("ID");
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
        if (message.getContent().containsKey("ID")) {

            //If it does, then get the id of the sender and accept the friend request.
            int senderID = (int) message.getContent().get("ID");
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
        if (queueMessage.getContent().containsKey("ID")) {
            Integer recipientID = (Integer) queueMessage.getContent().get("ID");

            //First, obtain the player handler of the recipient using their id.
            Thread recipientThread = ThreadRegistry.getHandler(recipientID).getThread();

            if (recipientThread != null) {

                //Check if the incoming message contains the game type needed.
                if (queueMessage.getContent().containsKey("game-type")) {

                    //Create the map for the thread message containing the message type.
                    Map<String, Object> requestMap = new HashMap<>();
                    requestMap.put("type", "game-request");
                    requestMap.put("ID", recipientID);
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

        if (requestMessage.getContent().containsKey("ID")) {

            //Get the id, and by extension the player handler of the user sending the game request.
            Integer senderID = (Integer) requestMessage.getContent().get("ID");
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
            default:
                log("PlayerHandler: Message not recognized: " + threadMessage.getContent());
        }
    }
}
