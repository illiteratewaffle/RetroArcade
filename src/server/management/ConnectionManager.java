package server.management;
import server.player.PlayerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

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

            System.out.println("Connection Manager: Listening on port " + port);
            //Start a loop that constantly listens for clients to accept.
            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection Manager: Accepted connection from " + clientSocket.getRemoteSocketAddress());

                //Create a blocking queue and player handler to handle the player connection on the server side.
                LinkedBlockingQueue queue = new LinkedBlockingQueue();
                PlayerHandler playerHandler = new PlayerHandler(clientSocket, queue);

                //Create a thread for the player and start it.
                Thread playerThread = Thread.ofVirtual().start(playerHandler);

                //Store the players handlers thread and blocking queue on the thread registry.
            }
        } catch (IOException e) {

            //Throw a message to the console and print a stack trace if there is an error.
            System.err.println("Connection Manager encountered an error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
