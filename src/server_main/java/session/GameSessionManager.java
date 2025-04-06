package session;

import player.PlayerHandler;
import management.ThreadMessage;
import management.ThreadRegistry;
import GameLogic_Client.IBoardGameController;
import GameLogic_Client.Ivec2;


import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.IntBinaryOperator;

import GameLogic_Client.IBoardGameController;
/**
 *
 */


import static management.ServerLogger.log;

public class GameSessionManager implements Runnable{
    private final PlayerHandler player1;
    private final PlayerHandler player2;

    private final IBoardGameController gameController;
    private final ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> queue = ThreadRegistry.threadRegistry;


    /**
     * Constructs the GameSessionManager with the two players and a queue
     * @param player1 First Player
     * @param player2 Second Player
     */
    public GameSessionManager(PlayerHandler player1, PlayerHandler player2, String gameType){
        this.player1 = player1;
        this.player2 = player2;
        this.gameController = getController(gameType);
    }
// GameSessionManager manager = new GameSessionManager(player1, player2, getController(gameType));
    public IBoardGameController getController(String gameType){
        switch (gameType.toLowerCase()) {
            case "tictactoe":
                return new GameLogic_Client.TicTacToe.TTTGameController();
            case "connect4":
                GameLogic_Client.Connect4.C4Controller c4 = new GameLogic_Client.Connect4.C4Controller();
                c4.start(); // Ensure Connect4 game is initialized
                return c4;
            case "checkers":
                return new GameLogic_Client.Checkers.CheckersController();
            default:
                throw new IllegalArgumentException("Unknown game type: " + gameType);
        }
    } 
    /**
     * The game continues in a loop till it ends.
     * It waits for the player inputs, processes the moves and updates the game state.
     */
    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        queue.put(currentThread, new LinkedBlockingQueue<>());

        log("GameSessionManager created");

        // Send initial game link info to players
        HashMap<String, Object> gameLink = new HashMap<>();
        gameLink.put("type", "game-link");
        queue.get(player1.getThread()).add(new ThreadMessage(currentThread, gameLink));
        queue.get(player2.getThread()).add(new ThreadMessage(currentThread, gameLink));

        while (gameController.GetGameOngoing()) {
            Player currentPlayer = getCurrentPlayer();
            Thread currentPlayerThread = currentPlayer.getThread();

            try {
                ThreadMessage msg = queue.get(currentThread).take();

                if (msg.getSender() == currentPlayerThread) {
                    String inputStr = (String) msg.getContent().get("move");  // Expecting key "move"
                    Ivec2 move = parseInput(inputStr);

                    gameController.ReceiveInput(move);
                    broadcastGameState();

                    if (!gameController.GetGameOngoing()) {
                        handleGameEnd();
                    }
                } else {
                    log("Received input from wrong player. Ignoring.");
                }

            } catch (InterruptedException e) {
                log("GameSessionManager interrupted while waiting for input:", e.toString());
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                log("GameSessionManager encountered an error:", e.toString());
            }
        }
    }

    private void broadcastGameState() {
        String state = formatGameState();
        HashMap<String, Object> update = new HashMap<>();
        update.put("type", "game-state");
        update.put("state", state);

        queue.get(player1.getThread()).add(new ThreadMessage(Thread.currentThread(), update));
        queue.get(player2.getThread()).add(new ThreadMessage(Thread.currentThread(), update));
    }
    
    /**
     * Determines the current player based on the game state.
     * @return The player whose turn it is.
     */
    private PlayerHandler getCurrentPlayer(){
        int currentPlayerIndex = gameController.getCurrentPlayer(); //Get the player's index

        //Retunr the corresponding player
        if (currentPlayerIndex == 0){
            return player1;
        }
        return player2;
    }


    /**
     * Waits for input from the specified player
     *  @param player The player to recieve input from
     *  @return The received input message
     */
    private ThreadMessage recieveInputFromPlayer(PlayerHandler player){
        try {
            return player.getMessageQueue.take(); 
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /**
     * Sends the current state of the game to the players
     */
    private void GameStateToPlayers(){
        ThreadMessage gameState = new ThreadMessage(Thread.currentThread(), formatGameState()); //Sender thread reference

        sendToPlayer(player1, gameState); //Send state to players
        sendToPlayer(player2, gameState);
    }

    /**
     * Formats the game state in a string format
     * @return The string format of current player, winner, and game status
     */
    private String formatGameState() {
        return "Current Player: " + gameController.getCurrentPlayer() +
                ", Winner: " + java.util.Arrays.toString(gameController.getWinner()) +
                ", Game Ongoing: " + gameController.getGameOngoing();
    }

    /**
     *  Sends a message to a specific player
     * @param player the player send the message to
     * @param message The message to send
     */
    private void sendToPlayer(PlayerHandler player, ThreadMessage message) {
        String state = formatGameState();
        ThreadMessage gameStateMsg = new ThreadMessage(Thread.currentThread(), state);
        player1.getQueue().offer(gameStateMsg);
        player2.getQueue().offer(gameStateMsg);
        
    }

    /**
     * Ends the game if necessary and notifies both players
     */
    private void handleGameEnd(){
        int[] winner = gameController.GetWinner();
        String message = winner.length == 0 ? "Game ended in a tie!" : "Player " + winner[0] + " wins!";

        HashMap<String, Object> gameEnd = new HashMap<>();
        gameEnd.put("type", "game-over");
        gameEnd.put("result", message);

        queue.get(player1.getThread()).add(new ThreadMessage(Thread.currentThread(), gameEnd));
        queue.get(player2.getThread()).add(new ThreadMessage(Thread.currentThread(), gameEnd));
        //TODO: update profiles 
        //End session
    }

    private Ivec2 parseInput(String inputStr) {
        String[] parts = inputStr.split(",");
        int x = Integer.parseInt(parts[0].trim());
        int y = Integer.parseInt(parts[1].trim());
        return new Ivec2(x, y);
    }

}