package Matchmaking;
import java.util.*
import Player
public class Matchmaking {
    private QueueSystem queue;
    private boolean matchFound;

    public Matchmaking(QueueSystem queue) {
        this.queue = queue;
        this.matchFound = false;
    }

    public Player findMatch() {
        List<Player> sortedPlayers = queue.sortQueue();
        if (sortedPlayers.size() >= 2) {
            matchFound = true;
            return sortedPlayers.get(0);
        }
        return null;
    }

    public String displayMatchConfirmation() {
        return matchFound ? "Match Found!" : "No Match Found!";
    }
}
