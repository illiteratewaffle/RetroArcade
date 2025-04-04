package client_main.java.AuthenticationAndProfile;

import leaderboard.PlayerRanking;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.player.Player;
import server.player.PlayerManager;
import AuthenticationAndProfile.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfileDatabaseAccessTest {
    private static int id;

    @BeforeEach
    void setUp() {
        id = PlayerManager.registerPlayer("username", "email@email.com", "12345678");
    }

//    @AfterAll
//    static void tearDown() {
//        PlayerManager.deleteProfile(id);
//    }

    @Test
    void obtainProfile() {
        //String hashedPassword = ProfileCreation.hashedPassword("1234567");
        Profile profile = new Profile("email1@email.com", "12345678910", "null", "null",
                false, "null", new FriendsList(), new PlayerRanking(), new GameHistory(), "null", "username1", 1);

        int id1 = PlayerManager.registerPlayer("username1", "email1@email.com", "12345678910");
        assertEquals(profile.getEmail(), ProfileDatabaseAccess.obtainProfile(id1).getEmail());
        assertEquals(profile.getUsername(), ProfileDatabaseAccess.obtainProfile(id1).getUsername());
        assertEquals(profile.getOnlineStatus(), ProfileDatabaseAccess.obtainProfile(id1).getOnlineStatus());
        PlayerManager.deleteProfile(id1);
    }

    @Test
    void obtainFriendsList() {
    }

    @Test
    void obtainPlayerRanking() {
    }

    @Test
    void obtainGameHistory() {
    }

    @Test
    void removeProfile() {
    }

    @Test
    void getAllProfiles() {
    }

    @Test
    void searchForProfile() {
    }

    @Test
    void viewPersonalProfile() {
    }

    @Test
    void viewOtherProfile() {
    }
}