package player;

import AuthenticationAndProfile.Profile;
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
     *
     * @param recipientID
     */
    public synchronized void sendFriendRequest(Integer recipientID) {
        try {
            this.getProfile().getFriendsList().sendFriendRequest(recipientID);
        } catch (SQLException | IOException e) {
            ServerLogger.log("PlayerHandler: " + this.getProfile().getUsername() + " could not send friend request to " + recipientID);
        }
    }

    /**
     * This function disconnects the player from the server.
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
     * @param clientSocket the Socket that the client is connected on
     *                     /@param queue the main message cue object that will be used to communicate between threads.
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
     * The function that the thread runs, listens to the blocking queue to send information to the client
     */
    public void run() {
        // Assign the mainThread to the thread created by ConnectionManager
        mainThread = Thread.currentThread();
        // Start the PlayerHandlerListener thread
        PlayerHandlerListener playerHandlerListener = new PlayerHandlerListener();
        Thread playerHandlerListenerThread = Thread.ofVirtual().start(playerHandlerListener);
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
     * This is the class that will listen to client messages
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
                        // Make sure that we have the GameSessionManagerThread before attempting to send information to it
                        if (jsonMap.containsKey("type") && (jsonMap.get("type").equals("chat") || jsonMap.get("type").equals("game"))) {
                            synchronized (gameSessionLock) {
                                if (gameSessionManagerThread == null)
                                    gameSessionLock.wait();
                            }
                            // Relay the message to the GameSessionManager
                            // System.out.println(jsonMap);
                            networkManager.sendMessage(gameSessionManagerThread, threadMessage);
                        }
                    } catch (IllegalArgumentException e) {
                        // TODO: Should this be handled better? wait maybe send back a message?
                        log("PlayerHandler: Failure to parse message:", e.toString());
                    }
                } catch (IOException e) {
                    disconnectPlayer();
                    break;
                } catch (InterruptedException e) {
                    // just gotta say this should be passed in through some way man, maybe a function? idk doesn't matter
                    log("PlayerHandler: Never received the thread for the GameSessionManager:", e.toString());
                }
            }
        }
    }
}
