package matchmaking;
import java.util.*;


public class Matchmaking {
    private MatchmakingQueue matchmakingQueue;

    public Matchmaking(MatchmakingQueue matchmakingQueue) {
        this.matchmakingQueue = matchmakingQueue;
    }


//ProfileDatabaseAccess.obtainPlayerRanking(id)
//.obtainProfile(id)

    /**
     * Matches players from the queue based on rank tolerance.
     * Pairs players with similar ranks and sets match status.
     * Runs until fewer than two players remain in the queue.
     * UNCOMPLETED Talk to Networking Teama
     * Which queue should I match out from>?????????? Solved?
     */
    public Profile matchOpponents(String gameType) {
        LinkedList<Profile> queue = matchmakingQueue.getQueue(gameType); // Add a getter for this in MatchmakingQueue

        while (matchmakingQueue.size() > 1) {

            Profile p1 = matchmakingQueue.dequeue(gameType);
            Profile p2 = matchmakingQueue.peek(); // Peek at the next player without removing

            if (p2 != null && Math.abs(p1.getPlayerRanking().getRanking(gameType) - p2.getPlayerRanking().getRanking(gameType) <= 150)) { // Rank difference tolerance
                matchmakingQueue.dequeue(p2, "Game type"); // Remove p2 from the queue

                return (p1,p2);
            } else {
                matchmakingQueue.enqueue(p1, gameType); // Reinsert if no suitable match
            }
        }
    }

    /***
     * Enter players into queue
     * @param player Players
     */
    public void enque(String gameType, Profile player) {

        if (!player.isTimeout()) {
            matchmakingQueue.enqueue(player, gameType);
        }

    }

}