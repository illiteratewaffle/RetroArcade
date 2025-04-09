package AuthenticationAndProfile;

import player.PlayerManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * GameHistory Class handles operation to do with the game history
 * @author Abigail Mullen
 */
public class GameHistory{
    private List<String> gameHistory = new ArrayList<>();
    private HashMap<String, Double> achievementProgress = new HashMap<String, Double>();
    private int id;

    /**
     * Constructor that loads the game history for a specific profile
     */
    public GameHistory(List<String> gameHistory, HashMap<String, Double> achievementProgress, int id) {
        this.gameHistory = gameHistory;
        this.achievementProgress = achievementProgress;
        this.id = id;
    }

    public GameHistory() {}


    /**
     * Retrives the full game history fo the user associated with the id
     * @return list of game history
     */
    public List<String> getGameHistory(){
        return new ArrayList<>(gameHistory); // returns a copy of the game history list
    }

    /**
     * Retrieves the last 5 games played by the user associated with the id
     * @return List of last five games played
     */
    public List<String> getRecentGames() { // retrieves the last five games played
        int size = gameHistory.size();
        if (size <= 5) {
            return new ArrayList<>(gameHistory);
        } else {
            ArrayList<String> recentGames = new ArrayList<>(gameHistory.subList(size - 5, size));
            Collections.reverse(recentGames);
            return recentGames;
        }
    }

    /**
     * Gets the player's achievement progress
     */
    public HashMap<String, Double> getAchievementProgress() {
        return achievementProgress;
    }

    /**
     * Sets the player's achievement progress
     *
     * @param achievementName the name of the achievement
     * @param progress the progress of the achievement
     */
    public void setAchievementProgress(String achievementName, double progress) throws SQLException {
        try {
            PlayerManager.setAchievementProgress(id, achievementName, Double.toString(progress));
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }


    /**
     * Alternatively sets the player's achievement progress using a hashmap
     *
     * @param achievementProgressHashMap the hashmap containing the achievement progress
     */
    public void setAchievementProgress(HashMap<String, Double> achievementProgressHashMap) {
        achievementProgress = achievementProgressHashMap;
    }
}
