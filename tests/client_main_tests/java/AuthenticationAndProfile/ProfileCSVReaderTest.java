package client_main_tests.java.AuthenticationAndProfile;

import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import AuthenticationAndProfile.*;

class ProfileCSVReaderTest {

    @Test
    void openSingleProfileFile() {
        ArrayList<String> expectedOutput = new ArrayList<>(List.of("2","sara_b","SaraB","sara@example.com","abcDEF123!@#",
                "Competitive and loves strategy games.","profilePic.png","null","f","4.2","640","gold","157","Connect-4," +
                        " Checkers","{0.60, WinConnect4WithPerfectMoves}","1, 7","4","2025-03-27 05:22:20.752369"));

        assertEquals(expectedOutput, ProfileCSVReader.openSingleProfileFile("tests/client_main/test_resources/AuthenticationAndProfile/player_profile_2Test.csv"));
    }

    @Test
    void openProfilesFile() {
        ArrayList<ArrayList<String>> expectedOutput= new ArrayList<ArrayList<String>>(List.of(new ArrayList<>(List.of("1","robert97","Rob","rob@gmail.com",
                "h3j#h3h##45E$t$%6757","Just a chill guy who loves board games.","null","Connect-4","t","3.25","500","silver","105","TTT," +
                        " Checkers, Connect-4, Connect-4 (first played ever)","{0.75, Have100CatsInTTT}, {0.00, WinCheckersWithoutLosingOnePiece}","2, 3, 7","8, 9","2025-03-27 05:22:20.752369")),
                new ArrayList<>(List.of("2","sara_b","SaraB","sara@example.com","abcDEF123!@#","Competitive and loves strategy games.","profilePic.png","null","f","4.2","640","gold","157",
                        "Connect-4, Checkers","{0.60, WinConnect4WithPerfectMoves}","1","4","2025-03-27 05:22:20.752369")),
                new ArrayList<>(List.of("3","dannyX","Danny","dannyx@gamezone.net","secureHASH321$$","Here for the fun!","null","Checkers","t","1.9","300","bronze","40",
                "TTT, Connect-4","{1.0, TTTFirstWin}","1, 2","","2025-03-27 05:22:20.752369"))));
        assertEquals(expectedOutput, ProfileCSVReader.openProfilesFile("tests/client_main/test_resources/AuthenticationAndProfile/profiles_exportTest.csv"));
    }
}