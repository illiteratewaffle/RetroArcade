package matchmaking;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class MatchmakingTest {

    @Test
    void testQuickSortDescendingOrder() {
        Player p1 = new Player("Alice", 5);
        Player p2 = new Player("Bob", 10);
        Player p3 = new Player("Charlie", 7);


        gameQueues.enqueue(p1, "Chess");
        gameQueues.enqueue(p2, "Chess");
        gameQueues.enqueue(p3, "Chess");

        LinkedList<Player> sorted = gameQueues.getQueue("1v1");
        assertEquals(10, sorted.get(0).getRank());
        assertEquals(7, sorted.get(1).getRank());
        assertEquals(5, sorted.get(2).getRank());
    }

    @Test
    void testTimeoutPreventsEnqueue() {
        Player p = new Player("Frank", 6);
        p.setTimeout(true);

        Matchmaking.enque(p); // Should not be added
        assertEquals(0, queue.getQueue("gametype").size());
    }


}
