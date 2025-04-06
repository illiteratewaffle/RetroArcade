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

    //The queue for players waiting to join a game
    Queue<PlayerHandler> gameQueue;

    //The game creator that the server controller will use to create game sessions.
    GameCreator gameCreator;

    public ServerController() {
        messageQueue = new LinkedBlockingQueue<>();
        gameQueue = new ConcurrentLinkedQueue<PlayerHandler>();
        gameCreator = new GameCreator(gameQueue);
        Thread.ofVirtual().start(gameCreator);

        ThreadRegistry.register(thread, messageQueue);
    }

    /**
     * Starts the server by instantiating a ConnectionManager.
     */
    public void startServer(int port) {
        ServerLogger.startServerLogger();
        ConnectionManager connectionManager = new ConnectionManager(this, port);
        Thread.startVirtualThread(connectionManager);
    }

    public void enqueuePlayer(PlayerHandler player) {
        gameQueue.add(player);
    }

    public void startGameFromQueue() {
        PlayerHandler player1 = gameQueue.poll();
        PlayerHandler player2 = gameQueue.poll();

        if ((player1 != null) && (player2 != null)) {
            gameCreator.createSession(player1, player2);
        }
    }

}
