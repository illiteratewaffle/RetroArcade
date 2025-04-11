package server_main.java.AuthenticationAndProfile;

import AuthenticationAndProfile.PlayerRanking;
import AuthenticationAndProfile.Profile;
import AuthenticationAndProfile.ProfileCreation;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerRankingTest {
    private PlayerRanking playerRanking;
    private static final int TTT_INDEX = 0;
    private static final int CONNECT4_INDEX = 1;
    private static final int CHECKERS_INDEX = 2;


    @Test
    void endOfMatchMethod() throws SQLException, IOException, NoSuchAlgorithmException {
        String username = Profile.generateUsername();
        String newUsername = Profile.generateUsername();
        String email = username + "@example.com";
        String password = Profile.generatePassword();
        Profile profile = ProfileCreation.createNewProfile(username, email, password);
        PlayerRanking playerRanking = new PlayerRanking(profile.getID(), new double[3], new int[3], new String[3], new int[3], new int[3], new int[3]);
        int playerID = profile.getID();

        PlayerRanking.setGameRating(playerID, TTT_INDEX, 1000);

        playerRanking.endOfMatchMethod(playerID, TTT_INDEX, 1);
        assertEquals(1050, playerRanking.getRating(playerID, TTT_INDEX));
        assertEquals(1, playerRanking.getWins(TTT_INDEX));
        assertEquals(1, playerRanking.getTotal(TTT_INDEX));

//        assertTrue(profile.getGameHistory().getAchievementProgress().containsKey("10 Wins ttt"));
//        assertTrue(profile.getGameHistory().getAchievementProgress().containsKey("50 Games Played ttt"));
    }

    @Test
    void getRating() throws SQLException, NoSuchAlgorithmException, IOException {
        String username = Profile.generateUsername();
        String newUsername = Profile.generateUsername();
        String email = username + "@example.com";
        String password = Profile.generatePassword();
        Profile profile = ProfileCreation.createNewProfile(username, email, password);
        int rating = PlayerRanking.getRating(profile.getID(), TTT_INDEX);
        assertEquals(0, rating);
    }

    @Test
    void getRank() throws SQLException, NoSuchAlgorithmException, IOException {
        String username = Profile.generateUsername();
        String email = username + "@example.com";
        String password = Profile.generatePassword();
        Profile profile = ProfileCreation.createNewProfile(username, email, password);
        PlayerRanking.setGameRating(profile.getID(), TTT_INDEX, 900);
        assertEquals(900, PlayerRanking.getRating(profile.getID(), TTT_INDEX));
        assertEquals("Silver", PlayerRanking.getRank(PlayerRanking.getRating(profile.getID(), TTT_INDEX)));
    }
}