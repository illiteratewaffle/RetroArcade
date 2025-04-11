package client_main.java.AuthenticationAndProfile;

import leaderboard.PlayerRanking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import AuthenticationAndProfile.*;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    private Profile profile;

    @BeforeEach
    void setUp() {
        try {
            HashMap<String, Double> achievementProgress = new HashMap<>();
            List<String> gameHistory = new ArrayList<>();
            List<Integer> friends = Arrays.asList(101, 102, 103);
            List<Integer> friendRequests = Arrays.asList(201, 202);
            String password = "1234567";

            String hashedPassword = ProfileCreation.hashedPassword(password);

            profile = new Profile("test@example.com", hashedPassword, "nickname", "This is bio.", false, "currentGame", new FriendsList(friends, friendRequests, 2), new AuthenticationAndProfile.PlayerRanking(), new GameHistory(gameHistory, achievementProgress), "C:profile/pic/path.png", "username", 2);
        }
        catch (Exception e) { }
    }



    @Test
    void getEmail() {
        assertEquals("test@example.com", profile.getEmail());
    }

    @Test
    void setEmail() {
        try{
        profile.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", profile.getEmail());
        }
        catch (Exception e) { fail(e.getMessage()); }
    }

    @Test
    void getHashedPassword() {
        /*HashMap<String, Double> achievementProgress = new HashMap<>();
        List<String> gameHistory = new ArrayList<>();
        List<Integer> friends = Arrays.asList(101, 102, 103);
        List<Integer> friendRequests = Arrays.asList(201, 202);
        String password = "WhatAGoodPassword!";
        try{
        String hashedPassword = ProfileCreation.hashedPassword(password);

        Profile profile2 = new Profile("test@example.com", hashedPassword, "nick", "This is bio.", false, "currentGame", new FriendsList(friends, friendRequests), new PlayerRanking(), new GameHistory(gameHistory, achievementProgress), "C:profile/pic/path.png", "username", 2);


        assertEquals(hashedPassword, profile2.getHashedPassword());
        }
        catch (Exception e) { fail(e.getMessage()); }*/
    }

    @Test
    void setHashedPassword() {
        try{
        profile.setHashedPassword("newHashedPassword");
        assertEquals("newHashedPassword", profile.getHashedPassword());
        }
        catch (Exception e) { fail(e.getMessage()); }
    }

    @Test
    void getNickname() {
        assertEquals("nickname", profile.getNickname());
    }

    @Test
    void setNickname() {
        try{
        profile.setNickname("newNickname");
        assertEquals("newNickname", profile.getNickname());
        }
        catch (Exception e) { fail(e.getMessage()); }
    }

    @Test
    void getBio() {
        assertEquals("This is bio.", profile.getBio());
    }

    @Test
    void setBio() {
        try{
        profile.setBio("newBio");
        assertEquals("newBio", profile.getBio());
        }
        catch (Exception e) { fail(e.getMessage()); }
    }

    @Test
    void setOnlineStatus() {
        try{
        profile.setOnlineStatus(false);
        assertFalse(profile.getOnlineStatus());
        }
        catch (Exception e) { fail(e.getMessage()); }
    }

    @Test
    void getCurrentStatus() {
        try{
        profile.setOnlineStatus(true);
        profile.setCurrentGame("newGame");
        profile.getCurrentStatus();
        }
        catch (Exception e) { fail(e.getMessage()); }
    }

    @Test
    void getCurrentGame() {
        assertEquals("currentGame", profile.getCurrentGame());
    }

    @Test
    void setCurrentGame() {
        try{
        profile.setCurrentGame("newGame");
        assertEquals("newGame", profile.getCurrentGame());
        }
        catch (Exception e) { fail(e.getMessage()); }
    }

    @Test
    void getGamesPlayed() {
        assertNotNull(profile.getGameHistory());
    }

    @Test
    void setGamesPlayed() {
        /*
        List<String> gameHistory = new ArrayList<>();
        GameHistory newGamesPlayed = new GameHistory(gameHistory);
        profile.setGameHistory(newGamesPlayed);
        assertEquals(newGamesPlayed, profile.getGameHistory());
        */
        fail();
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
        try{
        assertEquals("username", Profile.exportUsername(1));
        }
        catch (Exception e) { fail(e.getMessage()); }
    }

    @Test
    void updateUsernameInDatabase() {
        try{
        profile.updateUsername(6, "Alice");
        assertEquals("Alice", profile.exportUsername(6));
        }
        catch (Exception e) { fail(e.getMessage()); }
    }

    @Test
    void setAndGetFriendsList() {
        /*
        List<Integer> friends = Arrays.asList(101, 102);
        List<Integer> friendRequests = Arrays.asList(201, 202);
        FriendsList friendsList = new FriendsList(friends, friendRequests);
        profile.setFriendsList(friendsList);
        assertEquals(friendsList, profile.getFriendsList());
        */
        fail();
    }



}