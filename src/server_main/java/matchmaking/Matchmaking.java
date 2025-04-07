package matchmaking;
import player.PlayerHandler;

import java.sql.SQLException;
import java.util.*;

public class Matchmaking {

    /***
     * Matches opponents from the MatchmakingQueue that has been sorted. Takes the first and second
     * @param gameType the game to retrieve two players from
     * @return List of 2 PlayerHandlers
     */
    public List<PlayerHandler> matchOpponents(int gameType) {

        PlayerHandler h1 = MatchmakingQueue.dequeue(gameType);
        PlayerHandler h2 = MatchmakingQueue.peek(gameType);

        MatchmakingQueue.dequeue(h2, gameType);
        // Create a list to hold the matched players
        List<PlayerHandler> matchedPlayers = new ArrayList<>();
        matchedPlayers.add(h1);
        matchedPlayers.add(h2);
        return matchedPlayers;

    }

    /***
     * Adds players into the specific LinkedList, access's MatchmakingQueue to enqueue into the data structure
     * @param gameType the specific gameType LinkedList to add PlayerHandler into
     * @param handler the player to add into the LinkedList
     */
    public void enqueue(int gameType, PlayerHandler handler) throws SQLException {
        MatchmakingQueue.enqueue(handler, gameType);
    }

    /**
     * Dequeues a player from the matchmaking queue if they want to exit.
     * @param player The PlayerHandler of the player requesting to leave the queue.
     */
    public void dequeue(PlayerHandler player) {
        MatchmakingQueue.dequeue(player);
    }

    /**
     * Gets the current size of the matchmaking queue for a specific game type.
     * 0 for Tic-Tac-Toe,
     * 1 for Connect Four,
     * 2 for Checkers.
     * @param gameType An integer representing the game type
     * @return The number of players currently waiting in the queue for the specified game type.
     */
    public int getQueueSize(int gameType) {
        if (gameType == 0){
            return MatchmakingQueue.getQueueWaitTTT();
        } else if (gameType == 1) {
            return MatchmakingQueue.getQueueWaitCon4();
        } else {
            return MatchmakingQueue.getWaitCheckers();
        }
    }

}
