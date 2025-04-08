package session;

import management.ServerLogger;
import matchmaking.Matchmaking;
import player.PlayerHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GameCreator implements Runnable {

    //The concurrent linked queue which stores the queue for players waiting to join a game.
    private final Matchmaking gameQueue = new Matchmaking();

    //Concurrent hash map that stores a list of all currently active game sessions
    private final ConcurrentHashMap<Thread, GameSessionManager> activeSessions = new ConcurrentHashMap<>();

    public GameCreator() {
    }

    public void enqueuePlayer(PlayerHandler player, int gameType) throws SQLException {
        ServerLogger.log("GameCreator: Enqueued player " + player.getProfile().getUsername() + ":" + player.getProfile().getID());
        gameQueue.enqueue(gameType, player);
        player.setInQueue(true);
    }

    public void dequeuePlayer(PlayerHandler player) {
        if (gameQueue.isInQueue(player)) {
            gameQueue.dequeue(player);
            ServerLogger.log("GameCreator: Dequeued player " + player.getProfile().getUsername() + ":" + player.getProfile().getID());
        }
    }

    public void createSession(PlayerHandler player1, PlayerHandler player2, int gameType) {
        //Create the game session manager for the game session being created
        GameSessionManager session = new GameSessionManager(player1, player2, gameType);

        //Start the game session manager thread
        Thread sessionThread = Thread.startVirtualThread(session);

        //Assign the game session manager thread to both the game session hashmap list, and also onto the thread registry
        activeSessions.put(sessionThread, session);
        //ThreadRegistry.register(sessionThread, session.getMessageQueue()); This will need to be fixed after talking with martin.

        //Update the gameSessionManagerThreads within the player handlers
        ServerLogger.log("GameCreator: Created game session manager for " + player1.getProfile().getUsername()
                + ":" + player1.getProfile().getID() + " and " + player2.getProfile().getUsername() + ":"
                + player2.getProfile().getID());
    }

    public void endSession(Thread sessionThread) {
        activeSessions.remove(sessionThread);
    }

    public GameSessionManager getSession(Thread sessionThread) {
        return activeSessions.get(sessionThread);
    }

    public void startGameFromQueue(int gameType) {
        List<PlayerHandler> players = gameQueue.matchOpponents(gameType);
        if (players.size() >= 2) {
            createSession(players.get(0), players.get(1), gameType);

            //Set the inQueue status of both players to be false.
            players.get(0).setInQueue(false);
            players.get(1).setInQueue(false);
        } else {
            ServerLogger.log("GameCreator: Unable to retrieve a list of two players from Matchmaking.");
        }
    }

    @Override
    public void run() {
        while (true) {
            //Logic to check the size of each queue, and if there are two or more players in a queue then to create a game for them.
            if (gameQueue.getQueueSize(0) >= 2) { //Check the tic tac toe queue size
                startGameFromQueue(0);
            } else if (gameQueue.getQueueSize(1) >= 2) { //Check the Conenct 4 queue size
                startGameFromQueue(1);
            } else if (gameQueue.getQueueSize(2) >= 2) { //Check the checkers queue size
                startGameFromQueue(2);
            }
        }
    }
}
