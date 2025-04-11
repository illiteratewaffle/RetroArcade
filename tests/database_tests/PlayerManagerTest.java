package database_tests;

import org.junit.jupiter.api.*;
import player.PlayerManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/// /////
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PlayerManagerTest {
//
    // List to keep track of created test profile IDs for cleanup
    private List<Integer> createdPlayerIds;

    @BeforeEach
    void setup() {
        createdPlayerIds = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        // Delete each test profile from the database
        for (int id : createdPlayerIds) {
            try {
                PlayerManager.deleteProfile(id);
            } catch (SQLException e) {
                System.err.println("Cleanup: Error deleting test profile with id: " + id);
            }
            // Also delete the corresponding CSV file if it exists
            File profileCsv = new File("player_profile_" + id + ".csv");
            if (profileCsv.exists()) {
                profileCsv.delete();
            }
        }
        // Delete export CSV file if it exists
        File exportCsv = new File("profiles_export.csv");
        if (exportCsv.exists()) {
            exportCsv.delete();
        }
    }

    @Test
    void testRegisterPlayer() throws SQLException {
        String username = "testuser";
        String email = "testuser@example.com";
        int id = PlayerManager.registerPlayer(username, email, "hashedpass");
        createdPlayerIds.add(id);

        assertTrue(id > 0, "Player ID should be a positive integer.");
    }

    @Test
    void testAuthenticatePlayer() throws SQLException {
        String username = "authUser";
        String email = "authUser@example.com";
        int id = PlayerManager.registerPlayer(username, email, "hashedpass");
        createdPlayerIds.add(id);

        // Correct credentials
        int authId = PlayerManager.authenticatePlayer(username, "hashedpass");
        assertEquals(id, authId, "Authentication should return the correct player ID for valid credentials.");

        // Incorrect password returns -1
        int failedAuth = PlayerManager.authenticatePlayer(username, "wrongpass");
        assertEquals(-1, failedAuth, "Authentication should fail (-1) for invalid credentials.");
    }

    @Test
    void testGetProfileCSV() throws SQLException, IOException {
        String username = "profileUser";
        String email = "profileUser@example.com";
        int id = PlayerManager.registerPlayer(username, email, "hashedpass");
        createdPlayerIds.add(id);

        // Generate CSV export for single profile
        PlayerManager.getProfile(id);

        File csvFile = new File("player_profile_" + id + ".csv");
        assertTrue(csvFile.exists(), "CSV file for player profile should exist.");

        // Optionally, check content (for example, header contains expected column names)
        String content = Files.readString(csvFile.toPath());
        assertTrue(content.contains("username"), "CSV file should contain header 'username'.");
    }

    @Test
    void testGetProfileTableCSV() throws SQLException {
        // Create two profiles so that the profiles table is not empty
        String username1 = "tableUser1";
        String email1 = "tableUser1@example.com";
        int id1 = PlayerManager.registerPlayer(username1, email1, "hashedpass");
        createdPlayerIds.add(id1);

        String username2 = "tableUser2";
        String email2 = "tableUser2@example.com";
        int id2 = PlayerManager.registerPlayer(username2, email2, "hashedpass");
        createdPlayerIds.add(id2);

        // Generate CSV export for the entire profile table
        PlayerManager.getProfileTable();

        File csvFile = new File("profiles_export.csv");
        assertTrue(csvFile.exists(), "CSV export file 'profiles_export.csv' should exist.");
    }

    @Test
    void testSearchProfiles() throws SQLException {
        String substring = "alpha";
        String username1 = "alphaUser1";
        String email1 = "alphaUser1@example.com";
        int id1 = PlayerManager.registerPlayer(username1, email1, "hashedpass");
        createdPlayerIds.add(id1);

        String username2 = "alphaUser2";
        String email2 = "alphaUser2@example.com";
        int id2 = PlayerManager.registerPlayer(username2, email2, "hashedpass");
        createdPlayerIds.add(id2);

        List<Integer> foundProfiles = PlayerManager.searchProfiles(substring);

        // Check that both created IDs are in the results
        assertTrue(foundProfiles.contains(id1), "Search results should contain first test profile.");
        assertTrue(foundProfiles.contains(id2), "Search results should contain second test profile.");
    }

    @Test
    void testGetUsername() throws SQLException {
        String username = "nameUser";
        String email = "nameUser@example.com";
        int id = PlayerManager.registerPlayer(username, email, "hashedpass");
        createdPlayerIds.add(id);

        String fetchedUsername = PlayerManager.getUsername(id);
        assertEquals(username, fetchedUsername, "Fetched username should match the registered username.");
    }

    @Test
    void testUpdateAndGetAttribute() throws SQLException {
        // Assumes that the profiles table has a 'bio' column.
        String username = "attrUser";
        String email = "attrUser@example.com";
        int id = PlayerManager.registerPlayer(username, email, "hashedpass");
        createdPlayerIds.add(id);

        String testBio = "This is a test bio.";
        String updateStatus = PlayerManager.updateAttribute(id, "bio", testBio);
        assertTrue(updateStatus.contains("updated"), "Update status should indicate a successful update.");

        String fetchedBio = PlayerManager.getAttribute(id, "bio");
        assertEquals(testBio, fetchedBio, "Fetched bio should match the updated bio.");
    }

    @Test
    void testAddAndSearchFriendsList() throws SQLException {
        // Create two players for friendship test.
        String username1 = "friendUser1";
        String email1 = "friendUser1@example.com";
        int id1 = PlayerManager.registerPlayer(username1, email1, "hashedpass");
        createdPlayerIds.add(id1);

        String username2 = "friendUser2";
        String email2 = "friendUser2@example.com";
        int id2 = PlayerManager.registerPlayer(username2, email2, "hashedpass");
        createdPlayerIds.add(id2);

        // Add player2 as a friend to player1.
        String addResult = PlayerManager.addToFriendsList(id1, id2);
        assertTrue(addResult.contains("added player"), "Friend addition should succeed.");

        // Search in player1's friends list using a substring from player2's username.
        List<Integer> friendList = PlayerManager.searchFriendsList(id1, "friend");
        assertTrue(friendList.contains(id2), "Friends list should contain the added friend.");
    }

    @Test
    void testAddAndDeleteFriend() throws SQLException {
        // Create two players
        String username1 = "delFriendUser1";
        String email1 = "delFriendUser1@example.com";
        int id1 = PlayerManager.registerPlayer(username1, email1, "hashedpass");
        createdPlayerIds.add(id1);

        String username2 = "delFriendUser2";
        String email2 = "delFriendUser2@example.com";
        int id2 = PlayerManager.registerPlayer(username2, email2, "hashedpass");
        createdPlayerIds.add(id2);

        // Add as friend then delete
        PlayerManager.addToFriendsList(id1, id2);
        String deleteStatus = PlayerManager.deleteFriend(id1, id2);
        assertTrue(deleteStatus.contains("removed"), "Friend deletion should return a successful message.");

        // Verify friend is no longer in the list.
        List<Integer> friendList = PlayerManager.searchFriendsList(id1, "delFriend");
        assertFalse(friendList.contains(id2), "Friends list should not contain the friend after deletion.");
    }

    @Test
    void testAddAndDeleteFriendRequest() throws SQLException {
        // Create two players
        String username1 = "reqUser1";
        String email1 = "reqUser1@example.com";
        int id1 = PlayerManager.registerPlayer(username1, email1, "hashedpass");
        createdPlayerIds.add(id1);

        String username2 = "reqUser2";
        String email2 = "reqUser2@example.com";
        int id2 = PlayerManager.registerPlayer(username2, email2, "hashedpass");
        createdPlayerIds.add(id2);

        // Add friend request then delete it.
        String addStatus = PlayerManager.addToFriendRequests(id1, id2);
        assertTrue(addStatus.contains("added player"), "Friend request addition should succeed.");

        String deleteStatus = PlayerManager.deleteFriendRequest(id1, id2);
        assertTrue(deleteStatus.contains("removed"), "Friend request deletion should return a successful message.");

        // Verify that friend_requests does not contain id2.
        String friendRequests = PlayerManager.getAttribute(id1, "friend_requests");
        if (friendRequests != null) {
            assertFalse(friendRequests.contains(String.valueOf(id2)), "Friend requests should not contain the deleted friend request.");
        }
    }

    @Test
    void testAddToGamesPlayed() throws SQLException {
        String username = "gameUser";
        String email = "gameUser@example.com";
        int id = PlayerManager.registerPlayer(username, email, "hashedpass");
        createdPlayerIds.add(id);

        String gameName = "TestGame";
        String addStatus = PlayerManager.addToGamesPlayed(id, gameName);
        assertTrue(addStatus.contains("added game"), "Adding game played should return a successful message.");

        String gamesPlayed = PlayerManager.getAttribute(id, "games_played");
        assertNotNull(gamesPlayed, "Games played attribute should not be null.");
        assertTrue(gamesPlayed.contains(gameName), "Games played should contain the added game.");
    }

    @Test
    void testSetAchievementProgress() throws SQLException {
        String username = "achieveUser";
        String email = "achieveUser@example.com";
        int id = PlayerManager.registerPlayer(username, email, "hashedpass");
        createdPlayerIds.add(id);

        String achievementName = "FirstWin";
        String progress = "100";
        String status = PlayerManager.setAchievementProgress(id, achievementName, progress);
        assertTrue(status.contains("Set achievement"), "Achievement progress should be set successfully.");

        String achievementData = PlayerManager.getAttribute(id, "achievement_progress");
        assertNotNull(achievementData, "Achievement progress attribute should not be null.");
        assertTrue(achievementData.contains(achievementName), "Achievement progress data should contain the achievement name.");
        assertTrue(achievementData.contains(progress), "Achievement progress data should contain the progress value.");
    }

    @Test
    void testGetProfileID() throws SQLException {
        String username = "profileIDUser";
        String email = "profileIDUser@example.com";
        int id = PlayerManager.registerPlayer(username, email, "hashedpass");
        createdPlayerIds.add(id);

        int fetchedId = PlayerManager.getProfileID(username);
        assertEquals(id, fetchedId, "getProfileID should return the same ID as that of the registered profile.");
    }
}