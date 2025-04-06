package server.management;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

                // Authenticate the player before accepting them to the server
                AuthenticateClient authenticateClient = new AuthenticateClient(clientSocket);
                Thread.ofVirtual().start(authenticateClient);

                //Store the players handlers thread and blocking queue on the thread registry.
            }
        } catch (IOException e) {
            //Throw a message to the console and print a stack trace if there is an error.
            log("Connection Manager encountered an error: " + e.getMessage());
        }
    }
}
