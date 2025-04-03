package client_main.java.AuthenticationAndProfile;

import AuthenticationAndProfile.*;
import leaderboard.PlayerRanking;
import org.junit.jupiter.api.Test;
import server.player.PlayerManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfileDatabaseAccessTest {
    private int id = PlayerManager.registerPlayer("username", "email@email.com", "12345678");

    @Test
    void obtainProfile() {
        String hashedPassword = ProfileCreation.hashedPassword("1234567");
        Profile profile = new Profile("email@email.com", hashedPassword, "null", "null", false, "null", new FriendsList(), new PlayerRanking(), new GameHistory(), "null", "username", 1 );

        PlayerManager.registerPlayer();
        assertEquals(, ProfileDatabaseAccess.obtainProfile(id));
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