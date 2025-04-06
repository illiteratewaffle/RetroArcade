package session;

import management.ThreadRegistry;
import player.PlayerHandler;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameCreator implements Runnable {

    //Concurrent hash map that stores a list of all currently active game sessions
    private final ConcurrentHashMap<Thread, GameSessionManager> activeSessions = new ConcurrentHashMap<>();
    ConcurrentLinkedQueue<PlayerHandler> gameQueue;

    public GameCreator(ConcurrentLinkedQueue<PlayerHandler> gameQueue) {
        this.gameQueue = gameQueue;
    }

    public void createSession(PlayerHandler player1, PlayerHandler player2) {

        //Create the game session manager for the game session being created
        GameSessionManager session = new GameSessionManager(player1, player2, "tictactoe");
        //Start the game session manager thread
        Thread sessionThread = Thread.startVirtualThread(session);

        //Assign the game session manager thread to both the game session hashmap list, and also onto the thread registry
        activeSessions.put(sessionThread, session);
        //ThreadRegistry.register(sessionThread, session.getMessageQueue()); This will need to be fixed after talking with martin.

        //Update the gameSessionManagerThreads within the player handlers
        player1.setGameSessionManagerThread(sessionThread);
        player2.setGameSessionManagerThread(sessionThread);
        System.out.println("Game session made");
    }

    public void endSession(Thread sessionThread) {
        activeSessions.remove(sessionThread);
    }

    public GameSessionManager getSession(Thread sessionThread) {
        return activeSessions.get(sessionThread);
    }

    public void startGameFromQueue() {
        PlayerHandler player1 = gameQueue.poll();
        PlayerHandler player2 = gameQueue.poll();

        if ((player1 != null) && (player2 != null)) {
            createSession(player1, player2);
        }
    }

    @Override
    public void run() {
        while (true) {
            startGameFromQueue();
        }
    }
}
