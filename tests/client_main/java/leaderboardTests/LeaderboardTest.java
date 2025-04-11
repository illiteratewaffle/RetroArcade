package client_main.java.leaderboardTests;

import static org.junit.jupiter.api.Assertions.*;

import leaderboard.Leaderboard;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    // Setup CSV File
    @BeforeAll
    public static void setupCSVFile() throws IOException {
        // Write the CSV content to a file named "profiles_export.csv"
        FileWriter writer = new FileWriter(CSV_FILENAME);
        writer.write(CSV_CONTENT);
        writer.close();
    }

    // constructor

    @Test
    public void testConstructorNullSortChoice() {
        // When sortChoice is null, it should default to "RATING"
        Leaderboard leaderboard = new Leaderboard(null, "CHECKERS");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        // For "RATING" sort (with cherkers), the highest rating (2050) is from evan (row3)
        assertEquals("evan", rankings.get(0).get(2));
    }

    @Test
    public void testInvalidSortChoice() {
        // When an invalid sortChoice is passed, none of the conditions in getTopPlayers() match
        // The unsorted profiles are returned directly from CSVFileReader, which have 11 fields each
        Leaderboard leaderboard = new Leaderboard("INVALID", "CHECKERS");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        assertEquals(5, rankings.size());
        for (ArrayList<String> profile : rankings) {
            assertEquals(11, profile.size(), "Each unsorted profile should have 11 fields");
        }
        // Verify the CSV file order is preserved
        assertEquals("username", rankings.get(0).get(1));
        assertEquals("colby", rankings.get(1).get(1));
        assertEquals("evan", rankings.get(2).get(1));
        assertEquals("username", rankings.get(3).get(1));
        assertEquals("cristian", rankings.get(4).get(1));
    }

    @Test
    public void testInvalidGame() {
        // When an invalid game is specified, the strip() method returns an empty list
        Leaderboard leaderboard = new Leaderboard("RATING", "INVALID");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        assertTrue(rankings.isEmpty(), "Rankings should be empty for an invalid game");
    }

    // getTopPlayers & sortByRating

    @Test
    public void testSortByRating() {
        leaderboard = new Leaderboard("RATING", "CHECKERS");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        // Expected descending order by rating (for CHECKERS)
        // 1. evan (rating 2050, row3)
        // 2. cristian (rating 1800, row5)
        // 3. username (rating 1000, row4)
        // 4. colby (rating 600, row2)
        // 5. username (rating 0, row1)
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
    public void testSortByRatingTTT() {
        // For game TTT, sort by rating_ttt:
        // Row ratings: 0 (row1), 1500 (row2), 456 (row3), 8000 (row4), 600 (row5)
        // Expected descending order by rating_ttt: id 86, 97, 158, 98, 19
        Leaderboard leaderboard = new Leaderboard("RATING", "TTT");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        assertEquals("86", rankings.get(0).get(1));
        assertEquals("97", rankings.get(1).get(1));
        assertEquals("158", rankings.get(2).get(1));
        assertEquals("98", rankings.get(3).get(1));
        assertEquals("19", rankings.get(4).get(1));
    }

    @Test
    public void testSortByRatingC4() {
        // For game C4, sort by rating_connect4:
        // Row ratings: 0 (row1), 1550 (row2), 900 (row3), 2000 (row4), 750 (row5)
        // Expected descending order: id 86, 97, 98, 158, 19
        Leaderboard leaderboard = new Leaderboard("RATING", "C4");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        assertEquals("86", rankings.get(0).get(1));
        assertEquals("97", rankings.get(1).get(1));
        assertEquals("98", rankings.get(2).get(1));
        assertEquals("158", rankings.get(3).get(1));
        assertEquals("19", rankings.get(4).get(1));
    }

    // Sorting WLR

    @Test
    public void testSortByWLR() {
        leaderboard = new Leaderboard("WLR", "CHECKERS");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        // Expected descending order by win/loss ratio (for CHECKERS):
        // wlr values: 0 (row1), 1 (row2), 1.1 (row3), 0.75 (row4), 2 (row5)
        // Expected order: cristian (row5), evan (row3), colby (row2), username (row4), username (row1
        assertEquals("cristian", rankings.get(0).get(2));
        assertEquals("evan", rankings.get(1).get(2));
        assertEquals("colby", rankings.get(2).get(2));
        assertEquals("username", rankings.get(3).get(2));
        assertEquals("username", rankings.get(4).get(2));
    }

    @Test
    public void testSortByWLRTTT() {
        // For game TTT, sort by win/loss ratio (wlr_ttt):
        // wlr: 0 (row1), 0.5 (row2), 2 (row3), 3 (row4), 0.33 (row5)
        // Expected order: id 86, 98, 97, 158, 19
        Leaderboard leaderboard = new Leaderboard("WLR", "TTT");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        assertEquals("86", rankings.get(0).get(1));
        assertEquals("98", rankings.get(1).get(1));
        assertEquals("97", rankings.get(2).get(1));
        assertEquals("158", rankings.get(3).get(1));
        assertEquals("19", rankings.get(4).get(1));
    }

    @Test
    public void testSortByWLRC4() {
        // For game C4, sort by win/loss ratio (wlr_connect4):
        // wlr: 0 (row1), 2 (row2), 4 (row3), 0.25 (row4), 0.5 (row5)
        // Expected order: id 98, 97, 158, 86, 19
        Leaderboard leaderboard = new Leaderboard("WLR", "C4");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        assertEquals("98", rankings.get(0).get(1));
        assertEquals("97", rankings.get(1).get(1));
        assertEquals("158", rankings.get(2).get(1));
        assertEquals("86", rankings.get(3).get(1));
        assertEquals("19", rankings.get(4).get(1));
    }

    // SortByWins

    @Test
    public void testSortByWins() {
        leaderboard = new Leaderboard("WINS", "CHECKERS");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        // Wins for CHECKERS: 0 (row1), 10 (row2), 55 (row3), 34 (row4), 69 (row5)
        // Expected descending order: cristian (row5), evan (row3), username (row4), colby (row2), username (row1)
        assertEquals("cristian", rankings.get(0).get(2));
        assertEquals("evan", rankings.get(1).get(2));
        assertEquals("username", rankings.get(2).get(2));
        assertEquals("colby", rankings.get(3).get(2));
        assertEquals("username", rankings.get(4).get(2));
    }

    @Test
    public void testSortByWinsTTT() {
        // For game TTT, wins: 0 (row1), 15 (row2), 45 (row3), 56 (row4), 8 (row5)
        // Expected descending order: id 86, 98, 97, 158, 19
        Leaderboard leaderboard = new Leaderboard("WINS", "TTT");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        assertEquals("86", rankings.get(0).get(1));
        assertEquals("98", rankings.get(1).get(1));
        assertEquals("97", rankings.get(2).get(1));
        assertEquals("158", rankings.get(3).get(1));
        assertEquals("19", rankings.get(4).get(1));
    }

    @Test
    public void testSortByWinsC4() {
        // For game C4, wins: 0 (row1), 4 (row2), 8 (row3), 99 (row4), 10 (row5)
        // Expected descending order: id 86, 158, 98, 97, 19
        Leaderboard leaderboard = new Leaderboard("WINS", "C4");
        ArrayList<ArrayList<String>> rankings = leaderboard.getRankings();
        assertEquals("86", rankings.get(0).get(1));
        assertEquals("158", rankings.get(1).get(1));
        assertEquals("98", rankings.get(2).get(1));
        assertEquals("97", rankings.get(3).get(1));
        assertEquals("19", rankings.get(4).get(1));
    }

    // toggle Sort Order Methods

    @Test
    public void testToggleSortOrder() {
        leaderboard = new Leaderboard("RATING", "CHECKERS");
        ArrayList<ArrayList<String>> original = leaderboard.getRankings();
        // Make a copy of the original order
        List<List<String>> originalCopy = new ArrayList<>(original);
        leaderboard.toggleSortOrder(original);
        ArrayList<ArrayList<String>> toggled = leaderboard.getRankings();
        // The toggled order should be the reverse of the original order
        assertEquals(originalCopy.getLast().get(2), toggled.getFirst().get(2));
    }

    @Test
    public void testGUIToggleSortOrder() {
        leaderboard = new Leaderboard("RATING", "CHECKERS");
        ArrayList<ArrayList<String>> sortedOrder = leaderboard.getRankings();
        leaderboard.GUIToggleSortOrder();
        ArrayList<ArrayList<String>> toggledOrder = leaderboard.getRankings();
        // Verify that toggling via the GUI method reverses the sorted order
        assertEquals(sortedOrder.getLast().get(2), toggledOrder.getFirst().get(2));
    }

    // Search

    @Test
    public void testSearchPlayerFound() {
        leaderboard = new Leaderboard("RATING", "CHECKERS");
        // Search for a known player ("evan" from row3)
        ArrayList<ArrayList<String>> result = leaderboard.searchPlayer("evan");
        assertFalse(result.isEmpty());
        // In the stripped profile, username is at index 2
        assertEquals("evan", result.getFirst().get(2));
    }

    @Test
    public void testSearchPlayerNotFound() {
        leaderboard = new Leaderboard("RATING", "CHECKERS");
        // Search for a username that does not exist
        ArrayList<ArrayList<String>> result = leaderboard.searchPlayer("does not exist");
        assertTrue(result.isEmpty());
    }

    // Filtering

    @Test
    public void testFilterFriends() {
        // create leaderboard with game "CHECKERS" (which triggers reading and stripping the CSV)
        leaderboard = new Leaderboard("RATING", "CHECKERS");

        // the stripped rankings should have 5 rows
        ArrayList<ArrayList<String>> originalRankings = leaderboard.getRankings();
        assertEquals(5, originalRankings.size(), "Pre-filter, there should be 5 rows in the rankings");

        // filterFriends()
        ArrayList<ArrayList<String>> filteredRankings = leaderboard.filterFriends();

        // the row with id "158" should be removed
        assertEquals(4, filteredRankings.size(), "After filtering, there should be 4 rows in the rankings");

        // Check that each row's id is one of the allowed friends: 19, 97, 98, or 86
        for (ArrayList<String> row : filteredRankings) {
            int id = Integer.parseInt(row.get(1));
            assertTrue(id == 19 || id == 97 || id == 98 || id == 86,
                    "Row with id " + id + " should be in the allowed friends list");
        }
    }
}
