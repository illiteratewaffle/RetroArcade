package player;

import management.ThreadMessage;
import management.NetworkManager;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import static management.ServerLogger.log;

/**
 * This class will have a thread created whenever a new player joins
 */
public class PlayerHandler implements Runnable {
    private final Socket clientSocket;
    private final BlockingQueue<ThreadMessage> queue; //HERE
    private final NetworkManager networkManager; //HERE
    //private final ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> queue;
    private boolean running;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private Thread gameSessionManagerThread = null;
    private final Object gameSessionLock = new Object();
    private Thread mainThread = null;

    /**
     *
     * @param clientSocket the Socket that the client is connected on
     * /@param queue the main message cue object that will be used to communicate between threads.
     */
    public PlayerHandler(Socket clientSocket, BlockingQueue<ThreadMessage> queue) {
        this.clientSocket = clientSocket;
        //Create a dedicated queue for messages related to this player's thread.
        this.queue = queue;
        //Create a network manager dedicated to this player's thread.
        this.networkManager = new NetworkManager();
        this.running = true;
        // Initialize the BufferedReader and BufferedWriter
        try {
            this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e){
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
                // Grab the thread GameSessionManager is on for communicating back to it
                synchronized (gameSessionLock) {
                    if (gameSessionManagerThread == null) {
                        gameSessionManagerThread = threadMessage.getSender();
                        gameSessionLock.notifyAll();
                    }
                }
                // Convert to json formatting then send it to the client
                // THERE IS NO JSON TO STRING CONVERSION CLASS CREATED YET
                printWriter.println(threadMessage.getContent());
            } catch (InterruptedException e) {
                log("Failure to take message blocking queue for PlayerHandler:", e.toString());
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
                    // Convert the json formatting and send it to the GameSessionManager
                    // THERE IS NO JSON TO STRING CONVERSION CLASS CREATED YET
                    ThreadMessage threadMessage = new ThreadMessage(mainThread, message);
                    // Make sure that we have the GameSessionManagerThread before attempting to send information to it
                    synchronized (gameSessionLock) {
                        if (gameSessionManagerThread == null)
                            gameSessionLock.wait();
                    }
                    // Relay the message to the GameSessionManager
                    networkManager.sendMessage(gameSessionManagerThread, threadMessage);
                } catch (IOException e) {
                    log("Failure to read a message from the client using the BufferedReader:", e.toString());
                } catch (InterruptedException e) {
                    // just gotta say this should be passed in through some way man, maybe a function? idk doesn't matter
                    log("Never received the thread for the GameSessionManager:", e.toString());
                }
            }
        }
    }
}

/*
List of things to remember yuh:
 - Should use the virtual thread builder to name the thread and specify what should happen if a thread fails (logging it)
 - We have no proper system for the playerHandler knowing what thread the GameSessionManager is on
    - Therefore, I am simply waiting until I get a message from the blockingQueue to copy their thread.
    - However, this system to put it simply, sucks fucking ass
 - Alright why does PlayerHandler create a LinkedBlockingQueue? How does it share its own blocking queue to the main queue? bruh.
    - I know see but think it could be improved to be more straightforward
 - ClientHandler seems to act as if it is on the server side and is trying to be a PlayerHandler and ClientHandler? very confused.
 - Why do we not have a static class responsible for converting json to classes and vice versa?
 */
