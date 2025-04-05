package client_main.java.AuthenticationAndProfile;

import leaderboard.PlayerRanking;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.player.Player;
import server.player.PlayerManager;
import AuthenticationAndProfile.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfileDatabaseAccessTest {
    private static int id;
    private static Profile profile;

    @BeforeEach
    void setUp() {
        id = PlayerManager.registerPlayer("username", "email@email.com", "12345678");
        profile = ProfileDatabaseAccess.obtainProfile(id);
    }

    @AfterAll
    static void tearDown() {
        PlayerManager.deleteProfile(id);
    }

    @Test
    void obtainProfile() {
        //String hashedPassword = ProfileCreation.hashedPassword("1234567");
        Profile profile1 = new Profile("email1@email.com", "12345678910", "null", "null",
                false, "null", new FriendsList(), new PlayerRanking(), new GameHistory(), "null", "username1", 1);

        int id1 = PlayerManager.registerPlayer("username1", "email1@email.com", "12345678910");
        assertEquals(profile1.getEmail(), ProfileDatabaseAccess.obtainProfile(id1).getEmail());
        assertEquals(profile1.getUsername(), ProfileDatabaseAccess.obtainProfile(id1).getUsername());
        assertEquals(profile1.getOnlineStatus(), ProfileDatabaseAccess.obtainProfile(id1).getOnlineStatus());
        PlayerManager.deleteProfile(id1);
    }

    @Test
    void obtainFriendsList() {
        System.out.println(PlayerManager.updateAttribute(id, "friends", "[1,2,3,4]"));
        System.out.println(PlayerManager.updateAttribute(id, "friend_requests", "[7,8]"));
        List<Integer> friends = new ArrayList<>(Arrays.asList(1,2,3,4));
        List<Integer> friendRequests = new ArrayList<>(Arrays.asList(7,8));
        assertEquals(friends, ProfileDatabaseAccess.obtainFriendsList(id).getFriends());
        assertEquals(friendRequests, ProfileDatabaseAccess.obtainFriendsList(id).getFriendRequests());
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