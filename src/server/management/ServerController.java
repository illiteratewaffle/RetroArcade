package server.management;

import server.player.PlayerHandler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import server.management.ThreadMessage;

public class ServerController {

    //The thread the server controller is on.
    Thread thread = Thread.currentThread();

    //This is the message queue for incoming messages to the server controller.
    LinkedBlockingQueue messageQueue;

    //The queue for players waiting to join a game
    Queue<PlayerHandler> gameQueue;

    public ServerController() {
        messageQueue = new LinkedBlockingQueue();
        gameQueue = new ConcurrentLinkedQueue<PlayerHandler>();
        ThreadRegistry.register(thread, messageQueue);
    }

    /**
     * Starts the server by instantiating a ConnectionManager.
     */
    public void startServer(int port) {
        ConnectionManager connectionManager = new ConnectionManager(this, port);
        Thread.startVirtualThread(connectionManager);
    }

}

