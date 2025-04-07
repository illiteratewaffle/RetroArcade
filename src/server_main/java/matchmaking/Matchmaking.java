package matchmaking;
import player.PlayerHandler;

import java.util.*;

public class Matchmaking {

    /***
     *
     * @param gameType
     * @return
     */
    public List<PlayerHandler> matchOpponents(int gameType) {
        while (MatchmakingQueue.size(gameType) > 1) {
            PlayerHandler h1 = MatchmakingQueue.dequeue(gameType);      //Solulu
            PlayerHandler h2 = MatchmakingQueue.peek(gameType);

            if (h2 != null) {
                int rank1 = h1.getProfile().getPlayerRanking().getRating(gameType);
                int rank2 = h2.getProfile().getPlayerRanking().getRating(gameType);

                if (Math.abs(rank1 - rank2) <= 150) {
                    MatchmakingQueue.dequeue(h2, gameType);         //FInd a solulu
                    // Create a list to hold the matched players
                    List<PlayerHandler> matchedPlayers = new ArrayList<>();
                    matchedPlayers.add(h1);
                    matchedPlayers.add(h2);
                } else {
                    MatchmakingQueue.enqueue(h1, gameType);
                }
            } else {
                MatchmakingQueue.enqueue(h1, gameType);
            }
        }

        return null;
    }

    /***
     *
     * @param gameType
     * @param handler
     */
    public void enqueue(int gameType, PlayerHandler handler) {
        MatchmakingQueue.enqueue(handler, gameType);
    }

    /***
     *
     * @param player
     */
    public void dequeue(PlayerHandler player) {
        MatchmakingQueue.dequeue(player);
    }

    /***
     *
     * @param gameType
     * @return
     */
    public int getQueueSize(int gameType){
        if (gameType == 0){
            return MatchmakingQueue.getQueueWaitTTT();
        } else if (gameType == 1) {
            return MatchmakingQueue.getQueueWaitCon4();
        } else {
            return MatchmakingQueue.getWaitCheckers();
        }
    }
}
