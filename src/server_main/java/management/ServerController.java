package management;

import player.PlayerHandler;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import management.ThreadMessage;
import session.GameCreator;

public class ServerController {

    //The game creator that the server controller will use to create game sessions.
    private static final GameCreator gameCreator = new GameCreator();

    /**
     * Starts the server by instantiating a ConnectionManager.
     */
    public static void startServer(int port) {
        ServerLogger.startServerLogger();
        ConnectionManager connectionManager = new ConnectionManager(port);

        Thread.startVirtualThread(connectionManager);
        Thread.startVirtualThread(gameCreator);
        ServerLogger.log("Started connection manager and game creator.");
    }



    public static void enqueuePlayer(PlayerHandler player) {
        gameCreator.enqueuePlayer(player);
    }
}
