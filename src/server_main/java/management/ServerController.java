package management;

import player.PlayerHandler;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import management.ThreadMessage;
import session.GameCreator;

public class ServerController {

    //The thread the server controller is on.
    Thread thread = Thread.currentThread();

    //This is the message queue for incoming messages to the server controller.
    BlockingQueue<ThreadMessage> messageQueue;

    //The game creator that the server controller will use to create game sessions.
    GameCreator gameCreator;

    public ServerController() {
        messageQueue = new LinkedBlockingQueue<>();
        gameCreator = new GameCreator();

        ThreadRegistry.register(thread, messageQueue);
    }

    /**
     * Starts the server by instantiating a ConnectionManager.
     */
    public void startServer(int port) {
        ServerLogger.startServerLogger();
        ConnectionManager connectionManager = new ConnectionManager(this, port);

        Thread.startVirtualThread(connectionManager);
        Thread.startVirtualThread(gameCreator);
        ServerLogger.log("Started connection manager and game creator.");
    }

    public void enqueuePlayer(PlayerHandler player) {
        gameCreator.enqueuePlayer(player);
    }
}
