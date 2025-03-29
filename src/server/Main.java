package server;

import server.management.ServerController;

public class Main {
    /**
     * Main Method to start the server
     */
    public static void main(String[] args) {
        ServerController controller = new ServerController();
        int port = 5050;
        controller.startServer(port);
    }
}
