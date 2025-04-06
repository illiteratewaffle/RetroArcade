package client_main_tests.java.AuthenticationAndProfile;

import AuthenticationAndProfile.PlayerRanking;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.PlayerManager;
import static org.junit.jupiter.api.Assertions.*;
import AuthenticationAndProfile.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class AuthenticationTest {
    private static Profile profile;
    @BeforeEach
    void setUp() {
        HashMap<String, Double> achievementProgress = new HashMap<>();
        List<String> gameHistory = new ArrayList<>();
        String password = "1234567";
        String hashedPassword = ProfileCreation.hashedPassword(password);
        profile = new Profile("email1@email.com", hashedPassword,"nick", "This is bio.", false, "null", new FriendsList(), new PlayerRanking(), new GameHistory(gameHistory, achievementProgress), "C:profile/pic/path.png", "username", 2 );
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
            int id = PlayerManager.registerPlayer("username1", "email@email.com", ProfileCreation.hashedPassword("1234567"));
            Profile profile1 = ProfileDatabaseAccess.obtainProfile(id);
            assertEquals(profile1, Authentication.logIn("username1", "1234567"));
            ProfileDatabaseAccess.removeProfile(id);
        } catch (SQLException s) {
            System.out.println("registerPlayer or logIn error: " + s.getMessage());
        }
    }

//    @Test
//    void logOut() {
//        Authentication.setProfileLoggedIn(profile);
//        Authentication.logOut();
//        assertNull(Authentication.getProfileLoggedIn());
//    }

//    @Test
//    void getProfileLoggedIn() {
//        Authentication.setProfileLoggedIn(profile);
//        assertEquals(profile, Authentication.getProfileLoggedIn());
//    }

//    @Test
//    void setProfileLoggedIn() {
//        //Profile profile = new Profile("email@email.com", "46#B286734A8%367","nick", "This is bio.", false, "null", new FriendsList(), new PlayerRanking(), new GameHistory(), "C:profile/pic/path.png", "username", 2 );
//        Authentication.setProfileLoggedIn(profile);
//        assertEquals(profile, Authentication.getProfileLoggedIn());
//    }
}