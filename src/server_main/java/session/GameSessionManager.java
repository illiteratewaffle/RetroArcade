package session;

import GameLogic_Client.IBoardGameController;
import management.ThreadMessage;
import management.ThreadRegistry;
import player.PlayerHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static management.ServerLogger.log;

public class GameSessionManager implements Runnable {
    private final PlayerHandler player1;
    private final PlayerHandler player2;
    private final String gameType;
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
        if (gameType == 0) this.gameType = "tictactoe";
        else if (gameType == 1) this.gameType = "connect4";
        else this.gameType = "checkers";
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
     * @param player The player that disconnected.
     */
    private void handleDisconnection(PlayerHandler player) {

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
