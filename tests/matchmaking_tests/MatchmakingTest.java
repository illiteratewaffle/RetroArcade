package matchmaking_tests;

import AuthenticationAndProfile.Profile;
import AuthenticationAndProfile.ProfileCreation;
import AuthenticationAndProfile.PlayerRanking;
import matchmaking.Matchmaking;
import matchmaking.MatchmakingQueue;
import org.junit.jupiter.api.*;
import player.PlayerHandler;
import player.PlayerManager;

import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class MatchmakingTest {

    private Profile profile1, profile2, profile3;
    private PlayerHandler handler1, handler2, handler3;
    private Matchmaking matchmaking;
    private boolean createdProfiles = false;

    @BeforeEach
    void setup() throws SQLException, IOException, NoSuchAlgorithmException {
        if (!createdProfiles){
            profile1 = ProfileCreation.createNewProfile("rankUser1", "r1@email.com", "pass123");
            profile2 = ProfileCreation.createNewProfile("rankUser2", "r2@email.com", "pass123");
            profile3 = ProfileCreation.createNewProfile("rankUser3", "r3@email.com", "pass123");

            handler1 = new PlayerHandler(new Socket(), new LinkedBlockingQueue<>(), profile1);
            handler2 = new PlayerHandler(new Socket(), new LinkedBlockingQueue<>(), profile2);
            handler3 = new PlayerHandler(new Socket(), new LinkedBlockingQueue<>(), profile3);

            // Set custom ratings in descending order: handler2 > handler3 > handler1
            PlayerRanking.setGameRating(profile1.getID(), 0, 1000); // Lowest
            PlayerRanking.setGameRating(profile2.getID(), 0, 2000); // Highest
            PlayerRanking.setGameRating(profile3.getID(), 0, 1500); // Mid

            matchmaking = new Matchmaking();
            createdProfiles = true;
        }
    }

    @AfterEach
    void teardown() throws SQLException {
        //PlayerManager.deleteProfile(profile1.getID());
        //PlayerManager.deleteProfile(profile2.getID());
        //PlayerManager.deleteProfile(profile3.getID());
    }

    @Test
    void testQueue() throws SQLException{
        matchmaking.enqueue(0, handler1);
        assertEquals(1, matchmaking.getQueueSize(0));
    }
    @Test
    void testSortedEnqueueByRanking() throws SQLException {
        matchmaking.enqueue(0, handler1); // Rating 1000
        matchmaking.enqueue(0, handler2); // Rating 2000
        matchmaking.enqueue(0, handler3); // Rating 1500

        // After quickSort, queue should be: handler2, handler3, handler1
        List<PlayerHandler> queue = MatchmakingQueue.getQueue(0);

        assertEquals(profile2.getID(), queue.get(0).getProfile().getID()); // highest
        assertEquals(profile3.getID(), queue.get(1).getProfile().getID());
        assertEquals(profile1.getID(), queue.get(2).getProfile().getID()); // lowest
    }

    @Test
    void testMatchOpponentsReturnsTopTwo() throws SQLException {
        matchmaking.enqueue(0, handler1);
        matchmaking.enqueue(0, handler2);
        matchmaking.enqueue(0, handler3);

        List<PlayerHandler> matched = matchmaking.matchOpponents(0);

        assertEquals(2, matched.size());
        assertEquals(profile2.getID(), matched.get(0).getProfile().getID()); // 2000
        assertEquals(profile3.getID(), matched.get(1).getProfile().getID()); // 1500

        assertEquals(1, matchmaking.getQueueSize(0)); // handler1 remains
    }

    @Test
    void testDequeueSpecificPlayer() throws SQLException {
        matchmaking.enqueue(0, handler1);
        matchmaking.enqueue(0, handler2);
        matchmaking.enqueue(0, handler3);

        matchmaking.dequeue(handler2);
        assertEquals(2, matchmaking.getQueueSize(0));
        assertFalse(MatchmakingQueue.isInQueue(handler2));
    }

    @Test
    void testPeekAndSizeAfterMatch() throws SQLException {
        matchmaking.enqueue(0, handler1);
        matchmaking.enqueue(0, handler2);

        assertEquals(2, matchmaking.getQueueSize(0));
        PlayerHandler top = MatchmakingQueue.peek(0);
        assertEquals(profile2.getID(), top.getProfile().getID()); // highest

        matchmaking.matchOpponents(0);
        assertEquals(0, matchmaking.getQueueSize(0));
    }

    @Test
    void testDequeueNonexistentPlayerDoesNotCrash() throws SQLException {
        matchmaking.enqueue(0, handler1);

        matchmaking.dequeue(handler2); // handler2 was never added

        assertEquals(1, matchmaking.getQueueSize(0));
        assertTrue(MatchmakingQueue.isInQueue(handler1));
    }

    @Test
    void testIsInQueueAfterEnqueue() throws SQLException {
        matchmaking.enqueue(0, handler1);
        assertTrue(matchmaking.isInQueue(handler1));//Handler should be present in the matchmaking queue after enqueueing
    }

    @Test
    void testStableSortWhenRatingsAreEqual() throws SQLException {
        PlayerRanking.setGameRating(profile1.getID(), 0, 1500);
        PlayerRanking.setGameRating(profile2.getID(), 0, 1500);
        PlayerRanking.setGameRating(profile3.getID(), 0, 1500);

        matchmaking.enqueue(0, handler1);
        matchmaking.enqueue(0, handler2);
        matchmaking.enqueue(0, handler3);

        List<PlayerHandler> queue = MatchmakingQueue.getQueue(0);

        // All ratings are the same – order should remain insertion order (if stable)
        assertEquals(profile1.getID(), queue.get(0).getProfile().getID());
        assertEquals(profile2.getID(), queue.get(1).getProfile().getID());
        assertEquals(profile3.getID(), queue.get(2).getProfile().getID());
    }
}
