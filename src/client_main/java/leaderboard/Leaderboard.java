package leaderboard;

import AuthenticationAndProfile.FriendsList;
import server.player.PlayerManager;

import java.util.*;


public class Leaderboard {

    private static final String FILEPATH = "profiles_export.csv";
    private ArrayList<ArrayList<String>> rankings; // to be deleted later

    private String sortChoice = "RATING";
    private String sortGame = "CHECKERS";

    private static final int ID_INDEX = 0;
    private static final int USERNAME_INDEX = 1;
    private static final int WLR_TTT_INDEX = 2;
    private static final int WLR_C4_INDEX = 3;
    private static final int WLR_CHECKERS_INDEX = 4;
    private static final int RATING_TTT_INDEX = 5;
    private static final int RATING_C4_INDEX = 6;
    private static final int RATING_CHECKERS_INDEX = 7;
    private static final int WINS_TTT_INDEX = 8;
    private static final int WINS_C4_INDEX = 9;
    private static final int WINS_CHECKERS_INDEX = 10;

    private static final String GAME_CHECKERS = "CHECKERS";
    private static final String GAME_TTT = "TTT";
    private static final String GAME_C4 = "C4";
    private static final String SORT_RATING = "RATING";
    private static final String SORT_WLR = "WLR";
    private static final String SORT_WINS = "WINS";

    /**
     * Constructor. creates a new Leaderboard with the specified sorting criterion and game selection.
     *
     * @param selectedSort
     * @param selectedGame
     */
    public Leaderboard(String selectedSort, String selectedGame) {

        sortChoice = selectedSort;
        sortGame = selectedGame;

        if (sortChoice == null) {
            sortChoice = "RATING";
        }

        updateLeaderboard();
    }

    /**
     * Updates the leaderboard by retrieving player profiles, sorting them, and toggling the sort order.
     * To be triggered everytime leaderboardGUIController() is triggered
     */
    public void updateLeaderboard() {

        //PlayerManager.getProfileTable(); BRING BACK WHEN COMPLETELY DONE LEADERBOARD

        this.rankings = CSVFileReader.retrieveProfiles(FILEPATH);

        this.rankings = getTopPlayers(sortChoice, sortGame);

        // toggleSortOrder(rankings);
        // System.out.println("toggled " + rankings);
    }

    public void GUIToggleSortOrder() {
        //PlayerManager.getProfileTable(); BRING BACK WHEN COMPLETELY DONE LEADERBOARD

        this.rankings = CSVFileReader.retrieveProfiles(FILEPATH);

        this.rankings = getTopPlayers(sortChoice, sortGame);

        toggleSortOrder(rankings);
        // System.out.println("toggled " + rankings);
    }

    public ArrayList<ArrayList<String>> getRankings() {
        return rankings;
    }

    /**
     * This method sorts the existing player rankings based on the provided sort choice
     *
     * @param sortChoice
     * @param sortGame
     * @return
     */
    public ArrayList<ArrayList<String>> getTopPlayers(String sortChoice, String sortGame) {

        if (Objects.equals(sortChoice, SORT_RATING)) {

            rankings = strip(sortByRating(rankings, sortGame), sortGame);
            System.out.println(rankings);

        } else if (Objects.equals(sortChoice, SORT_WLR)) {

            rankings = strip(sortByWLR(rankings, sortGame), sortGame);
            System.out.println(rankings);

        } else if (Objects.equals(sortChoice, SORT_WINS)) {

            rankings = strip(sortByWins(rankings, sortGame), sortGame);
            System.out.println(rankings);

        } else {
            System.out.println("select a valid sorting option: rating, win/loss ratio, wins");
        }

        return rankings;
    }

    /**
     * Gets a list of profiles, sorts profiles in descending order.
     * Sorts a list of player profiles based on the rating in descending order.
     * Each player profile is represented by an ArrayList of strings, and the rating
     * is located at a specific index within each profile.
     *
     * @param profiles The list of player profiles, where each profile is an ArrayList<String> containing profile data.
     * @return The sorted list of player profiles, sorted by rating in descending order. The same list is returned after sorting.
     */
    public ArrayList<ArrayList<String>> sortByRating(ArrayList<ArrayList<String>> profiles, String sortGame) {

        if (Objects.equals(sortGame, GAME_CHECKERS)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert the rating (which is a String) to an integer for comparison.
                    int rating1 = Integer.parseInt(p1.get(RATING_CHECKERS_INDEX));
                    int rating2 = Integer.parseInt(p2.get(RATING_CHECKERS_INDEX));

                    // Compare the ratings in descending order.
                    return Integer.compare(rating2, rating1);
                }
            });

        } else if (Objects.equals(sortGame, GAME_TTT)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert the rating (which is a String) to an integer for comparison.
                    int rating1 = Integer.parseInt(p1.get(RATING_TTT_INDEX));
                    int rating2 = Integer.parseInt(p2.get(RATING_TTT_INDEX));

                    // Compare the ratings in descending order.
                    return Integer.compare(rating2, rating1);
                }
            });

        } else if (Objects.equals(sortGame, GAME_C4)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert the rating (which is a String) to an integer for comparison.
                    int rating1 = Integer.parseInt(p1.get(RATING_C4_INDEX));
                    int rating2 = Integer.parseInt(p2.get(RATING_C4_INDEX));

                    // Compare the ratings in descending order.
                    return Integer.compare(rating2, rating1);
                }
            });

        }

        return profiles;
    }

    /**
     * Gets a list of profiles, sorts profiles in descending order.
     * Sorts a list of player profiles based on the wlr in descending order.
     * Each player profile is represented by an ArrayList of strings, and the wlr
     * is located at a specific index within each profile.
     *
     * @param profiles The list of player profiles, where each profile is an ArrayList<String> containing profile data.
     * @return The sorted list of player profiles, sorted by wlr in descending order. The same list is returned after sorting.
     */
    public ArrayList<ArrayList<String>> sortByWLR(ArrayList<ArrayList<String>> profiles, String sortGame) {

        if (Objects.equals(sortGame, GAME_CHECKERS)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert the rating (which is a String) to an integer for comparison.
                    float rating1 = Float.parseFloat(p1.get(WLR_CHECKERS_INDEX));
                    float rating2 = Float.parseFloat(p2.get(WLR_CHECKERS_INDEX));

                    // Compare the ratings in descending order.
                    return Float.compare(rating2, rating1);
                }
            });

        } else if (Objects.equals(sortGame, GAME_TTT)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert the rating (which is a String) to an integer for comparison.
                    float rating1 = Float.parseFloat(p1.get(WLR_TTT_INDEX));
                    float rating2 = Float.parseFloat(p2.get(WLR_TTT_INDEX));

                    // Compare the ratings in descending order.
                    return Float.compare(rating2, rating1);
                }
            });

        } else if (Objects.equals(sortGame, GAME_C4)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert the rating (which is a String) to an integer for comparison.
                    float rating1 = Float.parseFloat(p1.get(WLR_C4_INDEX));
                    float rating2 = Float.parseFloat(p2.get(WLR_C4_INDEX));

                    // Compare the ratings in descending order.
                    return Float.compare(rating2, rating1);
                }
            });

        }

        return profiles;
    }

    /**
     * Gets a list of profiles, sorts profiles in descending order.
     * Sorts a list of player profiles based on the wins in descending order.
     * Each player profile is represented by an ArrayList of strings, and the wins
     * is located at a specific index within each profile.
     *
     * @param profiles The list of player profiles, where each profile is an ArrayList<String> containing profile data.
     * @return The sorted list of player profiles, sorted by wins in descending order. The same list is returned after sorting.
     */
    public ArrayList<ArrayList<String>> sortByWins(ArrayList<ArrayList<String>> profiles, String sortGame) {

        if (Objects.equals(sortGame, GAME_CHECKERS)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert "wins" (which is a String) to an integer for comparison.
                    int rating1 = Integer.parseInt(p1.get(WINS_CHECKERS_INDEX));
                    int rating2 = Integer.parseInt(p2.get(WINS_CHECKERS_INDEX));

                    // Compare the ratings in descending order.
                    return Integer.compare(rating2, rating1);
                }
            });

        } else if (Objects.equals(sortGame, GAME_TTT)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert "wins" (which is a String) to an integer for comparison.
                    int rating1 = Integer.parseInt(p1.get(WINS_TTT_INDEX));
                    int rating2 = Integer.parseInt(p2.get(WINS_TTT_INDEX));

                    // Compare the ratings in descending order.
                    return Integer.compare(rating2, rating1);
                }
            });

        } else if (Objects.equals(sortGame, GAME_C4)) {

            Collections.sort(profiles, new Comparator<ArrayList<String>>() {
                public int compare(ArrayList<String> p1, ArrayList<String> p2) {
                    // Convert "wins" (which is a String) to an integer for comparison.
                    int rating1 = Integer.parseInt(p1.get(WINS_C4_INDEX));
                    int rating2 = Integer.parseInt(p2.get(WINS_C4_INDEX));

                    // Compare the ratings in descending order.
                    return Integer.compare(rating2, rating1);
                }
            });

        }

        return profiles;
    }

    /**
     * This method processes a list of user profiles and extracts only the relevant leaderboard data
     * based on the provided game identifier. Each profile is expected to be a list of strings containing
     * various user data (such as user ID, username, and game-specific statistics). Depending on the game,
     * it extracts the win-loss ratio, rating, and wins from the corresponding indices and returns a new list
     * of profiles containing only these fields.
     *
     * @param profiles
     * @param game
     * @return
     */
    private ArrayList<ArrayList<String>> strip(ArrayList<ArrayList<String>> profiles, String game) {

        ArrayList<ArrayList<String>> strippedLeaderboardStatistics = new ArrayList<ArrayList<String>>();

        if (Objects.equals(game, GAME_C4)) {

            addRelevantGameStatistics(profiles, strippedLeaderboardStatistics, WLR_C4_INDEX, RATING_C4_INDEX, WINS_C4_INDEX);

        } else if (Objects.equals(game, GAME_CHECKERS)) {

            addRelevantGameStatistics(profiles, strippedLeaderboardStatistics, WLR_CHECKERS_INDEX, RATING_CHECKERS_INDEX, WINS_CHECKERS_INDEX);

        } else if (Objects.equals(game, GAME_TTT)) {

            addRelevantGameStatistics(profiles, strippedLeaderboardStatistics, WLR_TTT_INDEX, RATING_TTT_INDEX, WINS_TTT_INDEX);

        }

        return strippedLeaderboardStatistics;
    }

    /**
     *
     * For every profile in the provided list, this method creates a new list containing the user's ID,
     * username, and game-specific statistics extracted from the indices provided.
     * The extracted statistics include the win-loss ratio, rating, and wins count.
     * Inserts leaderboard positioning.
     *
     * See strip()
     *
     * @param profiles
     * @param strippedLeaderboardStatistics
     * @param wlrC4Index
     * @param ratingC4Index
     * @param winsC4Index
     */
    private void addRelevantGameStatistics(ArrayList<ArrayList<String>> profiles, ArrayList<ArrayList<String>> strippedLeaderboardStatistics, int wlrC4Index, int ratingC4Index, int winsC4Index) {
        for (int i_profiles = 0; i_profiles < profiles.size(); i_profiles++) {
            ArrayList<String> individual = new ArrayList<>();

            individual.add(String.valueOf(i_profiles + 1));
            individual.add(profiles.get(i_profiles).get(ID_INDEX));
            individual.add(profiles.get(i_profiles).get(USERNAME_INDEX));
            individual.add(profiles.get(i_profiles).get(wlrC4Index));
            individual.add(profiles.get(i_profiles).get(ratingC4Index));
            individual.add(profiles.get(i_profiles).get(winsC4Index));

            strippedLeaderboardStatistics.add(individual);
        }
    }

    /**
     * Reverses the order of player profiles in the list. This method creates a new list
     * by iterating through the given list of profiles in reverse order and adding each
     * profile to the new list.
     *
     * @param profiles The list of player profiles, where each profile is an ArrayList<String> containing profile data.
     */
    public void toggleSortOrder(ArrayList<ArrayList<String>> profiles){
        ArrayList<ArrayList<String>> reversedRankingsArray = new ArrayList<ArrayList<String>>();
        int listSize = profiles.size();
        for(int i = listSize - 1; i >= 0; i--) {
            reversedRankingsArray.add(profiles.get(i));
        }

        this.rankings = reversedRankingsArray;
    }

    /**
     * Searches for a player's profile in the leaderboard rankings based on the specified username.
     *
     * This method iterates through the stripped list of player profiles stored in the rankings field.
     * Each profile is represented as an ArrayList<String> with the username expected to be at index 2.
     * When a profile with a matching username is found, it is added to a result list, printed to the console,
     * and the search terminates, returning the result list containing the first matching profile.
     * If no matching profile is found, a message "player not found" is printed and an empty list is returned.
     *
     *
     * @param username
     * @return
     */
    public ArrayList<ArrayList<String>> searchPlayer(String username) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        boolean found = false;

        for (ArrayList<String> profile : rankings) {
            // Ensure the profile has a username at index 2
            // uses stripped - strip() - rankings
            if (profile.size() > 2 && profile.get(2).equals(username)) {
                result.add(profile);
                found = true;

                System.out.println(result);

                break; //for returning the first matching profile
            }
        }

        if (!found) {
            System.out.println("player not found");
        }

        return result;
    }

    public ArrayList<ArrayList<String>> filterFriends(){

        FriendsList friendsList = new FriendsList();
        List<Integer> friendsID = friendsList.getFriends();

        // using IDs, search for Player's friends in rankings
        // in rankings, isolate Player's friends
        // make a NEW 2d list of friends
        // send list to gui controller

        return rankings;
    }

}
