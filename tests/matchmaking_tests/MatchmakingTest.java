package matchmaking_tests;

import AuthenticationAndProfile.Profile;
import AuthenticationAndProfile.PlayerRanking;
import AuthenticationAndProfile.ProfileCreation;
import matchmaking.Matchmaking;
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

    private PlayerHandler handler1;
    private PlayerHandler handler2;
    private PlayerHandler handler3;
    private Profile profile1;
    private Profile profile2;
    private Profile profile3;
    private Matchmaking matchmaking;

    @BeforeEach
    void setup() throws SQLException, IOException, NoSuchAlgorithmException {
        profile1 = ProfileCreation.createNewProfile("testUser1", "user1@email.com", "pass123");
        profile2 = ProfileCreation.createNewProfile("testUser2", "user2@email.com", "pass123");
        profile3 = ProfileCreation.createNewProfile("testUser3", "user3@email.com", "pass123");

        handler1 = new PlayerHandler(new Socket(), new LinkedBlockingQueue<>(), profile1);
        handler2 = new PlayerHandler(new Socket(), new LinkedBlockingQueue<>(), profile2);
        handler3 = new PlayerHandler(new Socket(), new LinkedBlockingQueue<>(), profile3);

        matchmaking = new Matchmaking();
    }

    @AfterEach
    void teardown() throws SQLException {
        PlayerManager.deleteProfile(profile1.getID());
        PlayerManager.deleteProfile(profile2.getID());
        PlayerManager.deleteProfile(profile3.getID());
    }

    @Test
    void testEnqueueMultiplePlayers() throws SQLException {
        matchmaking.enqueue(0, handler1);
        matchmaking.enqueue(0, handler2);
        matchmaking.enqueue(0, handler3);

        assertEquals(3, matchmaking.getQueueSize(0));
    }

    @Test
    void testMatchTwoOfThreePlayers() throws SQLException {
        matchmaking.enqueue(0, handler1);
        matchmaking.enqueue(0, handler2);
        matchmaking.enqueue(0, handler3);

        List<PlayerHandler> matched = matchmaking.matchOpponents(0);

        assertEquals(2, matched.size());
        assertTrue(matched.contains(handler1) || matched.contains(handler2) || matched.contains(handler3));

        // Only one should remain in the queue
        assertEquals(1, matchmaking.getQueueSize(0));
    }

    @Test
    void testDequeueOneOfThree() throws SQLException {
        matchmaking.enqueue(0, handler1);
        matchmaking.enqueue(0, handler2);
        matchmaking.enqueue(0, handler3);

        matchmaking.dequeue(handler2);

        assertEquals(2, matchmaking.getQueueSize(0));
        assertFalse(matchmaking.isInQueue(handler2));
        assertTrue(matchmaking.isInQueue(handler1));
        assertTrue(matchmaking.isInQueue(handler3));
    }

    @Test
    void testMatchThenDequeueRemaining() throws SQLException {
        matchmaking.enqueue(0, handler1);
        matchmaking.enqueue(0, handler2);
        matchmaking.enqueue(0, handler3);

        matchmaking.matchOpponents(0); // matches two

        assertEquals(1, matchmaking.getQueueSize(0));

        matchmaking.dequeue(handler3);
        assertEquals(0, matchmaking.getQueueSize(0));
    }

    @Test
    void testQuickSortWithPlayerRankings() throws SQLException {
        int[] rating1 = {17, 0, 0};
        int[] rating2 = {10,0,0};
        int[] rating3 = {8, 1, 0};

        PlayerRanking.setGameRating(profile1.getID(), 0, 35);
        PlayerRanking.setGameRating(profile2.getID(), 0, 29);
        PlayerRanking.setGameRating(profile3.getID(), 0, 15);

        matchmaking.enqueue(0, handler1);
        matchmaking.enqueue(0, handler2);
        matchmaking.enqueue(0, handler3);

        List<PlayerHandler> matched = matchmaking.matchOpponents(0);
        assertEquals(handler2, matched.get(0));
        assertEquals(handler3, matched.get(1));
    }
}
