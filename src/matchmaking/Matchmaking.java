package matchmaking;
import java.util.*;
public class Matchmaking {
    private MatchmakingQueue matchmakingQueue;

    public Matchmaking(MatchmakingQueue matchmakingQueue) {
        this.matchmakingQueue = matchmakingQueue;
    }

    /***
     * Match Opponents according to rank
     */
    /***
     * Match Opponents according to rank (sorted matchmaking queue ensures similar ranks are adjacent)
     */
    public void matchOpponents() {
        while (matchmakingQueue.size() > 1) {
            Player p1 = matchmakingQueue.dequeue();
            Player p2 = matchmakingQueue.peek(); // Peek at the next player without removing

            if (p2 != null && Math.abs(p1.getRank() - p2.getRank()) <= 2) { // Rank difference tolerance
                matchmakingQueue.dequeue(p2, "Game type" ); // Remove p2 from the queue
                System.out.println("Matched: " + p1 + " vs " + p2);
                p1.setInMatch(true);
                p2.setInMatch(true);
            } else {
                matchmakingQueue.enqueue(p1); // Reinsert if no suitable match
            }
        }
    }

    /***
     * Matchmake friend invitations
     */
    public void matchFriends(Player friend, Player player) {
        if (!friend.isInMatch() && !player.isInMatch()) {
            System.out.println("Friends Matched: " + friend + " vs " + player);
            friend.setInMatch(true);
            player.setInMatch(true);
        } else {
            System.out.println("One of the players is already in a match.");
        }
    }

    /***
     * Remove Player from queue upon cancellation or successful matchmaking
     */
    public void dequeue(Player player) {
        matchmakingQueue.remove(player);
    }

    /***
     * Enter players into queue
     */
    public void enque(Player player) {
        if (!player.isTimeout()) {
            matchmakingQueue.enqueue(player);
        } else {
            System.out.println("Player " + player + " is in timeout.");
        }
    }

    /***
     * Check if player is undergoing timeout penalty
     */
    public boolean checkTimeout(Player player) {
        return player.isTimeout();
    }