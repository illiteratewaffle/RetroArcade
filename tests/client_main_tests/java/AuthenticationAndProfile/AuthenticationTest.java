package client_main_tests.java.AuthenticationAndProfile;

import AuthenticationAndProfile.*;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.PlayerManager;

import static org.junit.jupiter.api.Assertions.*;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class AuthenticationTest {
    private static Profile profile;

    @BeforeEach
    void setUp() {
        try {
            HashMap<String, Double> achievementProgress = new HashMap<>();
            List<String> gameHistory = new ArrayList<>();
            String password = "1234567";
            String hashedPassword = ProfileCreation.hashedPassword(password);
            profile = new Profile("email1@email.com", hashedPassword, "nick", "This is bio.", false, "null", new FriendsList(), new PlayerRanking(), new GameHistory(gameHistory, achievementProgress), "C:profile/pic/path.png", "username1", 2);
        } catch (NoSuchAlgorithmException n) {
            System.out.println(n.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try {
            PlayerManager.deleteProfile(profile.getID());
        } catch (SQLException s){
            System.out.println("deleteProfile error: " + s.getMessage());
        }
    }

    @Test
    void logIn() {
        try {
            int id = PlayerManager.registerPlayer("username2", "email2@email.com", ProfileCreation.hashedPassword("1234567"));
            Profile profile1 = ProfileDatabaseAccess.obtainProfile(id);
            assertEquals(profile1, Authentication.logIn("username2", "1234567"));
            PlayerManager.deleteProfile(id);
        } catch (SQLException s) {
            System.out.println("registerPlayer or logIn error: " + s.getMessage());
        } catch (NoSuchAlgorithmException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void logOut() {
        try {
            int id = PlayerManager.registerPlayer("username2", "email2@email.com", ProfileCreation.hashedPassword("1234567"));
            Profile profile1 = ProfileDatabaseAccess.obtainProfile(id);
            Authentication.logOut(id);
            assertFalse(ProfileDatabaseAccess.obtainProfile(id).getOnlineStatus());
            PlayerManager.deleteProfile(id);
        } catch (SQLException | NoSuchAlgorithmException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}