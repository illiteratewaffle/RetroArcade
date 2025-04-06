package client_main_tests.java.AuthenticationAndProfile;

import AuthenticationAndProfile.GameHistory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameHistoryTest {
    @Test
    void getGameHistory() {
        HashMap<String, Double> achievementProgress = new HashMap<>();
        achievementProgress.put("Win_Streak", 0.75);
        achievementProgress.put("Matches_Played", 0.50);
        List<String> gameHistory = new ArrayList<>(Arrays.asList("TTT", "Checkers", "Connect4", "Checkers"));
        GameHistory gh = new GameHistory(gameHistory, achievementProgress);
        assertEquals(gameHistory, gh.getGameHistory());
        assertEquals(achievementProgress, gh.getAchievementProgress());
    }
}