package server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class will have a thread created whenever a new player joins
 */
public class PlayerHandler {
    private final Socket clientSocket;
    private final ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> queue;
    private boolean running;

    /**
     *
     * @param clientSocket the Socket that the client is connected on
     * @param queue the main message cue object that will be used to communicate between threads.
     */
    public PlayerHandler(Socket clientSocket, ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> queue) {
        this.clientSocket = clientSocket;
        this.queue = queue;
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
