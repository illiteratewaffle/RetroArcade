package player;

import AuthenticationAndProfile.Profile;
import management.ServerLogger;
import management.ThreadMessage;
import management.NetworkManager;
import management.ThreadRegistry;

import java.io.*;
import java.net.Socket;
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
            gameSessionLock.notifyAll();
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
            log("Failure to initialize PlayerHandler BufferedReader/BufferedWriter:", e.toString());
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
        //
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
                        running = false;

                        //Unregister the player from the thread registry and the player list.
                        ThreadRegistry.unregister(PlayerHandler.this);

                        //Log that the player has disconnected.
                        ServerLogger.log("Player " + profile.getUsername() + " Disconnected.");

                        //Check if the player is in a game session, and if so handle the game session ending.
                        if (gameSessionManagerThread != null) {
                            //Implement disconnection handling for when they're in a game session.
                        }

                        break;
                    }

                    // Convert the json formatting and send it to the GameSessionManager
                    try {
                        ThreadMessage threadMessage = new ThreadMessage(mainThread, fromJson(message));
                        // Make sure that we have the GameSessionManagerThread before attempting to send information to it
                        synchronized (gameSessionLock) {
                            if (gameSessionManagerThread == null)
                                gameSessionLock.wait();
                        }
                        // Relay the message to the GameSessionManager
                        networkManager.sendMessage(gameSessionManagerThread, threadMessage);
                    } catch (IllegalArgumentException e) {
                        // TODO: Should this be handled better? wait maybe send back a message?
                        log("PlayerHandler: Failure to parse message:", e.toString());
                    }
                } catch (IOException e) {
                    log("PlayerHandler: Failure to read a message from the client using the BufferedReader:", e.toString());
                } catch (InterruptedException e) {
                    // just gotta say this should be passed in through some way man, maybe a function? idk doesn't matter
                    log("PlayerHandler: Never received the thread for the GameSessionManager:", e.toString());
                }
            }
        }
    }
}
