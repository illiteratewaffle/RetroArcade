package server_main.java.leaderboardTests;

import static org.junit.jupiter.api.Assertions.*;

import leaderboard.CSVFileReader;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFileReaderTest {

    // Use a test filename so as not to interfere with production data
    private static final String TEST_CSV_FILENAME = "test_profiles.csv";

    // CSV content: header plus three rows
    private static final String CSV_CONTENT =
            "id,username,nickname,email,hashed_password,bio,profile_pic_path,current_game,is_online,win_loss_ratio_ttt,win_loss_ratio_connect4,win_loss_ratio_checkers,rating_ttt,rating_connect4,rating_checkers,rank_ttt,rank_connect4,rank_checkers,wins_ttt,wins_connect4,wins_checkers,games_played,achievement_progress,friends,friend_requests,created_at\n" +
                    "19,username,null,email@email.com,12345678,null,null,null,null,0,0,0,0,0,0,unranked,unranked,unranked,0,0,0,null,null,{1,2,3,4},null,2025-04-05 18:57:53.970939\n" +
                    "97,colby,null,colby.campbell@ucalgary.ca,password,null,null,null,null,0.5,2,1,1500,1550,600,unranked,unranked,unranked,15,4,10,null,null,null,null,2025-04-04 17:33:23.814236\n" +
                    "98,evan,null,evansuckz,password,null,null,null,null,2,4,1.1,456,900,2050,unranked,unranked,unranked,45,8,55,null,null,null,null,2025-04-04 17:33:59.127249";

    @BeforeAll
    public static void setupCSVFile() throws IOException {
        // Write the CSV content to a temporary file
        FileWriter writer = new FileWriter(TEST_CSV_FILENAME);
        writer.write(CSV_CONTENT);
        writer.close();
    }

    @Test
    public void testRetrieveProfiles() {
        // Call retrieveProfiles on our temporary CSV file
        ArrayList<ArrayList<String>> profiles = CSVFileReader.retrieveProfiles(TEST_CSV_FILENAME);
        // We expect three profiles (rows)
        assertEquals(3, profiles.size(), "Should have 3 profiles");

        // According to CSVFileReader's transformation, each row becomes an 11-element list:
        // [ field[0], field[1], field[9], field[10], field[11], field[12], field[13], field[14], field[18], field[19], field[20] ]
        // For row 1 (id "19")
        List<String> expectedRow1 = List.of("19", "username", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        assertEquals(expectedRow1, profiles.get(0), "First profile does not match expected values");

        // For row 2 (id "97" for colby)
        List<String> expectedRow2 = List.of("97", "colby", "0.5", "2", "1", "1500", "1550", "600", "15", "4", "10");
        assertEquals(expectedRow2, profiles.get(1), "Second profile does not match expected values");

        // For row 3 (id "98" for evan)
        List<String> expectedRow3 = List.of("98", "evan", "2", "4", "1.1", "456", "900", "2050", "45", "8", "55");
        assertEquals(expectedRow3, profiles.get(2), "Third profile does not match expected values");
    }

    @Test
    public void testFileNotFound() {
        // When the file is not found, CSVFileReader.openFile catches the exception and returns an empty list.
        ArrayList<ArrayList<String>> profiles = CSVFileReader.retrieveProfiles("nonexistent_file.csv");
        assertTrue(profiles.isEmpty(), "Profiles should be empty when file is not found");
    }
}
