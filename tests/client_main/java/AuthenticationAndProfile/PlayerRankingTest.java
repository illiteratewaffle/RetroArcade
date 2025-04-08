package client_main.java.AuthenticationAndProfile;

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
    void getRating() throws SQLException, NoSuchAlgorithmException, IOException {
        Profile profile = ProfileCreation.createNewProfile("testUser", "test@email.com", "testPassword");
        int rating = PlayerRanking.getRating(profile.getID(), TTT_INDEX);
        assertEquals(0, rating);
    }

    @Test
    void getRank() throws SQLException, NoSuchAlgorithmException, IOException {
        String username = Profile.generateUsername();
        String email = username + "@example.com";
        String password = Profile.generatePassword();
        Profile profile = ProfileCreation.createNewProfile(username, email, password);
        PlayerRanking.setGameRating(profile.getID(), TTT_INDEX, 1500);
        assertEquals(1500, PlayerRanking.getRating(profile.getID(), TTT_INDEX));
    }
}