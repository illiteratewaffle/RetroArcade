package AuthenticationAndProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * GameHistory Class handles operation to do with the game history
 * @author Abigail Mullen
 */
public class GameHistory{
    private List<String> gameHistory = new ArrayList<>();
    private HashMap<String, Double> achievementProgress = new HashMap<String, Double>();
    private long id;
    //achievements
    //progress of achievements
    /**
     * Constructor that loads the game history for a specific profile
     */
//    public GameHistory(long id){
//        this.id = id;
//        loadGameHistory();
//    }

    public GameHistory(List<String> gameHistory, HashMap<String, Double> achievementProgress) {
        this.gameHistory = gameHistory;
        this.achievementProgress = achievementProgress;
    }

    public GameHistory(List<String> gameHistory){};



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
    public List<String> getRecentGames(){ // retrieves the last five games played
        int size = gameHistory.size();
        List<String> recentGames = new ArrayList<>();

        if (size <= 5){
            recentGames.addAll(gameHistory);
        } else {
            for (int i = size - 5; i < size; i++){
                recentGames.add(gameHistory.get(i));
            }
        }
        return recentGames;
    }

//    public void loadGameHistory(){
//        String filePath = // insert file path (different file for each id? all ids in same file?)
//        ArrayList<String> fileContents = GameHistoryCSVReader.openGameHistoryFile(filePath);
//        String[] games = //split game history based on how stored in csv (by ',' or ';' etc)
//        for (String game : games){
//            gameHistory.add(game.trim());
//        }
//    }

    // load game history from CSV file ?
    // save game history to CSV file ?


    public HashMap<String, Double> getAchievementProgress() {
        return achievementProgress;
    }

    public void setAchievementProgress(HashMap<String, Double> achievementProgress) {
        this.achievementProgress = achievementProgress;
    }
}
