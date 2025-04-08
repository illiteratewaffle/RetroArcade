package client_main_tests.java.AuthenticationAndProfile;

import AuthenticationAndProfile.GameHistory;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameHistoryTest {
    int id = 0;
    @Test
    void getGameHistory() {
        List<String> gameHistory = new ArrayList<>(Arrays.asList("TTT", "Checkers", "Connect4", "Checkers"));
        HashMap<String, Double> achievementProgress = new HashMap<>();
        achievementProgress.put("Win_Streak", 0.75);
        achievementProgress.put("Matches_Played", 0.50);
        GameHistory gh = new GameHistory(gameHistory, achievementProgress, id);
        assertEquals(gameHistory, gh.getGameHistory());
        assertEquals(achievementProgress, gh.getAchievementProgress());
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
    }

    @Test void getAchievementProgress() {

    }
}