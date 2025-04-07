package client_main_tests.java.AuthenticationAndProfile;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.PlayerManager;
import AuthenticationAndProfile.*;

import java.sql.SQLException;
import java.util.*;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ProfileDatabaseAccessTest {
    private static int id;
    private static Profile profile;

    @BeforeEach
    void setUp() {
        try {
            id = PlayerManager.registerPlayer("username2", "email2@email.com", "12345678");
            profile = ProfileDatabaseAccess.obtainProfile(id);
        } catch (SQLException s) {
            System.out.println("register player error: " + s.getMessage());
        }
    }

    @AfterAll
    static void tearDown() {
        try {
            PlayerManager.deleteProfile(id);
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    @Test
    void obtainProfile() {
        HashMap<String, Double> achievementProgress = new HashMap<>();
        List<String> gameHistory = new ArrayList<>();
        //String hashedPassword = ProfileCreation.hashedPassword("1234567");
        Profile profile1 = new Profile("email1@email.com", "12345678910", "null", "null",
                false, "null", new FriendsList(), new PlayerRanking(), new GameHistory(gameHistory, achievementProgress), "null", "username1", 1);
        try {
            int id1 = PlayerManager.registerPlayer("username1", "email1@email.com", "12345678910");
            assertEquals(profile1.getEmail(), ProfileDatabaseAccess.obtainProfile(id1).getEmail());
            assertEquals(profile1.getUsername(), ProfileDatabaseAccess.obtainProfile(id1).getUsername());
            assertEquals(profile1.getOnlineStatus(), ProfileDatabaseAccess.obtainProfile(id1).getOnlineStatus());
            PlayerManager.deleteProfile(id1);
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    @Test
    void obtainProfileDirect() {
        HashMap<String, Double> achievementProgress = new HashMap<>();
        List<String> gameHistory = new ArrayList<>();
        //String hashedPassword = ProfileCreation.hashedPassword("1234567");
        Profile profile1 = new Profile("email1@email.com", "12345678910", "null", "null",
                false, "null", new FriendsList(), new PlayerRanking(), new GameHistory(gameHistory, achievementProgress), "null", "username1", 1);
        try {
            int id1 = PlayerManager.registerPlayer("username1", "email1@email.com", "12345678910");
            assertEquals(profile1.getEmail(), ProfileDatabaseAccess.obtainProfileDirect(id1).getEmail());
            assertEquals(profile1.getUsername(), ProfileDatabaseAccess.obtainProfileDirect(id1).getUsername());
            assertEquals(profile1.getOnlineStatus(), ProfileDatabaseAccess.obtainProfileDirect(id1).getOnlineStatus());
            PlayerManager.deleteProfile(id1);
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    @Test
    void obtainFriendsList() {
        try {
            System.out.println(PlayerManager.addToFriendsList(id, 1));
            //System.out.println(ProfileDatabaseAccess.obtainFriendsList(id).getFriends());
            //System.out.println(ProfileDatabaseAccess.obtainFriendsList(id).getFriendRequests());
            System.out.println(PlayerManager.addToFriendsList(id, 2));
            //System.out.println(ProfileDatabaseAccess.obtainFriendsList(id).getFriends());
            //System.out.println(ProfileDatabaseAccess.obtainFriendsList(id).getFriendRequests());
            System.out.println(PlayerManager.addToFriendsList(id, 3));
            //System.out.println(ProfileDatabaseAccess.obtainFriendsList(id).getFriends());
            //System.out.println(ProfileDatabaseAccess.obtainFriendsList(id).getFriendRequests());
            System.out.println(PlayerManager.addToFriendsList(id, 4));
            //System.out.println(ProfileDatabaseAccess.obtainFriendsList(id).getFriends());
            //System.out.println(ProfileDatabaseAccess.obtainFriendsList(id).getFriendRequests());
            System.out.println(PlayerManager.addToFriendRequests(id, 7));
            //System.out.println(ProfileDatabaseAccess.obtainFriendsList(id).getFriends());
            //System.out.println(ProfileDatabaseAccess.obtainFriendsList(id).getFriendRequests());
            System.out.println(PlayerManager.addToFriendRequests(id, 9));
            //System.out.println(ProfileDatabaseAccess.obtainFriendsList(id).getFriends());
            //System.out.println(ProfileDatabaseAccess.obtainFriendsList(id).getFriendRequests());
            List<Integer> friends = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
            List<Integer> friendRequests = new ArrayList<>(Arrays.asList(7, 9));
            assertEquals(friends, ProfileDatabaseAccess.obtainFriendsList(id).getFriends());
            assertEquals(friendRequests, ProfileDatabaseAccess.obtainFriendsList(id).getFriendRequests());
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    @Test
    void obtainPlayerRanking() {
        try {
            System.out.println(PlayerManager.updateAttribute(id, "win_loss_ratio_ttt", 0.25));
            System.out.println(PlayerManager.updateAttribute(id, "win_loss_ratio_connect4", 0.50));
            System.out.println(PlayerManager.updateAttribute(id, "win_loss_ratio_checkers", 0.75));
            System.out.println(PlayerManager.updateAttribute(id, "rating_ttt", 30));
            System.out.println(PlayerManager.updateAttribute(id, "rating_connect4", 50));
            System.out.println(PlayerManager.updateAttribute(id, "rating_checkers", 80));
            System.out.println(PlayerManager.updateAttribute(id, "rank_ttt", "Bronze"));
            System.out.println(PlayerManager.updateAttribute(id, "rank_connect4", "Silver"));
            System.out.println(PlayerManager.updateAttribute(id, "rank_checkers", "Gold"));
            System.out.println(PlayerManager.updateAttribute(id, "wins_ttt", 5));
            System.out.println(PlayerManager.updateAttribute(id, "wins_connect4", 120));
            System.out.println(PlayerManager.updateAttribute(id, "wins_checkers", 500));
            System.out.println(PlayerManager.updateAttribute(id, "losses_ttt", 45));
            System.out.println(PlayerManager.updateAttribute(id, "losses_connect4", 20));
            System.out.println(PlayerManager.updateAttribute(id, "losses_checkers", 1));
            System.out.println(PlayerManager.updateAttribute(id, "total_ttt", 53));
            System.out.println(PlayerManager.updateAttribute(id, "total_connect4", 141));
            System.out.println(PlayerManager.updateAttribute(id, "total_checkers", 504));
            double[] winLossRatio = new double[]{0.25, 0.50, 0.75};
            int[] rating = new int[]{30, 50, 80};
            String[] rank = new String[]{"Bronze", "Silver", "Gold"};
            int[] wins = new int[]{5, 120, 500};
            int[] losses = new int[]{45, 20, 1};
            int[] total = new int[]{53, 141, 504};
            profile = ProfileDatabaseAccess.obtainProfile(id);
            PlayerRanking playerRanking = new PlayerRanking(id, winLossRatio, rating, rank, wins, losses, total);
            assertEquals(playerRanking.getWinLossRatio(0), ProfileDatabaseAccess.obtainPlayerRanking(id).getWinLossRatio(PlayerRanking.TTT_INDEX));
            assertEquals(playerRanking.getRating(2), ProfileDatabaseAccess.obtainPlayerRanking(id).getRating(PlayerRanking.CHECKERS_INDEX));
            assertEquals(playerRanking.getRank(1), ProfileDatabaseAccess.obtainPlayerRanking(id).getRank(PlayerRanking.CONNECT4_INDEX));
            assertEquals(playerRanking.getWins(id, 2), ProfileDatabaseAccess.obtainPlayerRanking(id).getWins(id, PlayerRanking.CHECKERS_INDEX));
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    @Test
    void obtainGameHistory(){
        try {
            System.out.println(PlayerManager.updateAttribute(id, "games_played", "[TTT, Checkers, Connect4, Checkers]"));
            System.out.println(PlayerManager.updateAttribute(id, "achievement_progress", "[{Have100Cat'sInTTT, 0.75}, {WinCheckersWithoutLosingOnePiece, 0.00}]"));
            List<String> gameHistory = new ArrayList<>(Arrays.asList("TTT", "Checkers", "Connect4", "Checkers"));
            HashMap<String, Double> achievements = new HashMap<>(Map.ofEntries(entry("Have100Cat'sInTTT", 0.75), entry("WinCheckersWithoutLosingOnePiece", 0.00)));
            GameHistory gameHistory1 = new GameHistory(gameHistory, achievements);
            assertEquals(gameHistory1.getGameHistory(), ProfileDatabaseAccess.obtainGameHistory(id).getGameHistory());
            assertEquals(gameHistory1.getAchievementProgress(), ProfileDatabaseAccess.obtainGameHistory(id).getAchievementProgress());
        } catch (SQLException s){
            System.out.println(s.getMessage());
        }
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