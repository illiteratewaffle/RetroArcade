package client_main.java.AuthenticationAndProfile;

import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import AuthenticationAndProfile.*;

class ProfileCSVReaderTest {

    @Test
    void openSingleProfileFile() {
        try {
            ArrayList<String> expectedOutput = new ArrayList<>(List.of("1692","User_cec35fa6","alice","User_cec35fa6@example.com",
                    "EC2452ECCC1514E4F614CA441D3185B0C26324A236EF11F1487935A350851C90","","","","true","0","0","0","0","0","0","unranked",
                    "unranked","unranked","0","0","0","","10 Wins Checkers=>0.00, 10 Wins Connect 4=>0.00, 10 Wins Tick Tac Toe=>0.00," +
                            " 50 Games Played Checkers=>0.00, 50 Games Played Connect 4=>0.00, 50 Games Played Tick Tac Toe=>0.00",
                    "1,2","7","0","0","0","0","0","0","2025-04-09 12:35:00.445503"));

            assertEquals(expectedOutput, ProfileCSVReader.openSingleProfileFile("tests/client_main/test_resources/AuthenticationAndProfile/player_profile_1692Test.csv"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void openProfilesFile() {
        try {
            ArrayList<ArrayList<String>> expectedOutput = new ArrayList<ArrayList<String>>(List.of(new ArrayList<>(List.of("1", "robert97", "Rob", "rob@gmail.com",
                            "h3j#h3h##45E$t$%6757", "Just a chill guy who loves board games.", "", "Connect-4", "true", "3.25", "500", "silver", "105", "TTT," +
                                    " Checkers, Connect-4, Connect-4", "Have100CatsInTTT=>0.75,WinCheckersWithoutLosingOnePiece=>0.00", "2, 3, 7", "8, 9","0","0","0","0","0","0","2025-03-27 05:22:20.752369")),
                    new ArrayList<>(List.of("2", "sara_b", "SaraB", "sara@example.com", "abcDEF123!@#", "Competitive and loves strategy games.", "profilePic.png", "", "false", "4.2", "640", "gold", "157",
                            "Connect-4, Checkers", "WinConnect4WithPerfectMoves=>0.60", "1", "4","0","0","0","0","0","0","2025-03-27 05:22:20.752369")),
                    new ArrayList<>(List.of("3", "dannyX", "Danny", "dannyx@gamezone.net", "secureHASH321$$", "Here for the fun!", "", "Checkers", "true", "1.9", "300", "bronze", "40",
                            "TTT, Connect-4", "TTTFirstWin=>0.00", "1, 2", "","0","0","0","0","0","0","2025-03-27 05:22:20.752369"))));
            assertEquals(expectedOutput, ProfileCSVReader.openProfilesFile("tests/client_main/test_resources/AuthenticationAndProfile/profiles_exportTest.csv"));
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}