package client_main_tests.java.AuthenticationAndProfile;

import AuthenticationAndProfile.PlayerRanking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        HashMap<String, Double> achievementProgress = new HashMap<>();
        List<String> gameHistory = new ArrayList<>();
        List<Integer> friends = Arrays.asList(101, 102, 103);
        List<Integer> friendRequests = Arrays.asList(201, 202);
        String password = "1234567";
        String hashedPassword = ProfileCreation.hashedPassword(password);
        profile = new Profile("test@example.com", hashedPassword,"nickname", "This is bio.", false, "currentGame", new FriendsList(friends, friendRequests), new PlayerRanking(), new GameHistory(gameHistory, achievementProgress), "C:profile/pic/path.png", "username", 2 );
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
        List<String> gameHistory = new ArrayList<>();
        HashMap<String, Double> achievementProgress = new HashMap<>();
        List<Integer> friends = Arrays.asList(101, 102, 103);
        List<Integer> friendRequests = Arrays.asList(201, 202);
        String password = "WhatAGoodPassword!";
        String hashedPassword = ProfileCreation.hashedPassword(password);
        Profile profile2 = new Profile("test@example.com", hashedPassword, "nick", "This is bio.", false, "currentGame", new FriendsList(friends, friendRequests), new PlayerRanking(), new GameHistory(gameHistory, achievementProgress), "C:profile/pic/path.png", "username", 2);


        assertEquals(hashedPassword, profile2.getHashedPassword());
    }

    @Test
    void setHashedPassword() {
        profile.setHashedPassword("newHashedPassword");
        assertEquals(ProfileCreation.hashedPassword("newHashedPassword"), profile.getHashedPassword());
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
        List<String> gameHistory = new ArrayList<>();
        HashMap<String, Double> achievementProgress = new HashMap<>();
        achievementProgress.put("Win_Streak", 0.75);
        achievementProgress.put("Matches_Played", 0.50);
        GameHistory newGamesPlayed = new GameHistory(gameHistory, achievementProgress);
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
        String username = Profile.generateUsername();
        String email = username + "@example.com";
        String password = Profile.generatePassword();
        ProfileCreation.createNewProfile(username, email, password);
        String hashedPassword = ProfileCreation.hashedPassword(password);
        int newProfileID = Authentication.getProfileLoggedIn().getID();
        assertEquals(username, Profile.exportUsername(newProfileID));
    }

    @Test
    void updateUsernameInDatabase() {
        String username = Profile.generateUsername();
        String newUsername = Profile.generateUsername();
        String email = username + "@example.com";
        String password = Profile.generatePassword();
        ProfileCreation.createNewProfile(username, email, password);
        String hashedPassword = ProfileCreation.hashedPassword(password);
        int newProfileID = Authentication.getProfileLoggedIn().getID();
        assertEquals(username, Profile.exportUsername(newProfileID));
        profile.updateUsername(newProfileID, newUsername);
        assertEquals(newUsername, profile.exportUsername(newProfileID));
    }

    @Test
    void setAndGetFriendsList() {
        List<Integer> friends = Arrays.asList(101, 102);
        List<Integer> friendRequests = Arrays.asList(201, 202);
        FriendsList friendsList = new FriendsList(friends, friendRequests);
        profile.setFriendsList(friendsList);
        assertEquals(friendsList, profile.getFriendsList());
    }



}