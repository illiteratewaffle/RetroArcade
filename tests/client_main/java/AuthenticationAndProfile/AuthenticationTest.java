package client_main.java.AuthenticationAndProfile;

import AuthenticationAndProfile.*;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Test;
import player.PlayerManager;

import static org.junit.jupiter.api.Assertions.*;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

class AuthenticationTest {
    private static Profile profile;

//    @BeforeEach
//    void setUp() {
//        try {
//            HashMap<String, Double> achievementProgress = new HashMap<>();
//            List<String> gameHistory = new ArrayList<>();
//            String password = "1234567";
//            String hashedPassword = ProfileCreation.hashedPassword(password);
//            profile = new Profile("email1@email.com", hashedPassword, "nick", "This is bio.", false, "null", new FriendsList(), new PlayerRanking(), new GameHistory(gameHistory, achievementProgress), "C:profile/pic/path.png", "username1", 2);
//        } catch (NoSuchAlgorithmException n) {
//            System.out.println(n.getMessage());
//        }
//    }

    @AfterEach
    void tearDown() {
        try {
            PlayerManager.deleteProfile(profile.getID());
            profile = null;
        } catch (SQLException s) {
            fail("deleteProfile error: " + s.getMessage());
        }
    }

    @Test
    void logIn() {
        try {
            int id = PlayerManager.registerPlayer("username5", "email5@email.com", ProfileCreation.hashedPassword("1234567"));
            profile = ProfileDatabaseAccess.obtainProfile(id);
            assertEquals("username5", Authentication.logIn("username5", "1234567").getUsername());
            assertEquals("email5@email.com", Authentication.logIn("username5", "1234567").getEmail());
        } catch (SQLException s) {
            fail("registerPlayer or logIn error: " + s.getMessage());
        } catch (NoSuchAlgorithmException | IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void logOut() {
        try {
            int id = PlayerManager.registerPlayer("username5", "email5@email.com", ProfileCreation.hashedPassword("1234567"));
            profile = ProfileDatabaseAccess.obtainProfile(id);
            Authentication.logOut(id);
            assertFalse(ProfileDatabaseAccess.obtainProfile(id).getOnlineStatus());
        } catch (SQLException | NoSuchAlgorithmException | IOException e) {
            fail(e.getMessage());
        }
    }
}