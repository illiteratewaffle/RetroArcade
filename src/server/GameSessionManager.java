package server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 
 */
public class GameSessionManager implements Runnable{
    private final Player player1;
    private final Player player2; 
    
    private final IBoardGameController gameController; 
    private final ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> queue; 
    

    /**
     * Constructs the GameSessionManager with the two players and a queue
     * @param player1 First Player
     * @param player2 Second Player
     * @param queue The shared communication between the two players 
     */
    public GameSessionManager(Player player1, Player player2, ConcurrentHashMap<Thread, BlockingQueue<ThreadMessage>> queue){
        this.player1 = player1;
        this.player2 = player2; 
        this. queue = queue;
        this.gameController = new IBoardGameController(); //IBoardController is a class that is being develop by the game logic team

    }

    /**
     * The game continues in a loop till it ends. 
     * It waits for the player inputs, processes the moves and updates the game state. 
     */
    @Override
    public void run(){
        while (gameController.GetGameonGoing()){ 
            Player currentPlayer = getCurrentPlayer(); //Get the player whose turn it is 
            ThreadMessage message = recieveInputFromPlayer(currentPlayer); //Wait for player input

            if (message != null){
                gameController.RecieveInput(message.getCoordinates()); //Process the input in the game logic 
                GameStateToPlayers(); //Notify players of the state of the game 
            }

            if (!gameController.GetGameonGoing()){
                handleGameEnd(); //Handle the game if no longer on going. 
            }
        }
    }


    /**
     * Determines the current player based on the game state. 
     * @return The player whose turn it is.
     */
    private Player getCurrentPlayer(){
        int currentPlayerIndex = gameController.GetCurrentPlayer(); //Get the player's index
        
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
    private ThreadMessage recieveInputFromPlayer(Player player){
        try {
            BlockingQueue<ThreadMessage> playerQueue = queue.get(player.getThread());
            return playerQueue.take();  // Blocking call until input received from the queue
        } catch (InterruptedException e){
            Thread.currentThread().interrupt(); //Restore interrupted state
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
    private String formatGameState(){
        return "Current Player: " + gameController.GetCurrentPlayer() + ", Winner: " + java.util.Arrays.toString(gameController.GetWinner()) + 
                ", Game On Going: " + gameController.GetGameonGoing();
    }
    /**
     *  Sends a message to a specific player
     * @param player the player send the message to 
     * @param message The message to send 
     */
    private void sendToPlayer(Player player, ThreadMessage message){

        //Add message to player's queue
        BlockingQueue<ThreadMessage> playerQueue = queue.get(player.getThread());
        if (playerQueue !=null){
            playerQueue.offer(message); 
        }
    }

    /**
     * Ends the game if necessary and notifies both players
     */
    private void handleGameEnd(){
        int[] winner = gameController.GetWinner(); //Get the winner of the game 

        String resultMessage = (winner.length > 1) ? "Game ended in a tie" : "Player " + winner[0] + " won!";
        sendToPlayer(player1, new ThreadMessage(Thread.currentThread(), resultMessage)); // Notify player 1 of the result
        sendToPlayer(player2, new ThreadMessage(Thread.currentThread(), resultMessage)); // Notify player 2 of the result

    }

}
