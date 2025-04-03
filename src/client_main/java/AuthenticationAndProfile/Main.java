package client_main.java.AuthenticationAndProfile;

import server.management.ServerController;

import java.util.Scanner;

public class Main {
    /**
     * Main Method to start the server
     */
    public static void main(String[] args) {
        ServerController controller = new ServerController();
        int port = 5050;
        controller.startServer(port);
        // Create an infinite while loop so the server does not exit
        // TODO: the main thread should be used for a proper task
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            if (sc.nextLine().equalsIgnoreCase("exit"))
                running = false;
        }
    }
}
