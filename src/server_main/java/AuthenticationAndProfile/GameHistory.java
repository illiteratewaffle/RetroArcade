package AuthenticationAndProfile;

import player.PlayerManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static AuthenticationAndProfile.ProfileCSVReader.openSingleProfileFile;

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
     * Used when entering a game. Adds the game title to the GameHistory List.
     * @param gameName
     * @throws SQLException
     */
    public void updateGameHistory(String gameName) throws SQLException{
        try {
            this.gameHistory.add(gameName);
            PlayerManager.addToGamesPlayed(id, gameName);
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
    }

    /**
     * Retrives the full game history fo the user associated with the id
     * @return list of game history
     */
    public List<String> getGameHistory() throws SQLException, IOException {
        try {
            PlayerManager.getProfile(id); //method to get Profile csv with all attributes associated to the specified id
            String csvProfileFilePath = String.format("player_profile_%d.csv", id); //csv file saved to the main project directory
            List<String> newGameHistory = new ArrayList<>();
            ArrayList<String> profileFields = openSingleProfileFile(csvProfileFilePath);
            String gameHistoryString = profileFields.get(ProfileCSVReader.GHIST_INDEX);
            if (!gameHistoryString.equals("")) {
                String[] fieldsList = gameHistoryString.split(",");
                for (int i = 0; i < fieldsList.length; i++) {
                    newGameHistory.add(fieldsList[i]);
                }
            }
            gameHistory = newGameHistory;
            return gameHistory; // returns a copy of the game history list
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
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
    public HashMap<String, Double> getAchievementProgress() throws SQLException {
        try {
            HashMap<String, Double> achievementProgress = new HashMap<>();
            String achievementProgressString = PlayerManager.getAttribute(id, "achievement_progress");
            if (!(achievementProgressString == null | achievementProgressString.equals(""))) {
                String[] achievements = achievementProgressString.split(",");
                String section = "";
                String key = "";
                Double value = 0.00;
                int keyComplete = 0;
                for (int j = 0; j < achievements.length; j++) {
                    String entry = achievements[j];
                    for (int k = 0; k < entry.length(); k++) {
                        char c = entry.charAt(k);
                        if (c == '=' | c == '{' | c == '}') {
                            keyComplete += 1;
                        } else if (c == '>') {
                            keyComplete += 1;
                            key += section;
                            section = "";
                        } else if (c == ',') {
                            value = Double.parseDouble(section);
                            achievementProgress.put(key, value);
                            key = "";
                            section = "";
                        } else if (!(c == '"' | c == ' ')) {
                            section += c;
                        }
                    }
                    value = Double.parseDouble(section);
                    achievementProgress.put(key, value);
                    section = "";
                    key = "";
                }
            }
            return achievementProgress;
        } catch (SQLException s) {
            throw new SQLException(s.getMessage());
        }
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
