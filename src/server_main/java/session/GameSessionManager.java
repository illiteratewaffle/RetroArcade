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
    private IBoardGameController getController(String gameType){
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
        BlockingQueue<ThreadMessage> myQueue = new LinkedBlockingQueue<>();
        ThreadRegistry.register(currentThread, myQueue);

        log("GameSessionManager created");

        // Send initial game link info to players
        player1.setGameSessionManagerThread(Thread.currentThread());

        player2.setGameSessionManagerThread(Thread.currentThread());
        while (gameController.getGameOngoing()) {

            try {
                ThreadMessage msg = myQueue.take();
                Thread sender = msg.getSender();
                Object typeObj = msg.getContent().get("type");
                HashMap<String, Object> content = (HashMap<String, Object>) msg.getContent();


                if (typeObj == null) {
                    log("Message missing type. Ignored.");
                    continue;
                }

                String type = typeObj.toString();

                switch (type) {
                    case "move":
                        PlayerHandler currentPlayer = getCurrentPlayer();
                        Thread currentPlayerThread = currentPlayer.getThread();
                        if (sender == currentPlayer.getThread()) {
                            String inputStr = (String) msg.getContent().get("move");  // Expecting key "move"
                            Ivec2 move = parseInput(inputStr);

                            gameController.receiveInput(move);
                            broadcastGameState();

                            if (!gameController.getGameOngoing()) {
                                handleGameEnd();
                            }
                        } else {
                            log("Received move from wrong player. Ignoring.");
                        }
                        break;

                    case "message":
                        handleChatMessage(sender, content);
                        break;

                    default:
                        log("Unknown message type: " + type);
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

        ThreadRegistry.getQueue(player1.getThread()).add(new ThreadMessage(Thread.currentThread(), update));
        ThreadRegistry.getQueue(player2.getThread()).add(new ThreadMessage(Thread.currentThread(), update));
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
     * Formats the game state in a string format
     * @return The string format of current player, winner, and game status
     */
    private String formatGameState() {
        return "Current Player: " + gameController.getCurrentPlayer() +
                ", Winner: " + java.util.Arrays.toString(gameController.getWinner()) +
                ", Game Ongoing: " + gameController.getGameOngoing();
    }

    /**
     * Ends the game if necessary and notifies both players
     */
    private void handleGameEnd(){
        int[] winner = gameController.getWinner();
        String message = winner.length == 0 ? "Game ended in a tie!" : "Player " + winner[0] + " wins!";

        HashMap<String, Object> gameEnd = new HashMap<>();
        gameEnd.put("type", "game-over");
        gameEnd.put("result", message);

        ThreadRegistry.getQueue(player1.getThread()).add(new ThreadMessage(Thread.currentThread(), gameEnd));
        ThreadRegistry.getQueue(player2.getThread()).add(new ThreadMessage(Thread.currentThread(), gameEnd));
        //TODO: update profiles 
        //End session
    }

    private Ivec2 parseInput(String inputStr) {
        String[] parts = inputStr.split(",");
        int x = Integer.parseInt(parts[0].trim());
        int y = Integer.parseInt(parts[1].trim());
        return new Ivec2(x, y);
    }

    private void handleChatMessage(Thread sender, HashMap<String, Object> content) {
        try {
            HashMap<String, Object> forward = new HashMap<>();
            forward.put("type", "message");
            forward.put("message", content.get("message"));

            if (sender == player1.getThread()) {
//                forward.put("sender", player1.getUsername());
                ThreadRegistry.getQueue(player2.getThread()).add(new ThreadMessage(Thread.currentThread(), forward));
            } else if (sender == player2.getThread()) {
//                forward.put("sender", player2.getUsername());
                ThreadRegistry.getQueue(player1.getThread()).add(new ThreadMessage(Thread.currentThread(), forward));
            } else {
                log("Chat message sender not recognized.");
            }
        } catch (Exception e) {
            log("Error handling chat message:", e.toString());
        }
    }


}