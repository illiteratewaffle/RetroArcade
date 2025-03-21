package server.player;

import server.management.ThreadMessage;
import server.management.NetworkManager;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class will have a thread created whenever a new player joins
 */
public class PlayerHandler {
    private final Socket clientSocket;
    private final BlockingQueue<ThreadMessage> messages; //HERE
    private final NetworkManager networkManager; //HERE
    //private final ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> queue;
    private boolean running;

    /**
     *
     * @param clientSocket the Socket that the client is connected on
     * @param queue the main message cue object that will be used to communicate between threads.
     */
    public PlayerHandler(Socket clientSocket) { // ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> queue
        this.clientSocket = clientSocket;
        //Create a dedicated queue for messages related to this player's thread.
        this.messages = new LinkedBlockingQueue<>(); //HERE
        //Create a network manager dedicated to this player's thread.
        this.networkManager = new NetworkManager(); //HERE
        this.running = true;
    }

    /**
     * The function that the thread runs, the output
     */
    public void run() {
        // Start the PlayerHandlerListener thread
        PlayerHandlerListener playerHandlerListener = new PlayerHandlerListener();
        Thread playerHandlerListenerThread = Thread.ofVirtual().start(playerHandlerListener);

        while (running) {
            // Output stuff goes here
        }
    }

    /**
     * This is the class that will run listen to client messages
     */
    private class PlayerHandlerListener implements Runnable {
        /**
         * The function that the thread runs, the input
         */
        public void run() {
            while (running) {
                // Input stuff goes here
            }
        }
    }
}
