package server.management;
import server.player.Player;
import server.player.PlayerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static server.management.ServerLogger.log;

public class ConnectionManager implements Runnable {
    private final int port;
    private final ServerController serverController;

    public ConnectionManager(ServerController serverController, int port) {

        this.serverController = serverController;
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            log("Connection Manager: Listening on port " + port);
            //Start a loop that constantly listens for clients to accept.
            while (true) {

                Socket clientSocket = serverSocket.accept();
                log("Connection Manager: Accepted connection from " + clientSocket.getRemoteSocketAddress());

                //Create a blocking queue and player handler to handle the player connection on the server side.
                BlockingQueue<ThreadMessage> queue = new LinkedBlockingQueue<>();
                PlayerHandler playerHandler = new PlayerHandler(clientSocket, queue);
                //Create a thread for the player and start it.
                Thread playerThread = Thread.ofVirtual().start(playerHandler);
                // Add the new player the ThreadRegistry
                ThreadRegistry.threadRegistry.put(playerThread, queue);
                // Add the new player to the playerList
                synchronized (ThreadRegistry.playerList) {
                    ThreadRegistry.playerList.add(new Player(playerThread, playerHandler));
                    // log("The new player is on Thread", playerThread.threadId());
                    ThreadRegistry.playerList.notifyAll();
                }

                //Store the players handlers thread and blocking queue on the thread registry.
            }
        } catch (IOException e) {
            //Throw a message to the console and print a stack trace if there is an error.
            log("Connection Manager encountered an error: " + e.getMessage());
        }
    }
}
