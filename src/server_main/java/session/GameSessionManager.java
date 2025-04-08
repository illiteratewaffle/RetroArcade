package session;

import GameLogic_Client.IBoardGameController;
import management.ServerController;
import management.ServerLogger;
import management.ThreadMessage;
import management.ThreadRegistry;
import player.PlayerHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static management.ServerLogger.log;

public class GameSessionManager implements Runnable {
    private final PlayerHandler player1;
    private final PlayerHandler player2;
    private final Integer gameType;
    private final IBoardGameController gameController;

    /**
     * The constructor for the GameSessionManager
     *
     * @param player1  The first player's PlayerHandler
     * @param player2  The second player's PlayerHandler
     * @param gameType
     */
    public GameSessionManager(PlayerHandler player1, PlayerHandler player2, int gameType) {
        this.player1 = player1;
        this.player2 = player2;
        // yeah, bad practice what about it
        this.gameType = gameType;
        this.gameController = getController(gameType);
    }

    /**
     * Get the corresponding controller for the game being played
     * @param gameType the String of the game type that is going to be created
     * @return the subclass of the IGameBoardController
     */
    private IBoardGameController getController(int gameType) {
        switch (gameType) {
            case 0:
                return new GameLogic_Client.TicTacToe.TTTGameController();
            case 1:
                GameLogic_Client.Connect4.C4Controller c4 = new GameLogic_Client.Connect4.C4Controller();
                c4.start(); // Ensure Connect4 game is initialized
                return c4;
            case 2:
                return new GameLogic_Client.Checkers.CheckersController();
            default:
                throw new IllegalArgumentException("GameSessionManager: Unknown game type: " + gameType);
        }
    }

    /**
     * The method to handle win conditions and stuff when a player disconnects.
     * @param message The Thread Message saying a disconnection has occurred.
     */
    private void handleDisconnection(ThreadMessage message) {

        //Find the thread of the player that was disconnected.
        Thread sender = message.getSender();

        //Check which player was the one that got disconnected.
        if (sender == player1.getThread()) {

            player2.setGameSessionManagerThread(null);
            handleGameEnd(player2, player1);
        } else if (sender == player2.getThread()) {

            player1.setGameSessionManagerThread(null);
            handleGameEnd(player1, player2);
        }

    }

    /**
     * Method that handles the end of a game session.
     * @param winner The player that won the game.
     */
    private void handleGameEnd(PlayerHandler winner, PlayerHandler loser) {
        try {
            //Update the players profiles based on the result of the game.
            winner.getProfile().getPlayerRanking().endOfMatchMethod(gameType, 1);
            loser.getProfile().getPlayerRanking().endOfMatchMethod(gameType, 0);
        } catch (SQLException e) {

            //If there is an error, log it.
            ServerLogger.log("GameSessionManager: Error while logging the winners and loser of a game session.", e);
        }

        //Once the game session has concluded, call the methods to end everything and log it.
        ServerController.endGameSession(Thread.currentThread());
        ServerLogger.log("GameSessionManager: Game session ended.");
    }

    /**
     * The method that runs on the separate Thread
     */
    public void run() {
        // Register the GameSessionManager on the ThreadRegistry
        Thread currentThread = Thread.currentThread();
        BlockingQueue<ThreadMessage> myQueue = new LinkedBlockingQueue<>();
        ThreadRegistry.register(currentThread, myQueue);

        // Tell the players the thread is ready
        player1.setGameSessionManagerThread(Thread.currentThread());
        player2.setGameSessionManagerThread(Thread.currentThread());

        log("GameSessionManager: Created " + gameType + " with players " + player1.getProfile().getUsername() +
                ":" + player1.getProfile().getID() + " and " + player2.getProfile().getUsername() + ":" +
                player2.getProfile().getID() + ".");

        // While the game is ongoing, could replace with a running boolean?
        while (gameController.getGameOngoing()) {
            try {
                ThreadMessage threadMessage = myQueue.take();
                if (threadMessage.getContent().containsKey("type") && threadMessage.getContent().get("type").equals("chat")) {
                    handleChatMessage(threadMessage);
                }
            } catch (InterruptedException e) {
                log("GameSessionManager: Failed to take from own BlockingQueue.");
                // TODO: shutdown game?
            }
        }
    }

    /**
     *
     * @param threadMessage
     */
    private void handleChatMessage(ThreadMessage threadMessage) {
        Map<String, Object> content = threadMessage.getContent();
        Thread sender = threadMessage.getSender();
        // Create a response
        HashMap<String, Object> forward = new HashMap<>();
        // Make a new message of the type "chat"
        forward.put("type", "chat");
        // Put the other users message in the hashmap
        forward.put("message", content.get("message"));
        // Send to the opposite player
        if (sender == player1.getThread()) {
            forward.put("sender", player1.getProfile().getUsername());
            ThreadRegistry.getQueue(player2.getThread()).add(new ThreadMessage(Thread.currentThread(), forward));
        } else if (sender == player2.getThread()) {
            forward.put("sender", player2.getProfile().getUsername());
            ThreadRegistry.getQueue(player1.getThread()).add(new ThreadMessage(Thread.currentThread(), forward));
        } else {
            log("GameSessionManager: Chat message sender not recognized.");
        }
    }
}
