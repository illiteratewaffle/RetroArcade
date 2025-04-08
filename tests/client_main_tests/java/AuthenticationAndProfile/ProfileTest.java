package client_main_tests.java.AuthenticationAndProfile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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
            int id = 0;
            profile = new Profile("test@example.com", hashedPassword, "nickname", "This is bio.", false, "currentGame", new FriendsList(), new PlayerRanking(), new GameHistory(), "C:profile/pic/path.png", "username", 2);
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }
    }


    @Test
    void getEmail() {
        assertEquals("test@example.com", profile.getEmail());
    }

    @Test
    void setEmail() {
        try {
            profile.setEmail("newemail@example.com");
            assertEquals("newemail@example.com", profile.getEmail());
        } catch (SQLException s) {
            fail(s.getMessage());
        }
    }

    @Test
    void getHashedPassword() {
        try {
            List<String> gameHistory = new ArrayList<>();
            HashMap<String, Double> achievementProgress = new HashMap<>();
            List<Integer> friends = Arrays.asList(101, 102, 103);
            List<Integer> friendRequests = Arrays.asList(201, 202);
            String password = "WhatAGoodPassword!";
            String hashedPassword = ProfileCreation.hashedPassword(password);
            int id = 0;
            Profile profile2 = new Profile("test@example.com", hashedPassword, "nick", "This is bio.", false, "currentGame", new FriendsList(friends, friendRequests, id), new PlayerRanking(), new GameHistory(gameHistory, achievementProgress, id), "C:profile/pic/path.png", "username", 2);


            assertEquals(hashedPassword, profile2.getHashedPassword());
        } catch (NoSuchAlgorithmException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void setHashedPassword() {
        try {
            profile.setHashedPassword("newHashedPassword");
            assertEquals(ProfileCreation.hashedPassword("newHashedPassword"), profile.getHashedPassword());
        } catch (SQLException | NoSuchAlgorithmException s) {
            fail(s.getMessage());
        }
    }

    @Test
    void getNickname() {
        assertEquals("nickname", profile.getNickname());
    }

    @Test
    void setNickname() {
        try {
            profile.setNickname("newNickname");
            assertEquals("newNickname", profile.getNickname());
        } catch (SQLException s) {
            fail(s.getMessage());
        }
    }

    @Test
    void getBio() {
        assertEquals("This is bio.", profile.getBio());
    }

    @Test
    void setBio() {
        try {
            profile.setBio("newBio");
            assertEquals("newBio", profile.getBio());
        } catch (SQLException s) {
            fail(s.getMessage());
        }
    }

    @Test
    void setOnlineStatus() {
        try {
            profile.setOnlineStatus(false);
            assertFalse(profile.getOnlineStatus());
        } catch (SQLException s) {
            fail(s.getMessage());
        }
    }

    @Test
    void getCurrentStatus() {
        try {
            profile.setOnlineStatus(true);
            profile.setCurrentGame("newGame");
            profile.getCurrentStatus();
        } catch (SQLException s) {
            fail(s.getMessage());
        }
    }

    @Test
    void getCurrentGame() {
        assertEquals("currentGame", profile.getCurrentGame());
    }

    @Test
    void setCurrentGame() {
        try {
            profile.setCurrentGame("newGame");
            assertEquals("newGame", profile.getCurrentGame());
        } catch (SQLException s) {
            fail(s.getMessage());
        }
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
        GameHistory newGamesPlayed = new GameHistory(gameHistory, achievementProgress, profile.getID());
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
        try {
            Profile profile1 = ProfileCreation.createNewProfile(username, email, password);
            String hashedPassword = ProfileCreation.hashedPassword(password);
            int newProfileID = profile1.getID();
            assertEquals(username, Profile.exportUsername(newProfileID));
        } catch (SQLException | NoSuchAlgorithmException s) {
            fail(s.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void updateUsernameInDatabase() {
        String username = Profile.generateUsername();
        String newUsername = Profile.generateUsername();
        String email = username + "@example.com";
        String password = Profile.generatePassword();
        try {
            Profile profile1 = ProfileCreation.createNewProfile(username, email, password);
            String hashedPassword = ProfileCreation.hashedPassword(password);
            int newProfileID = profile1.getID();
            assertEquals(username, Profile.exportUsername(newProfileID));
            profile.updateUsername(newProfileID, newUsername);
            assertEquals(newUsername, profile.exportUsername(newProfileID));
        } catch (SQLException | NoSuchAlgorithmException | IOException s) {
            fail(s.getMessage());
        }
    }

    @Test
    void setAndGetFriendsList() {
        List<Integer> friends = Arrays.asList(101, 102);
        List<Integer> friendRequests = Arrays.asList(201, 202);
        int id = 1;
        FriendsList friendsList = new FriendsList(friends, friendRequests, id);
        profile.setFriendsList(friendsList);
        assertEquals(friendsList, profile.getFriendsList());
    }



}