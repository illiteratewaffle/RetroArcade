package client_main_tests.java.AuthenticationAndProfile;

import leaderboard.PlayerRanking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import AuthenticationAndProfile.*;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    private Profile profile;

    @BeforeEach
    void setUp() {
        List<Long> friends = Arrays.asList(101L, 102L, 103L); // Example friend IDs
        List<Long> friendRequests = Arrays.asList(201L, 202L); // Example friend request IDs
        String password = "1234567";
        String hashedPassword = ProfileCreation.hashedPassword(password);
        profile = new Profile("test@example.com", hashedPassword,"nickname", "This is bio.", false, "currentGame", new FriendsList(friends, friendRequests), new PlayerRanking(), new GameHistory(), "C:profile/pic/path.png", "username", 2 );
    }


    @Test
    void getEmail() {
        assertEquals("test@example.com", profile.getEmail());
    }

    @Test
    void setEmail() {
        profile.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", profile.getEmail());
    }

    @Test
    void getHashedPassword() {

        List<Long> friends = Arrays.asList(101L, 102L, 103L); // Example friend IDs
        List<Long> friendRequests = Arrays.asList(201L, 202L); // Example friend request IDs
        String password = "WhatAGoodPassword!";
        String hashedPassword = ProfileCreation.hashedPassword(password);
        Profile profile2 = new Profile("test@example.com", hashedPassword, "nick", "This is bio.", false, "currentGame", new FriendsList(friends, friendRequests), new PlayerRanking(), new GameHistory(), "C:profile/pic/path.png", "username", 2);


        assertEquals(hashedPassword, profile2.getHashedPassword());
    }

    @Test
    void setHashedPassword() {
        profile.setHashedPassword("newHashedPassword");
        assertEquals("newHashedPassword", profile.getHashedPassword());
    }

    @Test
    void getNickname() {
        assertEquals("nickname", profile.getNickname());
    }

    @Test
    void setNickname() {
        profile.setNickname("newNickname");
        assertEquals("newNickname", profile.getNickname());
    }

    @Test
    void getBio() {
        assertEquals("This is bio.", profile.getBio());
    }

    @Test
    void setBio() {
        profile.setBio("newBio");
        assertEquals("newBio", profile.getBio());
    }

    @Test
    void setOnlineStatus() {
        profile.setOnlineStatus(false);
        assertFalse(profile.getOnlineStatus());
    }

    @Test
    void getCurrentStatus() {
        profile.setOnlineStatus(true);
        profile.setCurrentGame("newGame");
        profile.getCurrentStatus();
    }

    @Test
    void getCurrentGame() {
        assertEquals("currentGame", profile.getCurrentGame());
    }

    @Test
    void setCurrentGame() {
        profile.setCurrentGame("newGame");
        assertEquals("newGame", profile.getCurrentGame());
    }

    @Test
    void getGamesPlayed() {
        assertNotNull(profile.getGameHistory());
    }

    @Test
    void setGamesPlayed() {
        GameHistory newGamesPlayed = new GameHistory();
        profile.setGameHistory(newGamesPlayed);
        assertEquals(newGamesPlayed, profile.getGameHistory());
    }

    @Test
    void getProfilePic() {
        assertEquals("C:profile/pic/path.png", profile.getProfilePicFilePath());
    }

//    @Test
//    void setProfilePic() {
//        BufferedImage newProfilePic = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
//        profile.setProfilePicFilePath("C:/Pictures/Pictures/profile.png");
//        assertEquals(newProfilePic, profile.getProfilePicFilePath());
//    }

    @Test
    void exportUsername() {
        assertEquals("username", Profile.exportUsername(1));
    }

    @Test
    void updateUsernameInDatabase() {
        profile.updateUsername(6, "Alice");
        assertEquals("Alice", profile.exportUsername(6));
    }

    @Test
    void setAndGetFriendsList() {
        List<Long> friends = Arrays.asList(101L, 102L, 103L); // Example friend IDs
        List<Long> friendRequests = Arrays.asList(201L, 202L); // Example friend request IDs
        FriendsList friendsList = new FriendsList(friends, friendRequests);
        profile.setFriendsList(friendsList);
        assertEquals(friendsList, profile.getFriendsList());
    }



}