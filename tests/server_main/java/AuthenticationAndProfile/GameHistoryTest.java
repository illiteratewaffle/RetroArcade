package server_main.java.AuthenticationAndProfile;

import AuthenticationAndProfile.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.PlayerManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameHistoryTest {
    Profile profile;
    int id;

    @BeforeEach
    void setUp() {
        try {
            profile = ProfileCreation.createNewProfile("username6", "email6", "password");
            id = profile.getID();
        } catch (SQLException | NoSuchAlgorithmException | IOException s) {
            fail(s.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try {
            PlayerManager.deleteProfile(id);
        } catch (SQLException s) {
            fail(s.getMessage());
        }
    }

    @Test
    void getGameHistory() {
        try {
            List<String> gameHistory = new ArrayList<>(Arrays.asList("TTT", "Checkers", "Connect4", "Checkers"));
            PlayerManager.addToGamesPlayed(id, "TTT");
            PlayerManager.addToGamesPlayed(id, "Checkers");
            PlayerManager.addToGamesPlayed(id, "Connect4");
            PlayerManager.addToGamesPlayed(id, "Checkers");
            HashMap<String, Double> achievementProgress = new HashMap<>();
            achievementProgress.put("Win_Streak", 0.75);
            PlayerManager.setAchievementProgress(id, "Win_Streak", "0.75");
            achievementProgress.put("Matches_Played", 0.50);
            PlayerManager.setAchievementProgress(id,"Matches_Played", "0.50");

            GameHistory gh = new GameHistory(gameHistory, achievementProgress, id);

            assertEquals(gameHistory, gh.getGameHistory());
            assertEquals(achievementProgress, gh.getAchievementProgress());
        } catch (SQLException | IOException s) {
            fail(s.getMessage());
        }
    }

    @Test
    void getRecentGames() {
        List<String> gameHistory = new ArrayList<>(Arrays.asList("TTT", "Checkers", "Connect4", "Checkers", "TTT", "Connect4", "Chess"));
        HashMap<String, Double> achievementProgress = new HashMap<>();
        achievementProgress.put("Win_Streak", 0.75);
        achievementProgress.put("Matches_Played", 0.50);
        GameHistory gh = new GameHistory(gameHistory, achievementProgress, id);
        List<String> expectedRecentGames = new ArrayList<>(Arrays.asList("Chess", "Connect4", "TTT", "Checkers", "Connect4"));
        assertEquals(expectedRecentGames, gh.getRecentGames());
    }

    @Test
    void setAchievementProgress() {
        try {
            List<String> gameHistory = new ArrayList<>(Arrays.asList("TTT", "Checkers", "Connect4", "Checkers", "TTT", "Connect4", "Chess"));
            HashMap<String, Double> initialAchievementProgress = new HashMap<>();
            initialAchievementProgress.put("Win_Streak", 0.75);
            initialAchievementProgress.put("Matches_Played", 0.50);
            GameHistory gh = new GameHistory(gameHistory, initialAchievementProgress, id);
            HashMap<String, Double> newAchievementProgress = new HashMap<>();
            newAchievementProgress.put("Win_Streak", 0.90);
            newAchievementProgress.put("Matches_Played", 0.70);
            try {
                gh.setAchievementProgress("Win_Streak", 0.90);
                gh.setAchievementProgress("Matches_Played", 0.70);
            } catch (SQLException s) {
                fail(s.getMessage());
            }
            assertEquals(newAchievementProgress, gh.getAchievementProgress());
        } catch (SQLException s) {
            fail(s.getMessage());
        }
    }

    @Test void getAchievementProgress() {
        try {
            profile.getGameHistory().setAchievementProgress("10 Wins TTT", 0.50);
            profile.getGameHistory().setAchievementProgress("50 Wins TTT", 0.10);
            assertEquals(0.50, ProfileDatabaseAccess.obtainProfile(id).getGameHistory().getAchievementProgress().get("10 Wins TTT"));
            assertEquals(0.10, ProfileDatabaseAccess.obtainProfile(id).getGameHistory().getAchievementProgress().get("50 Wins TTT"));
        } catch (SQLException | IOException s) {
            fail(s.getMessage());
        }
    }
}