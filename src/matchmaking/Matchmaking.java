package matchmaking;
import java.util.*;
public class Matchmaking {
    private MatchmakingQueue matchmakingQueue;

    public Matchmaking(MatchmakingQueue matchmakingQueue) {
        this.matchmakingQueue = matchmakingQueue;
    }

    /**
     * Matches players from the queue based on rank tolerance.
     * Pairs players with similar ranks and sets match status.
     * Runs until fewer than two players remain in the queue.
     * UNCOMPLETED Talk to Networking Team
     */
    public void matchOpponents() {
        while (matchmakingQueue.size() > 1) {
            Player p1 = matchmakingQueue.dequeue();
            Player p2 = matchmakingQueue.peek(); // Peek at the next player without removing

            if (p2 != null && Math.abs(p1.getRank() - p2.getRank()) <= 2) { // Rank difference tolerance
                matchmakingQueue.dequeue(p2, "Game type"); // Remove p2 from the queue
                System.out.println("Matched: " + p1 + " vs " + p2);
                p1.setInMatch(true);
                p2.setInMatch(true);
                //call function or return player to networking *************************
            } else {
                matchmakingQueue.enqueue(p1); // Reinsert if no suitable match
            }
        }
    }
    /***
     * Remove Player from queue upon cancellation or successful matchmaking
     * @param player Player to remove from queue
     */
    public void dequeue(Player player) {
        matchmakingQueue.remove(player);
    }

    /***
     * Enter players into queue
     * @param player Players
     */
    public void enque(Player player, String gametype) {
        if (!player.isTimeout()) {
            matchmakingQueue.enqueue(player, gametype);
        } else {
            System.out.println("Player " + player + " is in timeout.");
        }
    }

    /***
     * Check if player is undergoing timeout penalty
     * @param player Player to check
     */
    public boolean checkTimeout(Player player) {
        return player.isTimeout();
    }
}
