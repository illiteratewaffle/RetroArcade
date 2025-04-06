package client_main.java.leaderboardTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import leaderboard.*;

public class LeaderboardTest {

    private static final String CSV_FILENAME = "profiles_export.csv";
    private static final String CSV_CONTENT =
            "id,username,nickname,email,hashed_password,bio,profile_pic_path,current_game,is_online,win_loss_ratio_ttt,win_loss_ratio_connect4,win_loss_ratio_checkers,rating_ttt,rating_connect4,rating_checkers,rank_ttt,rank_connect4,rank_checkers,wins_ttt,wins_connect4,wins_checkers,games_played,achievement_progress,friends,friend_requests,created_at\n" +
                    "19,username,null,email@email.com,12345678,null,null,null,null,0,0,0,0,0,0,unranked,unranked,unranked,0,0,0,null,null,{1,2,3,4},null,2025-04-05 18:57:53.970939\n" +
                    "97,colby,null,colby.campbell@ucalgary.ca,password,null,null,null,null,0.5,2,1,1500,1550,600,unranked,unranked,unranked,15,4,10,null,null,null,null,2025-04-04 17:33:23.814236\n" +
                    "98,evan,null,evansuckz,password,null,null,null,null,2,4,1.1,456,900,2050,unranked,unranked,unranked,45,8,55,null,null,null,null,2025-04-04 17:33:59.127249\n" +
                    "86,username,null,email@email.com,12345678,null,null,null,null,3,0.25,0.75,8000,2000,1000,unranked,unranked,unranked,56,99,34,null,null,null,null,2025-04-04 01:08:51.537061\n" +
                    "158,cristian,null,emailZ@me.com,HASHEDPASS,null,null,null,null,0.33,0.5,2,600,750,1800,unranked,unranked,unranked,8,10,69,null,null,null,null,2025-04-04 16:57:04.158973";

    private Leaderboard leaderboard;

    @BeforeAll
    public static void setupCSVFile() throws IOException {
        // Write the CSV content to a file named "profiles_export.csv"
        FileWriter writer = new FileWriter(CSV_FILENAME);
        writer.write(CSV_CONTENT);
        writer.close();
    }

    @Test
    public void testConstructorNullSortChoice() {
        // When sortChoice is null, it should default to "RATING"
        Leaderboard lb = new Leaderboard(null, "CHECKERS");
        ArrayList<ArrayList<String>> rankings = lb.getRankings();
        // For "RATING" sort, the highest rating (2050) is from evan (row3)
        assertEquals("evan", rankings.get(0).get(2));
    }

    @Test
    public void testSortByRating() {
        leaderboard = new Leaderboard("RATING", "CHECKERS");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        // Expected descending order by rating (for CHECKERS):
        // 1. evan (rating 2050)
        // 2. cristian (rating 1800)
        // 3. username (from row4, rating 1000)
        // 4. colby (rating 600)
        // 5. username (from row1, rating 0)
        assertEquals("evan", rankings.get(0).get(2));
        assertEquals("cristian", rankings.get(1).get(2));
        assertEquals("username", rankings.get(2).get(2)); // from row4
        assertEquals("colby", rankings.get(3).get(2));
        assertEquals("username", rankings.get(4).get(2)); // from row1

        // Each stripped profile should have 6 fields: [position, id, username, wlr, rating, wins]
        for (List<String> profile : rankings) {
            assertEquals(6, profile.size());
        }
    }

    @Test
    public void testSortByWLR() {
        leaderboard = new Leaderboard("WLR", "CHECKERS");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        // Expected descending order by win/loss ratio (for CHECKERS):
        // Row wins (wlr) are: row1 = 0, row2 = 1, row3 = 1.1, row4 = 0.75, row5 = 2
        // So the order should be: cristian (row5), evan (row3), colby (row2), username (row4), username (row1)
        assertEquals("cristian", rankings.get(0).get(2));
        assertEquals("evan", rankings.get(1).get(2));
        assertEquals("colby", rankings.get(2).get(2));
        assertEquals("username", rankings.get(3).get(2));
        assertEquals("username", rankings.get(4).get(2));
    }

    @Test
    public void testSortByWins() {
        leaderboard = new Leaderboard("WINS", "CHECKERS");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        // Wins (for CHECKERS) from our rows:
        // row1 = 0, row2 = 10, row3 = 55, row4 = 34, row5 = 69
        // Expected descending order: cristian (row5), evan (row3), username (row4), colby (row2), username (row1)
        assertEquals("cristian", rankings.get(0).get(2));
        assertEquals("evan", rankings.get(1).get(2));
        assertEquals("username", rankings.get(2).get(2));
        assertEquals("colby", rankings.get(3).get(2));
        assertEquals("username", rankings.get(4).get(2));
    }

    @Test
    public void testToggleSortOrder() {
        leaderboard = new Leaderboard("RATING", "CHECKERS");
        ArrayList<ArrayList<String>> original = leaderboard.getRankings();
        // Make a copy of the original order
        List<List<String>> originalCopy = new ArrayList<>(original);
        leaderboard.toggleSortOrder(original);
        ArrayList<ArrayList<String>> toggled = leaderboard.getRankings();
        // The toggled order should be the reverse of the original order
        assertEquals(originalCopy.get(originalCopy.size() - 1).get(2), toggled.get(0).get(2));
    }

    @Test
    public void testGUIToggleSortOrder() {
        leaderboard = new Leaderboard("RATING", "CHECKERS");
        ArrayList<ArrayList<String>> sortedOrder = leaderboard.getRankings();
        leaderboard.GUIToggleSortOrder();
        ArrayList<ArrayList<String>> toggledOrder = leaderboard.getRankings();
        // Verify that toggling via the GUI method reverses the sorted order
        assertEquals(sortedOrder.get(sortedOrder.size() - 1).get(2), toggledOrder.get(0).get(2));
    }

    @Test
    public void testSearchPlayerFound() {
        leaderboard = new Leaderboard("RATING", "CHECKERS");
        // Search for a player that is known to exist ("evan" from row3)
        ArrayList<ArrayList<String>> result = leaderboard.searchPlayer("evan");
        assertFalse(result.isEmpty());
        // In the stripped profile, username is at index 2
        assertEquals("evan", result.get(0).get(2));
    }

    @Test
    public void testSearchPlayerNotFound() {
        leaderboard = new Leaderboard("RATING", "CHECKERS");
        // Search for a username that does not exist
        ArrayList<ArrayList<String>> result = leaderboard.searchPlayer("nonexistent");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFilterFriends() {

    }
}
